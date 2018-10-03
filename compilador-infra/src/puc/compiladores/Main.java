package puc.compiladores;

import puc.compiladores.lexico.Lexico;
import puc.compiladores.ui.Compilador;

public class Main {

    public static void main(String[] args) {
//        try {
//            new Lexico();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        new Compilador().setVisible(true);
    }
}
