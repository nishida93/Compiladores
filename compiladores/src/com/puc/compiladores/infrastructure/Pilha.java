package com.puc.compiladores.infrastructure;

public class Pilha {

    private int[] memoria;
    private int topo;

    public Pilha() {
        this.memoria = new int[100];
        topo = 0;
    }

    public int getTopo() {
        return topo;
    }

    public void setTopo(int topo) {
        this.topo = topo;
    }

    public void inserePilha(int posicao, int valor) {
        this.memoria[posicao] = valor;
    }

    public void incrementaTopo() {
        this.topo++;
    }

    public void decrementaTopo() {
        this.topo--;
    }

    public int getValor(int posicao) {
        return this.memoria[posicao];
    }
}
