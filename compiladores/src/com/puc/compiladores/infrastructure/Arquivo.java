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

public class Arquivo extends JFileChooser {

    private JFileChooser fileChooser;
    private ArrayList<String> listArquivo;

    public Arquivo() { }

    public Arquivo(JTable table1) {
        listArquivo = new ArrayList<>();
        fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (Stream<String> stream = Files.lines(Paths.get(selectedFile.getAbsolutePath()))) {
                listArquivo = (ArrayList<String>) stream.collect(Collectors.toList());
                populateTable(table1);
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

    public void populateTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for(int i=0 ; i < listArquivo.size() ; i++) {
            Object[] palavra = new Object[] {i+1};
            //System.out.println("PRINT >>>" + getPalavra(1,0));
            palavra = adicionaElemento(palavra, getPalavra(i, 0));
            palavra = adicionaElemento(palavra, getPalavra(i, 1).replaceAll(",",""));
            palavra = adicionaElemento(palavra, getPalavra(i, 2).replaceAll(",",""));
            palavra = adicionaElemento(palavra, getPalavra(i, 3).replaceAll(",",""));
            model.addRow(palavra);
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
