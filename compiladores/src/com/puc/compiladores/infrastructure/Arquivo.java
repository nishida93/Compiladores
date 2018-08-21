package com.puc.compiladores.infrastructure;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Arquivo extends JFileChooser {

    private JFileChooser fileChooser;

    public Arquivo() {
        //super();
        fileChooser = new JFileChooser();
        //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            try {
                System.out.println(Files.readAllLines(Paths.get(selectedFile.getAbsolutePath())));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
