package com.puc.compiladores.infrastructure;

import com.puc.compiladores.ui.VM;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

/**
 * @author Matheus
 */
public class Arquivo extends JFileChooser {
    private ArrayList<String> listArquivo;
    private Pilha pilha = new Pilha();
    private VM vm = new VM();

    public Arquivo() {}

    public int getTamanhoArquivo(ArrayList<String> arquivo) {
        return arquivo.size();
    }

    public Arquivo(JTable instructionsTable, ArrayList<String> arquivo) {
        listArquivo = arquivo;
        populateTables(arquivo, instructionsTable);
    }

    public String getLinha(int index) {
        return listArquivo.get(index);
    }

    public String getPalavra(int indexLinha, int indexPalavra) {

        String linha = String.valueOf(getLinha(indexLinha));

        String[] palavra = linha.split(" ");

        try{
            return palavra[indexPalavra];
        } catch (ArrayIndexOutOfBoundsException e) {
            // Caso nao exista o index, retorna String vazia
            //System.out.println("Error index >>> " + e.getMessage());
            return "";
        }
    }

    public String debuggar(VM virtualMachine) {
        String input = displayInput(virtualMachine);
        return input;
    }

    private void populateTables(ArrayList<String> arquivo, JTable instructionsTable) {
        DefaultTableModel model = (DefaultTableModel) instructionsTable.getModel();
        clearAllRows(model);
        for(int i=0 ; i < arquivo.size() ; i++) {
            Object[] palavra = new Object[] {i};
            String comando = getPalavra(i, 0);
            String param1 = getPalavra(i, 1);
            String param2 = getPalavra(i, 2);
            if(comando.equals("ALLOC") || comando.equals("DALLOC"))
            {
                String[] params = getPalavra(i, 1).split(",");
                param1 = params[0];
                param2 = params[1];
            }
            palavra = adicionaElemento(palavra, comando);
            palavra = adicionaElemento(palavra, param1);
            palavra = adicionaElemento(palavra, param2);
            model.addRow(palavra);
        }
    }

    private void clearAllRows(DefaultTableModel model) {
        model.getRowCount();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    public int stepByStep(JTable instructionsTable, JTable stackTable, ArrayList<String> arquivo,
                          VM virtualMachine, int linha, Pilha novaPilha){
        listArquivo = arquivo;
        DefaultTableModel stackTableModel = (DefaultTableModel) stackTable.getModel();
        clearAllRows(stackTableModel);
        instructionsTable.changeSelection(linha,0, false, false);
        int aux = stepInstructions(stackTable, arquivo, virtualMachine, linha, novaPilha);
//        System.out.println("Valor da topo > " + novaPilha.getTopo());
        populaPilhaPrint(stackTableModel, novaPilha);
        return aux;
    }

    private void populaPilhaPrint(DefaultTableModel stackTableModel, Pilha pilha) {
        for(int i=0 ; i <= pilha.getTopo() ; i++) {
//            System.out.println("Valor da pilha > " + pilha.getValor(i));
            Object[] palavra = new Object[] {i, pilha.getValor(i)};
            palavra = adicionaElemento(palavra, palavra);
            stackTableModel.addRow(palavra);
        }
    }

//    public int populaBreakPoints(JTable stackTable, ArrayList<String> arquivo,
//                                   VM virtualMachine, int linha) {
//        DefaultTableModel stackTableModel = (DefaultTableModel) stackTable.getModel();
//        clearAllRows(stackTableModel);
//        int aux = stepInstructions(stackTable, arquivo, virtualMachine, linha, pilha);
//        System.out.println("Valor da topo > " + pilha.getTopo());
//        populaPilhaPrint(stackTableModel, pilha);
//        return aux;
//    }

    private int stepInstructions(JTable stackTable, ArrayList<String> arquivo,
                                 VM virtualMachine, int index, Pilha pilha) {
        DefaultTableModel stackTableModel = (DefaultTableModel) stackTable.getModel();
        clearAllRows(stackTableModel);

        String comando = getPalavra(index, 0);
        String param1 = getPalavra(index, 1);
        String param2 = getPalavra(index, 2);
        if(comando.equals("ALLOC") || comando.equals("DALLOC"))
        {
            String[] params = getPalavra(index, 1).split(",");
            param1 = params[0];
            param2 = params[1];
        }

        if (comando.equals(EnumInstrucoes.START.toString())) {
            pilha.decrementaTopo(); // S:=-1
        } else if(comando.equals(EnumInstrucoes.HLT.toString())) {
            // “Pára a execução da MVD”
            return -99;
        } else if(comando.equals(EnumInstrucoes.LDC.toString())) {
            pilha.incrementaTopo(); // S:=s + 1
            pilha.inserePilha(pilha.getTopo(), Integer.parseInt(param1)); // M [s]: = k
        } else if(comando.equals(EnumInstrucoes.LDV.toString())) {
            pilha.incrementaTopo(); // S:=s + 1
            pilha.inserePilha(pilha.getTopo(), pilha.getValor(Integer.parseInt(param1))); // M[s]:=M[n]
        } else if(comando.equals(EnumInstrucoes.ADD.toString())) {
            pilha.inserePilha(pilha.getTopo() - 1,
                    pilha.getValor(pilha.getTopo() - 1) + pilha.getValor(pilha.getTopo())); // M[s-1]:=M[s-1] + M[s]
            System.out.println("Inseriu na pilha");
            pilha.decrementaTopo(); // s:=s - 1
        } else if(comando.equals(EnumInstrucoes.SUB.toString())) {
            pilha.inserePilha(pilha.getTopo() - 1,
                    pilha.getValor(pilha.getTopo() - 1) - pilha.getValor(pilha.getTopo())); // M[s-1]:=M[s-1] - M[s]
            pilha.decrementaTopo(); // s:=s - 1
        } else if(comando.equals(EnumInstrucoes.MULT.toString())) {
            // M[s-1]:=M[s-1] * M[s]
            pilha.inserePilha(pilha.getTopo() - 1,
                    pilha.getValor(pilha.getTopo() - 1) * pilha.getValor(pilha.getTopo()));
            // ; s:=s - 1
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.DIVI.toString())) {
            // M[s-1]:=M[s-1] div M[s]
            pilha.inserePilha(pilha.getTopo() - 1,
                    pilha.getValor(pilha.getTopo() - 1) / pilha.getValor(pilha.getTopo()));
            // s:=s - 1
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.INV.toString())) {
            // M[s]:= -M[s]
            pilha.inserePilha(pilha.getTopo(), -pilha.getValor(pilha.getTopo()));
        } else if(comando.equals(EnumInstrucoes.AND.toString())) {
            // se M [s-1] = 1 e M[s] = 1 então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            if (pilha.getValor(pilha.getTopo() - 1) == 1 &&
                    pilha.getValor(pilha.getTopo()) == 1) {
                pilha.inserePilha(pilha.getTopo() - 1, 1);
            } else {
                pilha.inserePilha(pilha.getTopo() - 1, 0);
            }
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.OR.toString())) {
            // se M[s-1] = 1 ou M[s] = 1 então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            if (pilha.getValor(pilha.getTopo() - 1) == 1 ||
                    pilha.getValor(pilha.getTopo()) == 1) {
                pilha.inserePilha(pilha.getTopo() - 1, 1);
            } else {
                pilha.inserePilha(pilha.getTopo() - 1, 0);
            }
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.NEG.toString())) {
            // M[s]:=1 - M[s]
            pilha.inserePilha(pilha.getTopo(), 1 - pilha.getTopo());
        } else if(comando.equals(EnumInstrucoes.CME.toString())) {
            // se M[s-1] < M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            if (pilha.getValor(pilha.getTopo() - 1) < pilha.getValor(pilha.getTopo())) {
                pilha.inserePilha(pilha.getTopo() - 1, 1);
            } else {
                pilha.inserePilha(pilha.getTopo() - 1, 0);
            }
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.CMA.toString())) {
            // se M[s-1] > M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            if (pilha.getValor(pilha.getTopo() - 1) > pilha.getValor(pilha.getTopo())) {
                pilha.inserePilha(pilha.getTopo() - 1, 1);
            } else {
                pilha.inserePilha(pilha.getTopo() - 1, 0);
            }
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.CMAQ.toString())) {
            // se M[s-1] ≥ M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            if (pilha.getValor(pilha.getTopo() - 1) >= pilha.getValor(pilha.getTopo())) {
                pilha.inserePilha(pilha.getTopo() - 1, 1);
            } else {
                pilha.inserePilha(pilha.getTopo() - 1, 0);
            }
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.CMEQ.toString())) {
            // se M[s-1] ≤ M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            if (pilha.getValor(pilha.getTopo() - 1) <= pilha.getValor(pilha.getTopo())) {
                pilha.inserePilha(pilha.getTopo() - 1, 1);
            } else {
                pilha.inserePilha(pilha.getTopo() - 1, 0);
            }
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.CEQ.toString())) {
            // se M[s-1] = M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            if (pilha.getValor(pilha.getTopo() - 1) == pilha.getValor(pilha.getTopo())) {
                pilha.inserePilha(pilha.getTopo() - 1, 1);
            } else {
                pilha.inserePilha(pilha.getTopo() - 1, 0);
            }
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.CDIF.toString())) {
            // se M[s-1] ≠ M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            if (pilha.getValor(pilha.getTopo() - 1) != pilha.getValor(pilha.getTopo())) {
                pilha.inserePilha(pilha.getTopo() - 1, 1);
            } else {
                pilha.inserePilha(pilha.getTopo() - 1, 0);
            }
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.STR.toString())) {
            // M[n]:=M[s]
            pilha.inserePilha(Integer.parseInt(param1), pilha.getValor(pilha.getTopo()));
            // s:=s-1
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.JMP.toString())) {
            // i:= t
            if (verificaLabel(arquivo, param1) == -1) {
                return -99;
            }
            return verificaLabel(arquivo, param1);
        } else if(comando.equals(EnumInstrucoes.JMPF.toString())) {
            //se M[s] = 0 então i:=t senão i:=i + 1;
            // s:=s-1
            if (pilha.getValor(pilha.getTopo()) == 0) {
                if (verificaLabel(arquivo, param1) == -1) {
                    return -99;
                }
                pilha.decrementaTopo();
                return verificaLabel(arquivo, param1);
            }
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.NULL.toString())) {
            //continue;
        } else if(comando.equals(EnumInstrucoes.RD.toString())) {
            // S:=s + 1; M[s]:= “próximo valor de entrada”.
            pilha.incrementaTopo();
            String input = displayInput(virtualMachine);
            if ((input != null) && (input.length() > 0)) {
                pilha.inserePilha(pilha.getTopo(), Integer.parseInt(input));
            } else {
                System.out.println("Usuario nao digitou valor de entrada");
                return -99;
            }

        } else if(comando.equals(EnumInstrucoes.PRN.toString())) {
            // “Imprimir M[s]”; s:=s-1
            virtualMachine.writeOutput(String.valueOf(pilha.getValor(pilha.getTopo())));
            pilha.decrementaTopo();
        } else if(comando.equals(EnumInstrucoes.ALLOC.toString())) {
            //Para k:=0 até n-1 faça
            // {s:=s + 1; M[s]:=M[m+k]}
            for (int k=0; k < Integer.parseInt(param2); k++) {
                pilha.incrementaTopo();
                pilha.inserePilha(pilha.getTopo(), pilha.getValor(Integer.parseInt(param1) + k));
            }
        } else if(comando.equals(EnumInstrucoes.DALLOC.toString())) {
            // Para k:=n-1 até 0 faça
            // {M[m+k]:=M[s]; s:=s - 1}
            for (int k=Integer.parseInt(param2) - 1; k >= 0; k--) {
                pilha.inserePilha(Integer.parseInt(param1) + k, pilha.getValor(pilha.getTopo()));
                pilha.decrementaTopo();
            }
        } else if(comando.equals(EnumInstrucoes.CALL.toString())) {
            // S:=s + 1; M[s]:=i + 1; i:=t
            pilha.incrementaTopo();
            System.out.println("index = " + index);
            pilha.inserePilha(pilha.getTopo(), index + 1);
            if (verificaLabel(arquivo, param1) == -1) {
                return -99;
            }
            return verificaLabel(arquivo, param1);
        } else if(comando.equals(EnumInstrucoes.RETURN.toString())) {
            // i:=M[s]; s:=s - 1
            index = pilha.getValor(pilha.getTopo());
            pilha.decrementaTopo();
            return index;
        }
        return -98;
    }

    private int verificaLabel(ArrayList<String> arquivo, String label) {
        for(int i = 0 ; i <= arquivo.size() ; i++) {
            String comando = getPalavra(i, 0);
            if (comando.equals(label)) {
                return i;
            }
        }
        return -1;
    }

    private Object[] adicionaElemento(Object[] obj, Object newObj) {

        ArrayList<Object> temp = new ArrayList<>(Arrays.asList(obj));
        temp.add(newObj);
        return temp.toArray();

    }

    private String displayInput(VM virtualMachine) {
        String input = (String)JOptionPane.showInputDialog(
                virtualMachine,
                "Digite um valor de entrada:\n",
                "Valor de entrada",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);

        return input;
    }

    public void fechar() {
        System.exit(0);
    }
}