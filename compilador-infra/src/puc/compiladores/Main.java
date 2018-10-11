package puc.compiladores;

import puc.compiladores.sintatico.Sintatico;

public class Main {

    public static void main(String[] args) throws Exception {
        try {
            new Sintatico();
        } catch (Exception e) {
            throw e;
        }
    }
}
