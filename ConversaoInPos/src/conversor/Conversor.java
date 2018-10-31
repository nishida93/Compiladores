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
            } else if(elemento.contains(")")){
                desempilhaParenteses();
            } else {
                pilhaPosFixa.add(elemento);
            }
        }
        desempilha(0);

        return pilhaPosFixa;
    }

    private void desempilhaParenteses() {
        int i;
        for (i = pilhaAux.size(); i > 0; i--) {
            pilhaPosFixa.add(pilhaAux.get(i - 1));
            if(pilhaPosFixa.get(i - 1).equals("(")){
                pilhaAux.remove(i - 1);
                pilhaPosFixa.remove(i - 1);
                break;
            }
            pilhaAux.remove(i - 1);
        }
    }

    private void desempilha(int parada) {
        int i;
        for (i = pilhaAux.size(); i > 0; i--) {
            pilhaPosFixa.add(pilhaAux.get(i - 1));
            if(prioridade(pilhaAux.get(i - 1)) == parada){
                pilhaAux.remove(i - 1);
                break;
            }
            pilhaAux.remove(i - 1);
        }
    }
    private void trataElemento(String elemento) {
        if(elemento.contains("+") || elemento.contains("-")){
            verificaPilha(elemento, 4);
        } else if(elemento.contains("*") || elemento.contains("div")) {
            verificaPilha(elemento, 5);

        } else if(elemento.contains(">") || elemento.contains("<") ||
                elemento.contains(">=") || elemento.contains("<=") ||
                elemento.contains("=") || elemento.contains("!")) {
            verificaPilha(elemento, 3);

        } else if(elemento.contains("e")) {
            verificaPilha(elemento, 2);

        } else if(elemento.contains("ou")){
            verificaPilha(elemento, 1);

        }
    }

    private void verificaPilha(String elemento, int prioridadeElemento) {
        int aux = 0;
        boolean flag = true;
        if(pilhaAux.size() != 0) {
            for (String expressao: pilhaAux) {
                int prioridadeAux = prioridade(expressao);
                if(prioridadeAux >= prioridadeElemento) {
                    desempilha(prioridadeElemento);
                    pilhaAux.add(elemento);
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
        if(expressao.equals("+") || expressao.equals("-")){
            return 4;
        } else if(expressao.equals("*") || expressao.equals("div")) {
            return 5;
        } else if(expressao.equals(">") || expressao.equals("<") ||
                expressao.equals(">=") || expressao.equals("<=") ||
                expressao.equals("=") || expressao.equals("!")) {
            return 3;
        } else if(expressao.equals("e")) {
            return 2;
        } else if(expressao.equals("ou")){
            return 1;
        }
        return 0;
    }

    private List<String> setup() {

        pilhaInFixa.add("(");
        pilhaInFixa.add("(");
        pilhaInFixa.add("a");
        pilhaInFixa.add("+");
        pilhaInFixa.add("b");
        pilhaInFixa.add(")");
        pilhaInFixa.add("-");
        pilhaInFixa.add("45");
        pilhaInFixa.add(")");
        pilhaInFixa.add("*");
        pilhaInFixa.add("c");
        pilhaInFixa.add("*");
        pilhaInFixa.add("10");

        return pilhaInFixa;
    }
}
