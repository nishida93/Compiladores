/*
 * Created by JFormDesigner on Sat Sep 15 20:26:49 BRT 2018
 */

package puc.compiladores.ui;

import puc.compiladores.lexico.Lexico;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.*;

/**
 * @author Matheus Nishida
 */
public class Compilador extends JFrame {

    private ArrayList<String> listArquivo;
    private boolean flagFile = false;
    private File diretorio;
    private Lexico lexico;

    public Compilador() {
        initComponents();
    }

    private void buttonExecutarActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void abrirArquivo() {

        JFileChooser fileChooser = new JFileChooser();
        listArquivo = new ArrayList<>();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (Stream<String> stream = Files.lines(Paths.get(selectedFile.getAbsolutePath()))) {
                listArquivo = (ArrayList<String>) stream.collect(Collectors.toList());
                diretorio = new File(selectedFile.getAbsolutePath());
                flagFile = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        new Arquivo(textAreaCodigo, listArquivo, diretorio);
    }

    private void salvarArquivo() {

        if(flagFile) {
            try(BufferedWriter writer = Files.newBufferedWriter(diretorio.toPath())) {
                for(String line : textAreaCodigo.getText().split("\\n")) {
                    writer.write(line + "\r\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                diretorio = file;
                try(BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
                    for(String line : textAreaCodigo.getText().split("\\n")) {
                        writer.write(line + "\r\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void compilarArquivo() {
//        try {
//            lexico = new Lexico(diretorio);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Luan Bonomi
        menuBar = new JMenuBar();
        menuArquivo = new JMenu();
        menuItemAbrir = new JMenuItem();
        menuItemSalvar = new JMenuItem();
        panelTudo = new JPanel();
        panelCodigo = new JPanel();
        scrollPaneCodigo = new JScrollPane();
        textAreaCodigo = new JTextArea();
        panelErro = new JPanel();
        scrollPaneErro = new JScrollPane();
        textAreaErro = new JTextArea();
        panelExecutar = new JPanel();
        buttonExecutar = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(799, 400));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar ========
        {

            //======== menuArquivo ========
            {
                menuArquivo.setText("Arquivo");

                //---- menuItemAbrir ----
                menuItemAbrir.setText("Abrir");
                menuItemAbrir.addActionListener(e -> abrirArquivo());
                menuArquivo.add(menuItemAbrir);

                //---- menuItemSalvar ----
                menuItemSalvar.setText("Salvar");
                menuItemSalvar.addActionListener(e -> salvarArquivo());
                menuArquivo.add(menuItemSalvar);
            }
            menuBar.add(menuArquivo);
        }
        setJMenuBar(menuBar);

        //======== panelTudo ========
        {

            // JFormDesigner evaluation mark
            panelTudo.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), panelTudo.getBorder())); panelTudo.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            panelTudo.setLayout(new BorderLayout());

            //======== panelCodigo ========
            {
                panelCodigo.setLayout(new BorderLayout());

                //======== scrollPaneCodigo ========
                {
                    scrollPaneCodigo.setPreferredSize(new Dimension(2, 300));
                    scrollPaneCodigo.setViewportView(textAreaCodigo);
                }
                panelCodigo.add(scrollPaneCodigo, BorderLayout.CENTER);
            }
            panelTudo.add(panelCodigo, BorderLayout.NORTH);

            //======== panelErro ========
            {
                panelErro.setLayout(new BorderLayout());

                //======== scrollPaneErro ========
                {
                    scrollPaneErro.setPreferredSize(new Dimension(2, 250));

                    //---- textAreaErro ----
                    textAreaErro.setEditable(false);
                    scrollPaneErro.setViewportView(textAreaErro);
                }
                panelErro.add(scrollPaneErro, BorderLayout.CENTER);
            }
            panelTudo.add(panelErro, BorderLayout.CENTER);

            //======== panelExecutar ========
            {
                panelExecutar.setLayout(new BorderLayout());

                //---- buttonExecutar ----
                buttonExecutar.setText("COMPILAR");
                buttonExecutar.addActionListener(e -> compilarArquivo());
                panelExecutar.add(buttonExecutar, BorderLayout.CENTER);
            }
            panelTudo.add(panelExecutar, BorderLayout.SOUTH);
        }
        contentPane.add(panelTudo, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Luan Bonomi
    private JMenuBar menuBar;
    private JMenu menuArquivo;
    private JMenuItem menuItemAbrir;
    private JMenuItem menuItemSalvar;
    private JPanel panelTudo;
    private JPanel panelCodigo;
    private JScrollPane scrollPaneCodigo;
    private JTextArea textAreaCodigo;
    private JPanel panelErro;
    private JScrollPane scrollPaneErro;
    private JTextArea textAreaErro;
    private JPanel panelExecutar;
    private JButton buttonExecutar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
