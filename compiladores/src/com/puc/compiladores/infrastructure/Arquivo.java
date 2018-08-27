package com.puc.compiladores.infrastructure;

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

    private JFileChooser fileChooser;
    private ArrayList<String> listArquivo;
    private EnumInstrucoes enumInstrucoes;

    public Arquivo() { }

    public Arquivo(JTable instructionsTable) {
        listArquivo = new ArrayList<>();
        fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (Stream<String> stream = Files.lines(Paths.get(selectedFile.getAbsolutePath()))) {
                listArquivo = (ArrayList<String>) stream.collect(Collectors.toList());
                populateInstructionsTable(instructionsTable);
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

    private void populateInstructionsTable(JTable instructionsTable) {
        DefaultTableModel model = (DefaultTableModel) instructionsTable.getModel();
        clearAllRows(model);
        for(int i=0 ; i < listArquivo.size() ; i++) {
            Object[] palavra = new Object[] {i+1};
            if (getPalavra(i, 0).equals(EnumInstrucoes.START.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.START);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.HLT.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.HLT);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.LDC.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.LDC);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.LDV.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.LDV);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.ADD.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.ADD);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.SUB.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.SUB);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.MULT.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.MULT);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.DIVI.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.DIVI);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.INV.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.INV);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.AND.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.AND);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.OR.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.OR);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.NEG.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.NEG);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.CME.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.CME);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.CMA.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.CMA);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.CMAQ.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.CMAQ);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.CMEQ.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.CMEQ);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.CEQ.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.CEQ);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.CDIF.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.CDIF);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.STR.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.STR);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.JMP.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.JMP);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.JMPF.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.JMPF);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.NULL.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.NULL);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.RD.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.RD);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.PRN.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.PRN);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.ALLOC.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.ALLOC);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.DALLOC.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.DALLOC);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.CALL.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.CALL);
            } else if(getPalavra(i, 0).equals(EnumInstrucoes.RETURN.toString())) {
                System.out.println("A comando foi: " + enumInstrucoes.RETURN);
            }

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

    private Object[] adicionaElemento(Object[] obj, Object newObj) {

        ArrayList<Object> temp = new ArrayList<>(Arrays.asList(obj));
        temp.add(newObj);
        return temp.toArray();

    }

    public void fechar() {
        System.exit(0);
    }
}
