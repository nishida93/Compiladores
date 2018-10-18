package puc.compiladores.ui;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Arquivo {

    private ArrayList<String> listArquivo;

    public Arquivo(JTextArea instructions, ArrayList<String> arquivo, File diretorio) {
        listArquivo = arquivo;
        populateTextArea(arquivo, instructions, diretorio);
    }

    public String getLinha(int index) {
        return listArquivo.get(index);
    }

    private void populateTextArea(ArrayList<String> arquivo, JTextArea instructions, File diretorio) {

        for(int i=0 ; i < arquivo.size() ; i++) {
            String linha = getLinha(i);
            instructions.append(linha);
            if(linha != "\r\n"){
                instructions.append("\r\n");
            }
        }
    }
}
