package com.puc.compiladores.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Matheus
 */
public class Arquivo extends JFileChooser {

    private static final Logger LOG = LoggerFactory.getLogger(Arquivo.class);
    private JFileChooser fileChooser;
    private ArrayList<String> listArquivo;
    private EnumInstrucoes enumInstrucoes;
    private Pilha pilha;

    public Arquivo() { }

    public Arquivo(JTable stackTable, boolean isDebug) {
        if(isDebug) {
            System.out.println("Eh modo debug");
        }
        DefaultTableModel model2 = (DefaultTableModel) stackTable.getModel();
        populateStackTable(model2);
    }

    public Arquivo(JTable instructionsTable, JTable stackTable) {
        pilha = new Pilha();
        listArquivo = new ArrayList<>();
        fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (Stream<String> stream = Files.lines(Paths.get(selectedFile.getAbsolutePath()))) {
                listArquivo = (ArrayList<String>) stream.collect(Collectors.toList());
                populateTables(instructionsTable, stackTable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            System.out.println("Error index >>> " + e.getMessage());
            return "";
        }

    }

    private void populateTables(JTable instructionsTable, JTable stackTable) {
        DefaultTableModel model = (DefaultTableModel) instructionsTable.getModel();
        DefaultTableModel model2 = (DefaultTableModel) stackTable.getModel();
        clearAllRows(model);
        for(int i=0 ; i < listArquivo.size() ; i++) {
            Object[] palavra = new Object[] {i+1};

            String comando = getPalavra(i, 0);
            String param1 = getPalavra(i, 1).replaceAll(",", "");
            String param2 = getPalavra(i, 1).replaceAll(",", "");

            if (comando.equals(EnumInstrucoes.START.toString())) {
                pilha.decrementaTopo(); // S:=-1
            } else if(comando.equals(EnumInstrucoes.HLT.toString())) {
                // TODO implementar as logicas de cada comando
                // “Pára a execução da MVD”
            } else if(comando.equals(EnumInstrucoes.LDC.toString())) {
                pilha.incrementaTopo(); // S:=s + 1
                pilha.inserePilha(pilha.getTopo(), Integer.parseInt(param1)); // M [s]: = k
            } else if(comando.equals(EnumInstrucoes.LDV.toString())) {
                pilha.incrementaTopo(); // S:=s + 1
                pilha.inserePilha(pilha.getTopo(), pilha.getValor(Integer.parseInt(param1))); // M[s]:=M[n]
            } else if(comando.equals(EnumInstrucoes.ADD.toString())) {
                pilha.inserePilha(pilha.getTopo() - 1,
                        pilha.getValor(pilha.getTopo() - 1) + pilha.getValor(pilha.getTopo())); // M[s-1]:=M[s-1] + M[s]
                pilha.decrementaTopo(); // s:=s - 1
            } else if(comando.equals(EnumInstrucoes.SUB.toString())) {
                pilha.inserePilha(pilha.getTopo() - 1,
                        pilha.getValor(pilha.getTopo() - 1) - pilha.getValor(pilha.getTopo())); // M[s-1]:=M[s-1] - M[s]
                pilha.decrementaTopo(); // s:=s - 1
            } else if(comando.equals(EnumInstrucoes.MULT.toString())) {
                // TODO implementar as logicas de cada comando
                // M[s-1]:=M[s-1] * M[s]
                pilha.inserePilha(pilha.getTopo() - 1,
                        pilha.getValor(pilha.getTopo() - 1) * pilha.getValor(pilha.getTopo()));
                // ; s:=s - 1
                pilha.decrementaTopo();
            } else if(comando.equals(EnumInstrucoes.DIVI.toString())) {
                // TODO implementar as logicas de cada comando
                // M[s-1]:=M[s-1] div M[s]
                pilha.inserePilha(pilha.getTopo() - 1,
                        pilha.getValor(pilha.getTopo() - 1) / pilha.getValor(pilha.getTopo()));
                // s:=s - 1
            } else if(comando.equals(EnumInstrucoes.INV.toString())) {
                // TODO implementar as logicas de cada comando
                // M[s]:= -M[s]
                pilha.inserePilha(pilha.getTopo(), -pilha.getValor(pilha.getTopo()));
            } else if(comando.equals(EnumInstrucoes.AND.toString())) {
                // TODO implementar as logicas de cada comando
                // se M [s-1] = 1 e M[s] = 1 então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1

            } else if(comando.equals(EnumInstrucoes.OR.toString())) {
                // TODO implementar as logicas de cada comando
                // se M[s-1] = 1 ou M[s] = 1 então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1

            } else if(comando.equals(EnumInstrucoes.NEG.toString())) {
                // TODO implementar as logicas de cada comando
                // M[s]:=1 - M[s]
                pilha.inserePilha(pilha.getTopo(), 1 - pilha.getTopo());
            } else if(comando.equals(EnumInstrucoes.CME.toString())) {
                // TODO implementar as logicas de cada comando
                // se M[s-1] < M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            } else if(comando.equals(EnumInstrucoes.CMA.toString())) {
                // TODO implementar as logicas de cada comando
                // se M[s-1] > M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            } else if(comando.equals(EnumInstrucoes.CMAQ.toString())) {
                // TODO implementar as logicas de cada comando
                // se M[s-1] ≥ M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            } else if(comando.equals(EnumInstrucoes.CMEQ.toString())) {
                // TODO implementar as logicas de cada comando
                // se M[s-1] ≤ M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            } else if(comando.equals(EnumInstrucoes.CEQ.toString())) {
                // TODO implementar as logicas de cada comando
                // se M[s-1] = M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            } else if(comando.equals(EnumInstrucoes.CDIF.toString())) {
                // TODO implementar as logicas de cada comando
                // se M[s-1] ≠ M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
            } else if(comando.equals(EnumInstrucoes.STR.toString())) {
                // TODO implementar as logicas de cada comando
                // M[n]:=M[s]
                pilha.inserePilha(Integer.parseInt(param1), pilha.getValor(pilha.getTopo()));
                // s:=s-1
                pilha.decrementaTopo();
            } else if(comando.equals(EnumInstrucoes.JMP.toString())) {
                // TODO implementar as logicas de cada comando
                // i:= t
            } else if(comando.equals(EnumInstrucoes.JMPF.toString())) {
                // TODO implementar as logicas de cada comando
                //se M[s] = 0 então i:=t senão i:=i + 1;
                // s:=s-1
            } else if(comando.equals(EnumInstrucoes.NULL.toString())) {
                // TODO implementar as logicas de cada comando

            } else if(comando.equals(EnumInstrucoes.RD.toString())) {
                // TODO implementar as logicas de cada comando
                // S:=s + 1; M[s]:= “próximo valor de entrada”.
            } else if(comando.equals(EnumInstrucoes.PRN.toString())) {
                // TODO implementar as logicas de cada comando
                // “Imprimir M[s]”; s:=s-1
            } else if(comando.equals(EnumInstrucoes.ALLOC.toString())) {
                // TODO implementar as logicas de cada comando
                //Para k:=0 até n-1 faça
                // {s:=s + 1; M[s]:=M[m+k]}
            } else if(comando.equals(EnumInstrucoes.DALLOC.toString())) {
                // TODO implementar as logicas de cada comando
                // Para k:=n-1 até 0 faça
                // {M[m+k]:=M[s]; s:=s - 1}
            } else if(comando.equals(EnumInstrucoes.CALL.toString())) {
                // TODO implementar as logicas de cada comando
                // S:=s + 1; M[s]:=i + 1; i:=t
            } else if(comando.equals(EnumInstrucoes.RETURN.toString())) {
                // TODO implementar as logicas de cada comando
                // i:=M[s]; s:=s - 1
            }

            //populateStackTable(model2);

            palavra = adicionaElemento(palavra, getPalavra(i, 0));
            palavra = adicionaElemento(palavra, getPalavra(i, 1).replaceAll(",",""));
            palavra = adicionaElemento(palavra, getPalavra(i, 2).replaceAll(",",""));
            palavra = adicionaElemento(palavra, getPalavra(i, 3).replaceAll(",",""));
            model.addRow(palavra);
        }
    }

    private void clearAllRows(DefaultTableModel model) {
        model.getRowCount();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    private void populateStackTable(DefaultTableModel model2) {
        System.out.println("Entrando no metodo populate STACK");
        LOG.info("Bla bla bla");
        //clearAllRows(model2);
        model2.addRow(new Object[] {10,30});
    }

    private Object[] adicionaElemento(Object[] obj, Object newObj) {

        ArrayList<Object> temp = new ArrayList<>(Arrays.asList(obj));
        temp.add(newObj);
        return temp.toArray();

    }

    public void fechar() {
        System.exit(0);
    }
}
