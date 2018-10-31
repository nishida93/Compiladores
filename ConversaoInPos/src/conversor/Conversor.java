package conversor;

import java.util.ArrayList;
import java.util.List;

public class Conversor {

    List<String> pilhaInFixa = new ArrayList();
    List<String> pilhaPosFixa = new ArrayList();
    List<String> pilhaAux = new ArrayList();

    public Conversor() {
        setup();

        trataPilha();

        System.out.println("pilha = " + pilhaInFixa);
        System.out.println("pilha Pos = " + pilhaPosFixa);
    }

    private List<String> trataPilha() {
        for (String elemento: pilhaInFixa) {
            if(elemento.contains("+") || elemento.contains("-") ||
                elemento.contains("*") || elemento.contains("div") ||
                elemento.contains(">") || elemento.contains("<") ||
                elemento.contains(">=") || elemento.contains("<=") ||
                elemento.contains("=") || elemento.contains("!") ||
                elemento.contains("e") || elemento.contains("ou")) {

                trataElemento(elemento);
            } else {
                pilhaPosFixa.add(elemento);
            }
        }
        desempilha();

        return pilhaPosFixa;
    }

    private void desempilha() {
        int i;
        for (i = pilhaAux.size(); i > 0; i--) {
            pilhaPosFixa.add(pilhaAux.get(i));
            pilhaAux.remove(i);
        }
    }
    private void trataElemento(String elemento) {
        if(elemento.contains("+") || elemento.contains("-")){
            verificaPilha(elemento, 2);
        } else if(elemento.contains("*") || elemento.contains("div")) {
            verificaPilha(elemento, 1);

        } else if(elemento.contains(">") || elemento.contains("<") ||
                elemento.contains(">=") || elemento.contains("<=") ||
                elemento.contains("=") || elemento.contains("!")) {
            verificaPilha(elemento, 3);

        } else if(elemento.contains("e")) {
            verificaPilha(elemento, 4);

        } else if(elemento.contains("ou")){
            verificaPilha(elemento, 5);

        }
    }

    private void verificaPilha(String elemento, int prioridadeElemento) {
        int aux = 0;
        boolean flag = true;
        if(pilhaAux.size() != 0) {
            for (String expressao: pilhaAux) {
                int prioridadeAux = prioridade(expressao);
                if(prioridadeAux < prioridadeElemento) {
                    pilhaPosFixa.add(expressao);
                    flag = false;
                }
                aux++;
            }
        } else {
            pilhaAux.add(elemento);
            flag = false;
        }

        if(flag) {
            pilhaAux.add(elemento);
        }
    }

    private int prioridade(String expressao) {
        int i = 0;
        if(expressao.equals("+") || expressao.equals("-")){
            return 2;
        } else if(expressao.equals("*") || expressao.equals("div")) {
            return 1;
        } else if(expressao.equals(">") || expressao.equals("<") ||
                expressao.equals(">=") || expressao.equals("<=") ||
                expressao.equals("=") || expressao.equals("!")) {
            return 3;
        } else if(expressao.equals("e")) {
            return 4;
        } else if(expressao.equals("ou")){
            return 5;
        }
        return i;
    }

    private List<String> setup() {

        pilhaInFixa.add("a");
        pilhaInFixa.add("+");
        pilhaInFixa.add("b");
        pilhaInFixa.add("*");
        pilhaInFixa.add("c");
        pilhaInFixa.add("-");
        pilhaInFixa.add("10");

        return pilhaInFixa;
    }
}
