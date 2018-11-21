package puc.compiladores.semantico;

import puc.compiladores.lexico.Token;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTextArea;

public class Semantico {

    TabelaSimbolos tabelaSimbolos;

    /**
     * No momento de declarar variavel:
     * mesmo escopo: var com mesmo nome
     *
     * ate inicio da tab de simb: proc, funcao nome de prog com mesmo nome
     */

    private static ArrayList<String> OPERADORES_ARITMETICOS = new ArrayList<>(Arrays.asList(new String[] { "+", "-","*","div"}));
    private static ArrayList<String> OPERADORES_RELACIONAIS = new ArrayList<>(Arrays.asList(new String[] { ">", ">=","<","<=","!=","="}));
    private static ArrayList<String> OPERADORES_LOGICOS = new ArrayList<>(Arrays.asList(new String[] { "e", "ou","nao"}));


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

    /**
     *
     *
     * @param expressao
     * @param linha
     * @param textAreaErro
     * @param textAreaCodigo
     * @return
     * 0 para expressao booleana
     * 1 para expressao inteira
     */
    public Integer validaRetornoExpressao(final ArrayList expressao, final int linha, final JTextArea textAreaErro, final JTextArea textAreaCodigo) throws SemanticoException {
        ArrayList expressaoParam = expressao;
        int controle = 0;
        while (expressaoParam.size() !=1) {
            String s = String.valueOf(expressaoParam.get(controle));
            if (OPERADORES_ARITMETICOS.contains(s)) {
                String temp1 = String.valueOf(expressaoParam.get(controle-1));
                String temp2 = String.valueOf(expressaoParam.get(controle-2));
                try {
                    Integer result1 = Integer.parseInt(String.valueOf(temp1));
                    temp1 = "inteiro";
                } catch (NumberFormatException e) {}
                try {
                    Integer result2 = Integer.parseInt(String.valueOf(temp2));
                    temp2 = "inteiro";
                } catch (NumberFormatException e) {}

                if (temp1.equals("inteiro") && temp2.equals("inteiro")) {
                    expressaoParam.remove(controle);
                    expressaoParam.remove(controle-1);
                    expressaoParam.remove(controle-2);
                    expressaoParam.add(controle-2, "inteiro");
                    controle=0;
                } else {
                    throw SemanticoException.erroSemantico("Erro durante a expressao", linha-1, textAreaErro, textAreaCodigo);
                }


            }

            if (OPERADORES_RELACIONAIS.contains(s)) {
                String temp1 = String.valueOf(expressaoParam.get(controle-1));
                String temp2 = String.valueOf(expressaoParam.get(controle-2));

                try {
                    Integer result1 = Integer.parseInt(String.valueOf(temp1));
                    temp1 = "inteiro";
                } catch (NumberFormatException e) {}
                try {
                    Integer result2 = Integer.parseInt(String.valueOf(temp2));
                    temp2 = "inteiro";
                } catch (NumberFormatException e) {}

                if (temp1.equals("inteiro") && temp2.equals("inteiro")) {
                    expressaoParam.remove(controle);
                    expressaoParam.remove(controle-1);
                    expressaoParam.remove(controle-2);
                    expressaoParam.add(controle-2, "booleano");
                    controle=0;
                } else {
                    throw SemanticoException.erroSemantico("Erro durante a expressao", linha-1, textAreaErro, textAreaCodigo);
                }
            }

            if (OPERADORES_LOGICOS.contains(s)) {
                String temp1 = String.valueOf(expressaoParam.get(controle-1));
                String temp2 = String.valueOf(expressaoParam.get(controle-2));
                if (temp1.equals("booleano") && temp2.equals("booleano")) {
                    expressaoParam.remove(controle);
                    expressaoParam.remove(controle-1);
                    expressaoParam.remove(controle-2);
                    expressaoParam.add(controle-2, "booleano");
                    controle=0;
                } else {
                    throw SemanticoException.erroSemantico("Erro durante a expressao", linha-1, textAreaErro, textAreaCodigo);
                }
            }

            controle++;
        }
        return expressaoParam.get(0).equals("booleano") ? 0 : 1;
    }

    public String pegaTipoFuncao(String lexema) {
        return tabelaSimbolos.pegaTipoFuncao(lexema);
    }
}
