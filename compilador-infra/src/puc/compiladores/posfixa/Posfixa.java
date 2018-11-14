package puc.compiladores.posfixa;

import java.util.ArrayList;

public class Posfixa {

    private ArrayList<String> arrayInfixa = new ArrayList<>();
    private ArrayList<String> arrayPosfixa = new ArrayList<>();
    private ArrayList<String> arrayAux = new ArrayList<>();

    public Posfixa() { }

    public ArrayList<String> trataPofixa(ArrayList<String> arrayExpressao) {

        arrayInfixa = arrayExpressao;

        trataPilha();

        return arrayPosfixa;
    }

    private ArrayList<String> trataPilha() {

        String aux = "";
        for (String elemento: arrayInfixa) {
            if(elemento.equals("*") || elemento.equals("div") ||
                    elemento.equals("+") || elemento.equals("-") ||
                    elemento.equals(">") || elemento.equals("<") ||
                    elemento.equals(">=") || elemento.equals("<=") ||
                    elemento.equals("=") || elemento.equals("!") ||
                    elemento.equals("e") || elemento.equals("ou")) {
                trataElemento(elemento);
            } else if (elemento.equals("(")) {
                arrayAux.add(elemento);
            } else if (elemento.equals(")")) {
                removeParenteses();
            } else if (elemento.equals("+u") || elemento.equals("-u") ||
                    elemento.equals("nao")) {
                aux = elemento;
            } else {
                arrayPosfixa.add(elemento);
                if(elemento.equals("+u") || elemento.equals("-u") ||
                        elemento.equals("nao")){
                    arrayAux.add(aux);
                    aux = "";
                }
            }
        }
        desempilhaResto();
        return arrayPosfixa;
    }

    private void desempilhaResto() {
        int i;
        for (i = arrayAux.size() - 1; i >= 0; i--) {
            arrayPosfixa.add(arrayAux.get(i));
            arrayAux.remove(i);
        }
    }

    private void removeParenteses() {
        int i;
        for (i = arrayAux.size() - 1; i >= 0; i--) {
            if(arrayAux.get(i).equals("(")) {
                arrayAux.remove(i);
                break;
            } else {
                arrayPosfixa.add(arrayAux.get(i));
                arrayAux.remove(i);
            }
        }
    }

    private void trataElemento(String expressao) {
        for (String elemento: arrayAux) {
            if(elemento.equals("(")){
                break;
            } else if(pegaPrioridade(elemento) >= pegaPrioridade(expressao)) {
                desempilha(expressao);
                break;
            }
        }
        arrayAux.add(expressao);
    }

    private void desempilha(String expressao) {
        int i;
        for (i = arrayAux.size() - 1; i >= 0; i--) {
            if(arrayAux.get(i).equals("(")) {
                break;
            }else if(pegaPrioridade(arrayAux.get(i)) <= pegaPrioridade(expressao)) {
                arrayPosfixa.add(arrayAux.get(i));
                arrayAux.remove(i);
                break;
            }
        }
    }

    private int pegaPrioridade(String elemento){
        if(elemento.equals("*") || elemento.equals("div")) {
            return 5;
        } else if (elemento.equals("+") || elemento.equals("-")) {
            return 4;
        } else if (elemento.equals(">") || elemento.equals("<") ||
                elemento.equals(">=") || elemento.equals("<=") ||
                elemento.equals("=") || elemento.equals("!")) {
            return 3;
        } else if (elemento.equals("e")) {
            return 2;
        } else if (elemento.equals("ou")) {
            return 1;
        }
        return 0;
    }
}
