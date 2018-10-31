package puc.compiladores.lexico;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Lexico {

    private int controle = 0;
    private int linha = 1;
    Token t = new Token();
    private JTextArea textAreaErro;
    private JTextArea textAreaCodigo;
    private int qtdTokens;

    private final ArrayList<Character> characterArrayList;

    public Lexico(File arquivo, JTextArea textAreaError, JTextArea textAreaCod) throws Exception {
        qtdTokens = 0;
        textAreaErro = textAreaError;
        textAreaCodigo = textAreaCod;

        FileInputStream entrada = new FileInputStream(arquivo);
        InputStreamReader entradaf = new InputStreamReader(entrada);
        int c = entradaf.read();
        characterArrayList = new ArrayList<>();
        characterArrayList.clear();
        while(c!=-1)
        {
            characterArrayList.add((char)c);

            c = entradaf.read();
        }

        /*for (Token t :
                listaToken) {
            System.out.println(t.toString());
        }*/
    }

    public boolean isFileOver(int controleSintatico) {
        return controle == characterArrayList.size();
    }

    public Token getToken() throws LexicoException {
        while (controle < characterArrayList.size()) {
            t = null;
            if (characterArrayList.get(controle).toString().equals(" ") ||
                    characterArrayList.get(controle).toString().equals("") ||
                    characterArrayList.get(controle).toString().equals("\r") ||
                    characterArrayList.get(controle).toString().equals("\t"))
            {}
            else if(characterArrayList.get(controle).toString().equals("\n"))
            {
                System.out.println("Quebrou linha");
                linha++;
            }

            else if(characterArrayList.get(controle).toString().equals("{") ||
                    characterArrayList.get(controle).toString().equals("}"))
            {
                while(!characterArrayList.get(controle).toString().equals("}"))
                {

                    controle++;
                }
                try {
                    if (characterArrayList.get(controle+1).toString().equals("}")) {
                        throw LexicoException.erroLexico("Erro em comentarios", linha, textAreaErro, textAreaCodigo);
                    }
                } catch (IndexOutOfBoundsException e) {

                }
                controle++;
            }
            else if(Character.isDigit(characterArrayList.get(controle)))
            {
                t = trataDigito(characterArrayList);
            }
            else if(Character.isLetter(characterArrayList.get(controle))) {
                t = trataIdentificadorEPalavraReservada(characterArrayList);
            }
            else if(characterArrayList.get(controle).toString().equals(":")) {
                t = trataAtribuicao(characterArrayList);
            }
            else if (characterArrayList.get(controle).toString().equals("+") ||
                    characterArrayList.get(controle).toString().equals("-") ||
                    characterArrayList.get(controle).toString().equals("*")) {
                t = trataOperadorAritmetico(characterArrayList);
            }
            else if (characterArrayList.get(controle).toString().equals("<") ||
                    characterArrayList.get(controle).toString().equals(">") ||
                    characterArrayList.get(controle).toString().equals("=") ||
                    characterArrayList.get(controle).toString().equals("!")) {
                t = trataOperadorRelacional(characterArrayList);
            }
            else if(characterArrayList.get(controle).toString().equals(";") ||
                    characterArrayList.get(controle).toString().equals(",") ||
                    characterArrayList.get(controle).toString().equals("(") ||
                    characterArrayList.get(controle).toString().equals(")") ||
                    characterArrayList.get(controle).toString().equals(".")) {
                t = trataPontuacao(characterArrayList);
            } else {
                throw LexicoException.erroLexico("Caracter invalido " + characterArrayList.get(controle), linha, textAreaErro, textAreaCodigo);
            }
            if (t != null) {
                qtdTokens++;
                System.out.println("Está retornando o Token >>> " + t.toString());
                return t;
            }
            controle++;
        }
        return null;
    }

    private Token trataPontuacao(ArrayList<Character> characterArrayList) {
        Token t = new Token();
        StringBuilder pontuacao = new StringBuilder();

        String str;
        t.setLinha(linha);

        pontuacao.append(characterArrayList.get(controle));

        if(characterArrayList.get(controle).toString().equals(";"))
        {
            str = pontuacao.toString();
            t.setSimbolo(Simbolo.SPONTOVIRGULA.getName());
            t.setLexema(str);
        }

        if(characterArrayList.get(controle).toString().equals(","))
        {
            str = pontuacao.toString();
            t.setSimbolo(Simbolo.SVIRGULA.getName());
            t.setLexema(str);
        }

        if(characterArrayList.get(controle).toString().equals("("))
        {
            str = pontuacao.toString();
            t.setSimbolo(Simbolo.SABREPARENTESES.getName());
            t.setLexema(str);
        }

        if(characterArrayList.get(controle).toString().equals(")"))
        {
            str = pontuacao.toString();
            t.setSimbolo(Simbolo.SFECHAPARENTESES.getName());
            t.setLexema(str);
        }

        if(characterArrayList.get(controle).toString().equals("."))
        {
            str = pontuacao.toString();
            t.setSimbolo(Simbolo.SPONTO.getName());
            t.setLexema(str);
        }

        controle++;
        return t;
    }

    private Token trataOperadorRelacional(ArrayList<Character> characterArrayList) throws LexicoException {
        Token t = new Token();
        StringBuilder id = new StringBuilder();
        t.setLinha(linha);

        String str;

        id.append(characterArrayList.get(controle));

        if(characterArrayList.get(controle).toString().equals("="))
        {
            str = id.toString();
            t.setSimbolo(Simbolo.SIG.getName());
            t.setLexema(str);
            controle++;
        }

        if(characterArrayList.get(controle).toString().equals(">"))
        {
            controle++;

            if(characterArrayList.get(controle).toString().equals("="))
            {
                id.append(characterArrayList.get(controle));

                str = id.toString();
                t.setSimbolo(Simbolo.SMAIORIG.getName());
                t.setLexema(str);
                controle++;
            }
            else
            {
                str = id.toString();
                t.setSimbolo(Simbolo.SMAIOR.getName());
                t.setLexema(str);
            }
        }

        if(characterArrayList.get(controle).toString().equals("<"))
        {
            controle++;

            if(characterArrayList.get(controle).toString().equals("="))
            {
                id.append(characterArrayList.get(controle));

                str = id.toString();
                t.setSimbolo(Simbolo.SMENORIG.getName());
                t.setLexema(str);
                controle++;
            }
            else
            {
                str = id.toString();
                t.setSimbolo(Simbolo.SMENOR.getName());
                t.setLexema(str);
            }
        }

        if(characterArrayList.get(controle).toString().equals("!"))
        {
            controle++;

            if(characterArrayList.get(controle).toString().equals("="))
            {
                id.append(characterArrayList.get(controle));

                str = id.toString();
                t.setSimbolo("sdif");
                t.setLexema(str);
                t.setLinha(linha);
                controle++;
            }
            else
            {
                controle--;
                throw LexicoException.erroLexico("Caracter invalido " + characterArrayList.get(controle), linha, textAreaErro, textAreaCodigo);
            }
        }
        return t;
    }

    private Token trataDigito(ArrayList<Character> characterArrayList) {
        Token t = new Token();
        StringBuilder num = new StringBuilder();

        num.append(characterArrayList.get(controle));
        controle++; // le o próximo caracter

        while (Character.isDigit((char) characterArrayList.get(controle))) {
            num.append(characterArrayList.get(controle));
            controle++;
        }

        t.setLexema(num.toString());
        t.setSimbolo(Simbolo.SNUMERO.getName());
        t.setLinha(linha);

        return t;
    }

    private Token trataIdentificadorEPalavraReservada(ArrayList<Character> characterArrayList) {
        Token t = new Token();
        StringBuilder id = new StringBuilder();

        id.append(characterArrayList.get(controle));
        controle++; // le o próximo caracter

        while (Character.isLetter((char) characterArrayList.get(controle)) ||
                Character.isDigit((char) characterArrayList.get(controle)) ||
                characterArrayList.get(controle).toString().equals("_")) {
            id.append(characterArrayList.get(controle));
            controle++;
        }

        String str = id.toString();

        t.setLexema(str);

        switch (str) {
            case "programa":
                t.setSimbolo(Simbolo.SPROGRAMA.getName());
                break;
            case "se":
                t.setSimbolo(Simbolo.SSE.getName());
                break;
            case "entao":
                t.setSimbolo(Simbolo.SENTAO.getName());
                break;
            case "senao":
                t.setSimbolo(Simbolo.SSENAO.getName());
                break;
            case "enquanto":
                t.setSimbolo(Simbolo.SENQUANTO.getName());
                break;
            case "faca":
                t.setSimbolo(Simbolo.SFACA.getName());
                break;
            case "inicio":
                t.setSimbolo(Simbolo.SINICIO.getName());
                break;
            case "fim":
                t.setSimbolo(Simbolo.SFIM.getName());
                break;
            case "escreva":
                t.setSimbolo(Simbolo.SESCREVA.getName());
                break;
            case "leia":
                t.setSimbolo(Simbolo.SLEIA.getName());
                break;
            case "var":
                t.setSimbolo(Simbolo.SVAR.getName());
                break;
            case "inteiro":
                t.setSimbolo(Simbolo.SINTEIRO.getName());
                break;
            case "booleano":
                t.setSimbolo(Simbolo.SBOOLEANO.getName());
                break;
            case "procedimento":
                t.setSimbolo(Simbolo.SPROCEDIMENTO.getName());
                break;
            case "funcao":
                t.setSimbolo(Simbolo.SFUNCAO.getName());
                break;
            case "div":
                t.setSimbolo(Simbolo.SDIV.getName());
                break;
            case "e":
                t.setSimbolo(Simbolo.SE.getName());
                break;
            case "ou":
                t.setSimbolo(Simbolo.SOU.getName());
                break;
            case "nao":
                t.setSimbolo(Simbolo.SNAO.getName());
                break;
            default:
                t.setSimbolo(Simbolo.SIDENTIFICADOR.getName());
                break;
        }
        t.setLinha(linha);
        return t;
    }

    private Token trataAtribuicao(ArrayList<Character> characterArrayList) {
        Token t = new Token();
        StringBuilder palavra = new StringBuilder();

        String str;

        palavra.append(characterArrayList.get(controle));
        controle++; // le o próximo caracter
        t.setLinha(linha);

        if(characterArrayList.get(controle).toString().equals("="))
        {
            palavra.append(characterArrayList.get(controle));

            str = palavra.toString();

            t.setSimbolo(Simbolo.SATRIBUICAO.getName());
            t.setLexema(str);
            controle++;
        }
        else
        {
            str = palavra.toString();
            t.setSimbolo(Simbolo.SDOISPONTOS.getName());
            t.setLexema(str);

        }
        return t;
    }

    private Token trataOperadorAritmetico(ArrayList<Character> characterArrayList) {
        Token t = new Token();
        StringBuilder operador = new StringBuilder();

        String str;
        t.setLinha(linha);

        operador.append(characterArrayList.get(controle));

        if(characterArrayList.get(controle).toString().equals("+"))
        {
            str = operador.toString();
            t.setSimbolo(Simbolo.SMAIS.getName());
            t.setLexema(str);
        }

        if(characterArrayList.get(controle).toString().equals("-"))
        {
            str = operador.toString();
            t.setSimbolo(Simbolo.SMENOS.getName());
            t.setLexema(str);
        }

        if(characterArrayList.get(controle).toString().equals("*"))
        {
            str = operador.toString();
            t.setSimbolo(Simbolo.SMULT.getName());
            t.setLexema(str);
        }

        controle++;
        return t;
    }
}
