/*
 * Created by JFormDesigner on Thu Aug 16 23:42:28 BRT 2018
 */

package com.puc.compiladores.ui;

import com.puc.compiladores.infrastructure.Arquivo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author Matheus
 */
public class VM extends JFrame {

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
        new Arquivo(table1);
    }

    private void menuItem2ActionPerformed(ActionEvent e) {
        new Arquivo().fechar();
    }

    private void initComponents(Container contentPane) {

        DefaultTableModel modelTable1 = new DefaultTableModel();
        modelTable1.addColumn("Linha");
        modelTable1.addColumn("Instrução");
        modelTable1.addColumn("Atributo #1");
        modelTable1.addColumn("Atributo #2");
        modelTable1.addColumn("Comentário");

        DefaultTableModel modelTable2 = new DefaultTableModel();
        modelTable2.addColumn("Endereço[S]");
        modelTable2.addColumn("Valor");

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Matheus
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menu2 = new JMenu();
        menuItem3 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menu3 = new JMenu();
        menuItem5 = new JMenuItem();
        panel1 = new JPanel();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable(modelTable1);
        panel3 = new JPanel();
        panel4 = new JPanel();
        label3 = new JLabel();
        scrollPane3 = new JScrollPane();
        textArea1 = new JTextArea();
        panel5 = new JPanel();
        label5 = new JLabel();
        scrollPane4 = new JScrollPane();
        textArea2 = new JTextArea();
        panel6 = new JPanel();
        label7 = new JLabel();
        scrollPane5 = new JScrollPane();
        textArea3 = new JTextArea();
        panel2 = new JPanel();
        label2 = new JLabel();
        scrollPane2 = new JScrollPane();
        table2 = new JTable(modelTable2);

        //======== this ========
        //Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("Arquivo");

                //---- menuItem1 ----
                menuItem1.setText("Abrir...");
                menuItem1.addActionListener(e -> menuItem1ActionPerformed(e));
                menu1.add(menuItem1);

                //---- menuItem2 ----
                menuItem2.setText("Sair");
                menuItem2.addActionListener(e -> menuItem2ActionPerformed(e));
                menu1.add(menuItem2);
            }
            menuBar1.add(menu1);

            //======== menu2 ========
            {
                menu2.setText("Executar");

                //---- menuItem3 ----
                menuItem3.setText("Compilar");
                menu2.add(menuItem3);

                //---- menuItem4 ----
                menuItem4.setText("Debuggar");
                menu2.add(menuItem4);
            }
            menuBar1.add(menu2);

            //======== menu3 ========
            {
                menu3.setText("Sobre...");

                //---- menuItem5 ----
                menuItem5.setText("Sobre");
                menuItem5.addActionListener(e -> menuItem5ActionPerformed(e));
                menu3.add(menuItem5);
            }
            menuBar1.add(menu3);
        }
        setJMenuBar(menuBar1);

        //======== panel1 ========
        {
            panel1.setPreferredSize(new Dimension(600, 2216));
            panel1.setBorder(LineBorder.createBlackLineBorder());
            panel1.setLayout(new BorderLayout());

            //---- label1 ----
            label1.setText("Instru\u00e7\u00f5es a serem executadas pela VM");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            label1.setFont(new Font(".SF NS Text", Font.BOLD | Font.ITALIC, 13));
            label1.setBorder(LineBorder.createBlackLineBorder());
            panel1.add(label1, BorderLayout.NORTH);

            //======== scrollPane1 ========
            {
                scrollPane1.setPreferredSize(new Dimension(454, 2000));
                scrollPane1.setBorder(LineBorder.createBlackLineBorder());
                scrollPane1.setViewportView(table1);
            }
            panel1.add(scrollPane1, BorderLayout.CENTER);

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

                    //---- label3 ----
                    label3.setText("Janela de Entrada");
                    label3.setHorizontalAlignment(SwingConstants.CENTER);
                    label3.setFont(new Font(".SF NS Text", Font.BOLD | Font.ITALIC, 13));
                    label3.setBorder(LineBorder.createBlackLineBorder());
                    panel4.add(label3, BorderLayout.NORTH);

                    //======== scrollPane3 ========
                    {

                        //---- textArea1 ----
                        textArea1.setText("Input test");
                        scrollPane3.setViewportView(textArea1);
                    }
                    panel4.add(scrollPane3, BorderLayout.CENTER);
                }
                panel3.add(panel4, BorderLayout.WEST);

                //======== panel5 ========
                {
                    panel5.setPreferredSize(new Dimension(200, 200));
                    panel5.setBorder(LineBorder.createBlackLineBorder());
                    panel5.setLayout(new BorderLayout());

                    //---- label5 ----
                    label5.setText("Janela de Sa\u00edda");
                    label5.setFont(new Font(".SF NS Text", Font.BOLD | Font.ITALIC, 13));
                    label5.setHorizontalAlignment(SwingConstants.CENTER);
                    label5.setBorder(LineBorder.createBlackLineBorder());
                    panel5.add(label5, BorderLayout.NORTH);

                    //======== scrollPane4 ========
                    {

                        //---- textArea2 ----
                        textArea2.setText("Output test");
                        scrollPane4.setViewportView(textArea2);
                    }
                    panel5.add(scrollPane4, BorderLayout.CENTER);
                }
                panel3.add(panel5, BorderLayout.CENTER);

                //======== panel6 ========
                {
                    panel6.setPreferredSize(new Dimension(200, 32));
                    panel6.setBorder(LineBorder.createBlackLineBorder());
                    panel6.setLayout(new BorderLayout());

                    //---- label7 ----
                    label7.setText("Break Points");
                    label7.setFont(new Font(".SF NS Text", Font.BOLD | Font.ITALIC, 13));
                    label7.setHorizontalAlignment(SwingConstants.CENTER);
                    label7.setBorder(LineBorder.createBlackLineBorder());
                    panel6.add(label7, BorderLayout.NORTH);

                    //======== scrollPane5 ========
                    {

                        //---- textArea3 ----
                        textArea3.setText("Break point #1");
                        scrollPane5.setViewportView(textArea3);
                    }
                    panel6.add(scrollPane5, BorderLayout.CENTER);
                }
                panel3.add(panel6, BorderLayout.EAST);
            }
            panel1.add(panel3, BorderLayout.SOUTH);
        }
        contentPane.add(panel1);

        //======== panel2 ========
        {
            panel2.setBorder(LineBorder.createBlackLineBorder());
            panel2.setLayout(new BorderLayout());

            //---- label2 ----
            label2.setText("Conte\u00fado da Pilha");
            label2.setHorizontalAlignment(SwingConstants.CENTER);
            label2.setFont(new Font(".SF NS Text", Font.BOLD | Font.ITALIC, 13));
            label2.setBorder(LineBorder.createBlackLineBorder());
            panel2.add(label2, BorderLayout.NORTH);

            //======== scrollPane2 ========
            {
                scrollPane2.setBorder(LineBorder.createBlackLineBorder());

                //---- table2 ----
                table2.setPreferredScrollableViewportSize(new Dimension(190, 400));
                scrollPane2.setViewportView(table2);
            }
            panel2.add(scrollPane2, BorderLayout.CENTER);
        }
        contentPane.add(panel2);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Matheus
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenu menu2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem4;
    private JMenu menu3;
    private JMenuItem menuItem5;
    private JPanel panel1;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel3;
    private JPanel panel4;
    private JLabel label3;
    private JScrollPane scrollPane3;
    private JTextArea textArea1;
    private JPanel panel5;
    private JLabel label5;
    private JScrollPane scrollPane4;
    private JTextArea textArea2;
    private JPanel panel6;
    private JLabel label7;
    private JScrollPane scrollPane5;
    private JTextArea textArea3;
    private JPanel panel2;
    private JLabel label2;
    private JScrollPane scrollPane2;
    private JTable table2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private JFileChooser jFileChooser;
}
