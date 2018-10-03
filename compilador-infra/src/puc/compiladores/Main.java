package puc.compiladores;

import puc.compiladores.lexico.Lexico;
import puc.compiladores.sintatico.Sintatico;

public class Main {

    public static void main(String[] args) {
        try {
            new Sintatico();
        } catch (Exception e) {
            System.out.println("Erro na inicializacao: " + e);
        }
    }
}
