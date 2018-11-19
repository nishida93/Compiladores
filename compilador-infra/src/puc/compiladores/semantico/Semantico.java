package puc.compiladores.semantico;

import puc.compiladores.lexico.Token;

public class Semantico {

    TabelaSimbolos tabelaSimbolos;

    /**
     * No momento de declarar variavel:
     * mesmo escopo: var com mesmo nome
     *
     * ate inicio da tab de simb: proc, funcao nome de prog com mesmo nome
     */



    public Semantico() {
        tabelaSimbolos = new TabelaSimbolos();
    }

    public boolean existeDuplicidadeVariavel(final String lexema) {
        return tabelaSimbolos.verificaDuplicidade(lexema);
    }
    /**
     * Pesquisa declaracao de variavel na tabela de simbolos
     *
     * @return
     * true se variavel for encontrada na tabela de simbolos
     * false se nao for encontrada
     */
    public boolean pesquisaDeclaracaoVariavelTabelaSimbolos(final String lexema) {
        return tabelaSimbolos.existeVariavel(lexema);
    }

    /**
     * Pesquisa declaracao de funcao na tabela de simbolos
     *
     * @return
     * true se funcao for encontrada na tabela de simbolos
     * false se nao for encontrada
     */
    public boolean pesquisaDeclaracaoFuncaoTabelaSimbolos(final String lexema) {
        return tabelaSimbolos.existeFuncao(lexema);
    }

    /**
     * Pesquisa declaracao de procedimento na tabela de simbolos
     *
     * @return
     * true se procedimento for encontrado na tabela de simbolos
     * false se nao for encontrado
     */
    public boolean pesquisaDeclaracaoProcedimentoTabelaSimbolos(final String lexema) {
        return tabelaSimbolos.existeProcedimento(lexema);
    }

    /**
     * Pesquisa duplicidade de variavel na tabela de simbolos
     *
     * @return
     * true se houver duplicidade
     * false se nao houver duplicidade
     */
    public boolean pesquisaDuplicidadeVariavel(final String lexema) {
        return tabelaSimbolos.existeVariavel(lexema);
    }

    /**
     * Insere na tabela de simbolos
     *
     * @param simbolo
     */
    public void insereTabelaSimbolos(Simbolo simbolo) {
        tabelaSimbolos.empilha(simbolo);
    }

    /**
     * TODO: apagar o método, uma vez que é apenas para debug
     * Imprime toda a tabela de simbolos
     */
    public void imprime() {
        tabelaSimbolos.imprime();
    }

    /**
     * Coloca tipo na tabela de simbolos
     *
     * @param lexema
     * @param tipo
     */
    public void colocaTipoTabela(final String lexema, final String tipo) {
        tabelaSimbolos.colocaTipo(lexema, tipo);
    }

    public void desempilhaSimbolos(final String lexemaEscopo) {
        tabelaSimbolos.desempilha(lexemaEscopo);
    }

    public boolean isTipoInteiro(final String lexema) {
        return tabelaSimbolos.verificaSeTipoInteiro(lexema);
    }

    public void printaVariaveis() {
        tabelaSimbolos.printaVariaveis();
    }

    public String buscaPosicaoSimbolo(final Token tk) {
        return tabelaSimbolos.buscaPosicaoSimbolo(tk);
    }

    public String buscaRotuloProcedimento(final String lexema) {
        return tabelaSimbolos.buscaRotuloProcedimento(lexema);
    }

    public String buscaRotuloFuncao(final String lexema) {
        return tabelaSimbolos.buscaRotuloFuncao(lexema);
    }

    public String pegaTipoVariavel(final String lexema) {
        return tabelaSimbolos.pegaTipoVariavel(lexema);
    }
}
