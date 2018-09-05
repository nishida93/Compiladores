/*
 * Created by JFormDesigner on Wed Sep 05 08:52:44 BRT 2018
 */

package com.puc.lexico.ui;

import java.awt.*;
import javax.swing.*;

/**
 * @author Luan Bonomi
 */
public class InterfaceCompilador extends JFrame {
    public InterfaceCompilador() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Luan Bonomi
        dialogPane = new JPanel();
        menuBar = new JMenuBar();
        menuFile = new JMenu();
        menuItemAbrir = new JMenuItem();
        menuItemSalvar = new JMenuItem();
        menuExecutar = new JMenu();
        menuItemCompilar = new JMenuItem();

        //======== this ========
        setMinimumSize(new Dimension(1200, 800));
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== dialogPane ========
        {
            dialogPane.setPreferredSize(new Dimension(1200, 800));

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(null);

            //======== menuBar ========
            {
                menuBar.setMinimumSize(new Dimension(1200, 3));
                menuBar.setMaximumSize(new Dimension(1200, 32769));
                menuBar.setPreferredSize(new Dimension(1200, 27));

                //======== menuFile ========
                {
                    menuFile.setText("File");

                    //---- menuItemAbrir ----
                    menuItemAbrir.setText("Abri...");
                    menuFile.add(menuItemAbrir);

                    //---- menuItemSalvar ----
                    menuItemSalvar.setText("Salvar");
                    menuFile.add(menuItemSalvar);
                }
                menuBar.add(menuFile);

                //======== menuExecutar ========
                {
                    menuExecutar.setText("Executar");

                    //---- menuItemCompilar ----
                    menuItemCompilar.setText("Compilar");
                    menuExecutar.add(menuItemCompilar);
                }
                menuBar.add(menuExecutar);
            }
            dialogPane.add(menuBar);
            menuBar.setBounds(0, 0, 1200, menuBar.getPreferredSize().height);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < dialogPane.getComponentCount(); i++) {
                    Rectangle bounds = dialogPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = dialogPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                dialogPane.setMinimumSize(preferredSize);
                dialogPane.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(dialogPane);
        dialogPane.setBounds(0, 0, 1200, 715);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Luan Bonomi
    private JPanel dialogPane;
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuItemAbrir;
    private JMenuItem menuItemSalvar;
    private JMenu menuExecutar;
    private JMenuItem menuItemCompilar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
