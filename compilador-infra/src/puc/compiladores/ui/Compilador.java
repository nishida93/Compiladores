/*
 * Created by JFormDesigner on Sat Sep 15 20:26:49 BRT 2018
 */

package puc.compiladores.ui;

import puc.compiladores.lexico.Lexico;
import puc.compiladores.sintatico.Sintatico;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
        textAreaCodigo.setText(null);
        textAreaErro.setText(null);

        FileDialog fd = new FileDialog(this, "Open", FileDialog.LOAD);
        fd.setVisible(true);
        String path = fd.getDirectory();
        String nameFile = fd.getFile();
        diretorio = new File(path + nameFile);
        flagFile = true;
        printandoTexto();
    }

    private void salvarArquivo() {
        textAreaErro.setText(null);
        if(flagFile) {
            escrevendoTexto();
        } else {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                diretorio = file;
                escrevendoTexto();
            }
        }

        printandoTexto();

    }

    private void escrevendoTexto(){
        try {
            FileWriter writer = new FileWriter(diretorio);
            BufferedWriter bw = new BufferedWriter(writer);
            textAreaCodigo.write(bw);
            bw.close();
        } catch (Exception e) {

        }

    }

    private void printandoTexto() {
        textAreaCodigo.setText(null);

        try {
            FileReader reader = new FileReader(diretorio);
            BufferedReader br = new BufferedReader(reader);
            textAreaCodigo.read(br, null);
            br.close();
            textAreaCodigo.requestFocus();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private void compilarArquivo() {
        try {
            new Sintatico(diretorio, textAreaErro, textAreaCodigo);
        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Luan Bonomi
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItemAbrir = new JMenuItem();
        menuItemSalvar = new JMenuItem();
        panelTudo = new JPanel();
        panelCodigo = new JPanel();
        scrollPaneCodigo = new JScrollPane();
        textAreaCodigo = new JTextArea();
        labelCodigo = new JLabel();
        panelErro = new JPanel();
        scrollPaneErro = new JScrollPane();
        textAreaErro = new JTextArea();
        labelErro = new JLabel();
        panelExecutar = new JPanel();
        buttonExecutar = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(799, 400));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("Arquivo");

                //---- menuItemAbrir ----
                menuItemAbrir.setText("Abrir");
                menuItemAbrir.addActionListener(e -> abrirArquivo());
                menu1.add(menuItemAbrir);

                //---- menuItemSalvar ----
                menuItemSalvar.setText("Salvar");
                menuItemSalvar.addActionListener(e -> salvarArquivo());
                menu1.add(menuItemSalvar);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

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
                panelCodigo.setPreferredSize(new Dimension(0, 300));
                panelCodigo.setLayout(new BorderLayout());

                //======== scrollPaneCodigo ========
                {
                    scrollPaneCodigo.setViewportView(textAreaCodigo);
                }
                panelCodigo.add(scrollPaneCodigo, BorderLayout.CENTER);

                //---- labelCodigo ----
                labelCodigo.setText("C\u00f3digo");
                panelCodigo.add(labelCodigo, BorderLayout.NORTH);
            }
            panelTudo.add(panelCodigo, BorderLayout.NORTH);

            //======== panelErro ========
            {
                panelErro.setPreferredSize(new Dimension(0, 200));
                panelErro.setLayout(new BorderLayout());

                //======== scrollPaneErro ========
                {
                    scrollPaneErro.setViewportView(textAreaErro);
                }
                panelErro.add(scrollPaneErro, BorderLayout.CENTER);

                //---- labelErro ----
                labelErro.setText("Console");
                panelErro.add(labelErro, BorderLayout.NORTH);
            }
            panelTudo.add(panelErro, BorderLayout.CENTER);

            //======== panelExecutar ========
            {
                panelExecutar.setPreferredSize(new Dimension(0, 50));
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
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItemAbrir;
    private JMenuItem menuItemSalvar;
    private JPanel panelTudo;
    private JPanel panelCodigo;
    private JScrollPane scrollPaneCodigo;
    private JTextArea textAreaCodigo;
    private JLabel labelCodigo;
    private JPanel panelErro;
    private JScrollPane scrollPaneErro;
    private JTextArea textAreaErro;
    private JLabel labelErro;
    private JPanel panelExecutar;
    private JButton buttonExecutar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
