package com.puc.compiladores.infrastructure;

import static java.lang.Integer.parseInt;

public class Instrucoes {

    int s = 0;
    int i = 0;
    int pilha[];

    public Instrucoes(String instrucao, String parametro) {
        instrucao = instrucao.toUpperCase();
        switch (instrucao){
            case "LDC":
                ldcFunction(parametro);
                break;
            case "LDV":
                ldvFunction(parametro);
                break;
            case "STR":
                strFunction(parametro);
                break;
            case "JMP":
                jmpFunction(parametro);
                break;
            case "JMPF":
                jmpfFunction(parametro);
                break;
            case "CALL":
                callFunction(parametro);
                break;
        }
    }

    public Instrucoes(String instrucao){
        instrucao = instrucao.toUpperCase();
        switch (instrucao){
            case "ADD":
                addFunction();
                break;
            case "SUB":
                subFunction();
                break;
            case "MULT":
                multFunction();
                break;
            case "DIVI":
                diviFunction();
                break;
            case "INV":
                invFunction();
                break;
            case "AND":
                andFunction();
                break;
            case "OR":
                orFunction();
                break;
            case "NEG":
                negFunction();
                break;
            case "CME":
                cmeFunction();
                break;
            case "CMA":
                cmaFunction();
                break;
            case "CEQ":
                ceqFunction();
                break;
            case "CDIF":
                cdifFunction();
                break;
            case "CMEQ":
                cmeqFunction();
                break;
            case "CMAQ":
                cmaqFunction();
                break;
            case "START":
                startFunction();
                break;
            case "HLT":
                hltFunction();
                break;
            case "NULL":
                nullFunction();
                break;
            case "RD":
                rdFunction();
                break;
            case "PRN":
                prnFunction();
                break;
            case "RETURN":
                returnFunction();
                break;
        }
    }

    public Instrucoes(String instrucao, String primeiroParametro, String segundoParametro) {
        instrucao = instrucao.toUpperCase();
        switch (instrucao){
            case "ALLOC":
                allocFunction(primeiroParametro, segundoParametro);
                break;
            case "DALLOC":
                dallocFunction(primeiroParametro, segundoParametro);
                break;
        }
    }

    public void ldcFunction(String parametro) {
        s = s + 1;
        pilha[s] = parseInt(parametro);
    }

    public void ldvFunction(String parametro) {
        s = s + 1;
        pilha[s] = pilha[parseInt(parametro)];
    }

    public void addFunction() {
        pilha[s - 1] = pilha[s - 1] + pilha[s];
        s = s - 1;
    }

    public void subFunction() {
        pilha[s - 1] = pilha[s - 1] - pilha[s];
        s = s - 1;
    }

    public void multFunction() {
        pilha[s - 1] = pilha[s - 1] * pilha[s];
        s = s - 1;
    }

    public void diviFunction() {
        pilha[s - 1] = pilha[s - 1] / pilha[s];
        s = s - 1;
    }

    public void invFunction() {
        pilha[s] = -pilha[s];
    }

    public void andFunction() {
        if(1 == pilha[s] && 1 == pilha[s - 1]){
            pilha[s - 1] = 1;
        }else {
            pilha[s - 1] = 0;
        }
        s = s - 1;
    }

    public void orFunction() {
        if(1 == pilha[s] || 1 == pilha[s - 1]){
            pilha[s - 1] = 1;
        }else {
            pilha[s - 1] = 0;
        }
        s = s - 1;
    }

    public void negFunction() {
        pilha[s] = 1 - pilha[s];
    }

    public void cmeFunction() {
        if(pilha[s - 1] < pilha[s]){
            pilha[s - 1] = 1;
        }else {
            pilha[s - 1] = 0;
        }
        s = s - 1;
    }

    public void cmaFunction() {
        if(pilha[s - 1] > pilha[s]){
            pilha[s - 1] = 1;
        }else {
            pilha[s - 1] = 0;
        }
        s = s - 1;
    }

    public void ceqFunction() {
        if(pilha[s - 1] == pilha[s]){
            pilha[s - 1] = 1;
        }else {
            pilha[s - 1] = 0;
        }
        s = s - 1;
    }

    public void cdifFunction() {
        if(pilha[s - 1] != pilha[s]){
            pilha[s - 1] = 1;
        }else {
            pilha[s - 1] = 0;
        }
        s = s - 1;
    }

    public void cmeqFunction() {
        if(pilha[s - 1] <= pilha[s]){
            pilha[s - 1] = 1;
        }else {
            pilha[s - 1] = 0;
        }
        s = s - 1;
    }

    public void cmaqFunction() {
        if(pilha[s - 1] >= pilha[s]){
            pilha[s - 1] = 1;
        }else {
            pilha[s - 1] = 0;
        }
        s = s - 1;
    }

    public void startFunction() {
        s = s - 1;
    }

    public void hltFunction() {
        // fazer depois
    }

    public void strFunction(String parametro) {
        pilha[parseInt(parametro)] = pilha[s];
        s = s - 1;
    }

    public void jmpFunction(String parametro) {
        i = parseInt(parametro);
    }

    public void jmpfFunction(String parametro) {
        if(0 == pilha[s]) {
            i = parseInt(parametro);
        }else {
            i = i + 1;
        }
        s = s - 1;
    }

    public void nullFunction() {

    }

    public void rdFunction() {
        s = s + 1;
        //pilha[s] = //próximo valor de entrada;
    }

    public void prnFunction() {
        //printf pilha[s]
        s = s - 1;
    }

    public void allocFunction(String primeiroParametro, String segundoParametro) {
        int k = 0, n = 0;

        n = parseInt(segundoParametro);

        for(k = 0; k < n - 1; k++){
            s = s + 1;
            pilha[s] = pilha[parseInt(primeiroParametro) + k];
        }
    }

    public void dallocFunction(String primeiroParametro, String segundoParametro) {
        int k = 0, n = 0;

        n = parseInt(segundoParametro);

        for(k = n - 1; k < 0; k--){
            pilha[parseInt(primeiroParametro) + k] = pilha[s];
            s = s + 1;
        }
    }

    public void callFunction(String parametro) {
        s = s + 1;
        pilha[s] = i;
        i = parseInt(parametro);
    }

    public void returnFunction() {
        i = pilha[s];
        s = s - 1;
    }
}
