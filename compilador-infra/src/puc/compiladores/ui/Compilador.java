/*
 * Created by JFormDesigner on Sat Sep 15 20:26:49 BRT 2018
 */

package puc.compiladores.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Matheus Nishida
 */
public class Compilador extends JFrame {
    public Compilador() {
        initComponents();
    }

    private void buttonExecutarActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Matheus Nishida
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItemAbrir = new JMenuItem();
        menuItemSalvar = new JMenuItem();
        textFieldInput = new JTextField();
        buttonExecutar = new JButton();
        label1 = new JLabel();

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
                menu1.add(menuItemAbrir);

                //---- menuItemSalvar ----
                menuItemSalvar.setText("Salvar");
                menu1.add(menuItemSalvar);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        //---- textFieldInput ----
        textFieldInput.setPreferredSize(new Dimension(106, 400));
        textFieldInput.setBackground(Color.white);
        textFieldInput.setMaximumSize(new Dimension(500, 2147483647));
        textFieldInput.setHorizontalAlignment(SwingConstants.LEFT);
        contentPane.add(textFieldInput, BorderLayout.NORTH);

        //---- buttonExecutar ----
        buttonExecutar.setText("Executar");
        buttonExecutar.setPreferredSize(new Dimension(9, 20));
        buttonExecutar.addActionListener(e -> buttonExecutarActionPerformed(e));
        contentPane.add(buttonExecutar, BorderLayout.SOUTH);

        //---- label1 ----
        label1.setPreferredSize(new Dimension(650, 10));
        label1.setBackground(Color.white);
        contentPane.add(label1, BorderLayout.WEST);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Matheus Nishida
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItemAbrir;
    private JMenuItem menuItemSalvar;
    private JTextField textFieldInput;
    private JButton buttonExecutar;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
