/*
 * Created by JFormDesigner on Thu Aug 16 23:42:28 BRT 2018
 */

package com.puc.compiladores.ui;

import com.puc.compiladores.infrastructure.Arquivo;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author Matheus
 */
public class VM extends JFrame {

    private DefaultTableModel modelTabelaInstrucoes = new DefaultTableModel();
    private DefaultTableModel modelTabelaPilha = new DefaultTableModel();
    private ArrayList<String> listArquivo;

    public VM() {
        jFileChooser = new JFileChooser();
        Container contentPane = getContentPane();
        contentPane.setPreferredSize(new Dimension(1200,800));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        initComponents(contentPane);
    }

    private void menuItem5ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(getContentPane(),
                "Desenvolvedores:\nMatheus Nishida RA: 12212692\nLuan Bonomi RA: 15108780");
    }

    private void menuItem1ActionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        listArquivo = new ArrayList<>();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (Stream<String> stream = Files.lines(Paths.get(selectedFile.getAbsolutePath()))) {
                listArquivo = (ArrayList<String>) stream.collect(Collectors.toList());

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        new Arquivo(tableInstrucoes, listArquivo);

    }

    private void menuFecharActionPerformed(ActionEvent e) {
        new Arquivo().fechar();
    }

    private void menuCompilarActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void menu3ActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void menuItem2ActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void btnCompilarActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void menuItemCompilarActionPerformed(ActionEvent e) {
        // TODO add your code here
        //System.out.println("ARQUIVO> " + listArquivo);
        new Arquivo(tablePilha, false, listArquivo, this);
    }

    private void menuItemDebuggarActionPerformed(ActionEvent e) {
        // TODO add your code here
        //System.out.println("ARQUIVO> " + listArquivo);
        btnContinuar.setEnabled(true);
        new Arquivo(tablePilha, true, listArquivo, this);
    }

    @SuppressWarnings("unchecked")
    private void initComponents(Container contentPane) {

        modelTabelaInstrucoes.addColumn("Linha");
        modelTabelaInstrucoes.addColumn("Instrução");
        modelTabelaInstrucoes.addColumn("Atributo #1");
        modelTabelaInstrucoes.addColumn("Atributo #2");
        modelTabelaInstrucoes.addColumn("Comentário");


        modelTabelaPilha.addColumn("Endereço[S]");
        modelTabelaPilha.addColumn("Valor");

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Matheus
        menuBar = new JMenuBar();
        menuArquivo = new JMenu();
        menuItemAbrir = new JMenuItem();
        menuItemSair = new JMenuItem();
        menuExecutar = new JMenu();
        menuItemCompilar = new JMenuItem();
        menuItemDebuggar = new JMenuItem();
        menuSobre = new JMenu();
        menuItemSobre = new JMenuItem();
        panel1 = new JPanel();
        lblInstrucoes = new JLabel();
        scrollTabelaInstrucoes = new JScrollPane();
        tableInstrucoes = new JTable(modelTabelaInstrucoes);
        panel3 = new JPanel();
        panel4 = new JPanel();
        lblEntrada = new JLabel();
        scrollEntrada = new JScrollPane();
        textAreaEntrada = new JTextArea();
        panel5 = new JPanel();
        lblSaida = new JLabel();
        scrollSaida = new JScrollPane();
        textAreaSaida = new JTextArea();
        panel6 = new JPanel();
        lblBreakPoints = new JLabel();
        scrollBreakPoints = new JScrollPane();
        textAreaBreakPoints = new JTextArea();
        btnContinuar = new JButton();
        panel2 = new JPanel();
        lblPilha = new JLabel();
        scrollPilha = new JScrollPane();
        tablePilha = new JTable(modelTabelaPilha);

        //======== this ========
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        //======== menuBar ========
        {

            //======== menuArquivo ========
            {
                menuArquivo.setText("Arquivo");

                //---- menuItemAbrir ----
                menuItemAbrir.setText("Abrir...");
                menuItemAbrir.addActionListener(e -> menuItem1ActionPerformed(e));
                menuArquivo.add(menuItemAbrir);

                //---- menuItemSair ----
                menuItemSair.setText("Sair");
                menuItemSair.addActionListener(e -> menuItem2ActionPerformed(e));
                menuArquivo.add(menuItemSair);
            }
            menuBar.add(menuArquivo);

            //======== menuExecutar ========
            {
                menuExecutar.setText("Executar");

                //---- menuItemCompilar ----
                menuItemCompilar.setText("Compilar");
                menuItemCompilar.addActionListener(e -> {
			btnCompilarActionPerformed(e);
			menuItemCompilarActionPerformed(e);
		});
                menuExecutar.add(menuItemCompilar);

                //---- menuItemDebuggar ----
                menuItemDebuggar.setText("Debuggar");
                menuItemDebuggar.addActionListener(e -> menuItemDebuggarActionPerformed(e));
                menuExecutar.add(menuItemDebuggar);
            }
            menuBar.add(menuExecutar);

            //======== menuSobre ========
            {
                menuSobre.setText("Sobre...");
                menuSobre.addActionListener(e -> menu3ActionPerformed(e));

                //---- menuItemSobre ----
                menuItemSobre.setText("Sobre");
                menuItemSobre.addActionListener(e -> menuItem5ActionPerformed(e));
                menuSobre.add(menuItemSobre);
            }
            menuBar.add(menuSobre);
        }
        setJMenuBar(menuBar);

        //======== panel1 ========
        {
            panel1.setPreferredSize(new Dimension(600, 2216));
            panel1.setBorder(LineBorder.createBlackLineBorder());
            panel1.setLayout(new BorderLayout());

            //---- lblInstrucoes ----
            lblInstrucoes.setText("Instru\u00e7\u00f5es a serem executadas pela VM");
            lblInstrucoes.setHorizontalAlignment(SwingConstants.CENTER);
            lblInstrucoes.setFont(new Font(".SF NS Text", Font.BOLD | Font.ITALIC, 13));
            lblInstrucoes.setBorder(LineBorder.createBlackLineBorder());
            panel1.add(lblInstrucoes, BorderLayout.NORTH);

            //======== scrollTabelaInstrucoes ========
            {
                scrollTabelaInstrucoes.setPreferredSize(new Dimension(454, 2000));
                scrollTabelaInstrucoes.setBorder(LineBorder.createBlackLineBorder());
                scrollTabelaInstrucoes.setViewportView(tableInstrucoes);
            }
            panel1.add(scrollTabelaInstrucoes, BorderLayout.CENTER);

            //======== panel3 ========
            {
                panel3.setBorder(LineBorder.createBlackLineBorder());
                panel3.setLayout(new BorderLayout());

                //======== panel4 ========
                {
                    panel4.setPreferredSize(new Dimension(200, 32));
                    panel4.setForeground(new Color(153, 153, 153));
                    panel4.setBorder(LineBorder.createBlackLineBorder());
                    panel4.setLayout(new BorderLayout());

                    //---- lblEntrada ----
                    lblEntrada.setText("Janela de Entrada");
                    lblEntrada.setHorizontalAlignment(SwingConstants.CENTER);
                    lblEntrada.setFont(new Font(".SF NS Text", Font.BOLD | Font.ITALIC, 13));
                    lblEntrada.setBorder(LineBorder.createBlackLineBorder());
                    panel4.add(lblEntrada, BorderLayout.NORTH);

                    //======== scrollEntrada ========
                    {

                        //---- textAreaEntrada ----
                        scrollEntrada.setViewportView(textAreaEntrada);
                    }
                    panel4.add(scrollEntrada, BorderLayout.CENTER);
                }
                panel3.add(panel4, BorderLayout.WEST);

                //======== panel5 ========
                {
                    panel5.setPreferredSize(new Dimension(200, 200));
                    panel5.setBorder(LineBorder.createBlackLineBorder());
                    panel5.setLayout(new BorderLayout());

                    //---- lblSaida ----
                    lblSaida.setText("Janela de Sa\u00edda");
                    lblSaida.setFont(new Font(".SF NS Text", Font.BOLD | Font.ITALIC, 13));
                    lblSaida.setHorizontalAlignment(SwingConstants.CENTER);
                    lblSaida.setBorder(LineBorder.createBlackLineBorder());
                    panel5.add(lblSaida, BorderLayout.NORTH);

                    //======== scrollSaida ========
                    {

                        //---- textAreaSaida ----
                        scrollSaida.setViewportView(textAreaSaida);
                    }
                    panel5.add(scrollSaida, BorderLayout.CENTER);
                }
                panel3.add(panel5, BorderLayout.CENTER);

                //======== panel6 ========
                {
                    panel6.setPreferredSize(new Dimension(200, 32));
                    panel6.setBorder(LineBorder.createBlackLineBorder());
                    panel6.setLayout(new BorderLayout());

                    //---- lblBreakPoints ----
                    lblBreakPoints.setText("Break Points");
                    lblBreakPoints.setFont(new Font(".SF NS Text", Font.BOLD | Font.ITALIC, 13));
                    lblBreakPoints.setHorizontalAlignment(SwingConstants.CENTER);
                    lblBreakPoints.setBorder(LineBorder.createBlackLineBorder());
                    panel6.add(lblBreakPoints, BorderLayout.NORTH);

                    //======== scrollBreakPoints ========
                    {

                        //---- textAreaBreakPoints ----
                        scrollBreakPoints.setViewportView(textAreaBreakPoints);
                    }
                    panel6.add(scrollBreakPoints, BorderLayout.CENTER);
                }
                panel3.add(panel6, BorderLayout.EAST);

                //---- btnContinuar ----
                btnContinuar.setText("Continuar");
                btnContinuar.setActionCommand("Continuar");
                panel3.add(btnContinuar, BorderLayout.SOUTH);
            }
            panel1.add(panel3, BorderLayout.SOUTH);
        }
        contentPane.add(panel1);

        //======== panel2 ========
        {
            panel2.setBorder(LineBorder.createBlackLineBorder());
            panel2.setLayout(new BorderLayout());

            //---- lblPilha ----
            lblPilha.setText("Conte\u00fado da Pilha");
            lblPilha.setHorizontalAlignment(SwingConstants.CENTER);
            lblPilha.setFont(new Font(".SF NS Text", Font.BOLD | Font.ITALIC, 13));
            lblPilha.setBorder(LineBorder.createBlackLineBorder());
            panel2.add(lblPilha, BorderLayout.NORTH);

            //======== scrollPilha ========
            {
                scrollPilha.setBorder(LineBorder.createBlackLineBorder());

                //---- tablePilha ----
                tablePilha.setPreferredScrollableViewportSize(new Dimension(190, 400));
                scrollPilha.setViewportView(tablePilha);
            }
            panel2.add(scrollPilha, BorderLayout.CENTER);
        }
        contentPane.add(panel2);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents






        btnContinuar.setEnabled(false);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Matheus
    private JMenuBar menuBar;
    private JMenu menuArquivo;
    private JMenuItem menuItemAbrir;
    private JMenuItem menuItemSair;
    private JMenu menuExecutar;
    private JMenuItem menuItemCompilar;
    private JMenuItem menuItemDebuggar;
    private JMenu menuSobre;
    private JMenuItem menuItemSobre;
    private JPanel panel1;
    private JLabel lblInstrucoes;
    private JScrollPane scrollTabelaInstrucoes;
    private JTable tableInstrucoes;
    private JPanel panel3;
    private JPanel panel4;
    private JLabel lblEntrada;
    private JScrollPane scrollEntrada;
    private JTextArea textAreaEntrada;
    private JPanel panel5;
    private JLabel lblSaida;
    private JScrollPane scrollSaida;
    private JTextArea textAreaSaida;
    private JPanel panel6;
    private JLabel lblBreakPoints;
    private JScrollPane scrollBreakPoints;
    private JTextArea textAreaBreakPoints;
    private JButton btnContinuar;
    private JPanel panel2;
    private JLabel lblPilha;
    private JScrollPane scrollPilha;
    private JTable tablePilha;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private JFileChooser jFileChooser;

    public void writeOutput(String valor) {
        textAreaSaida.setText(textAreaSaida.getText() + "\n" + valor);
    }

    public void clearOutput() {
        textAreaSaida.setText("");
    }
}
