package com.puc.compiladores.infrastructure;

public class Instrucoes {

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

    }

    public void ldvFunction(String parametro) {

    }

    public void addFunction() {

    }

    public void subFunction() {

    }

    public void multFunction() {

    }

    public void diviFunction() {

    }

    public void invFunction() {

    }

    public void andFunction() {

    }

    public void orFunction() {

    }

    public void negFunction() {

    }

    public void cmeFunction() {

    }

    public void cmaFunction() {

    }

    public void ceqFunction() {

    }

    public void cdifFunction() {

    }

    public void cmeqFunction() {

    }

    public void cmaqFunction() {

    }

    public void startFunction() {

    }

    public void hltFunction() {

    }

    public void strFunction(String parametro) {

    }

    public void jmpFunction(String parametro) {

    }

    public void jmpfFunction(String parametro) {

    }

    public void nullFunction() {

    }

    public void rdFunction() {

    }

    public void prnFunction() {

    }

    public void allocFunction(String primeiroParametro, String segundoParametro) {

    }

    public void dallocFunction(String primeiroParametro, String segundoParametro) {

    }

    public void callFunction(String parametro) {

    }

    public void returnFunction() {

    }
}
