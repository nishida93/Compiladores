package puc.compiladores;

import puc.compiladores.lexico.Lexico;

public class Main {

    public static void main(String[] args) {
        try {
            new Lexico();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
