package puc.compiladores.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;

public class Arquivo {

    private ArrayList<String> listArquivo;

    public Arquivo(JTextArea instructions, ArrayList<String> arquivo) {
        listArquivo = arquivo;
        populateTextArea(arquivo, instructions);
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
            System.out.println("Error index >>> " + e.getMessage());
            return "";
        }
    }

    private void populateTextArea(ArrayList<String> arquivo, JTextArea instructions) {

        for(int i=0 ; i < arquivo.size() ; i++) {
            String linha = getLinha(i);

            instructions.append(linha);
            instructions.append("\n");
        }
    }
}
