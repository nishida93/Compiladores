package puc.compiladores.lexico;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Lexico {

    private static ArrayList<Token> listaToken = new ArrayList();
    private int controle = 0;
    private int linha = 1;
    Token t = new Token();
    private char[] test;

    public Lexico() throws Exception {
        FileInputStream entrada = new FileInputStream(new File("/home/matheus/Desktop/teste1.txt"));
        InputStreamReader entradaf = new InputStreamReader(entrada);
        int c = entradaf.read();
        ArrayList<Character> characterArrayList = new ArrayList<>();
        characterArrayList.clear();
        while(c!=-1)
        {
            characterArrayList.add((char)c);

            c = entradaf.read();
        }


        /*for (Character test :
                characterArrayList) {
            System.out.println(test + "\n");

        }
        System.out.println("SIZE IS >>> "+characterArrayList.size());*/

        while (controle < characterArrayList.size()) {
            t = null;
            System.out.println("Entrou no while com caracter >>> " + characterArrayList.get(controle).toString());
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

            else if(characterArrayList.get(controle).toString().equals("{"))
            {
                while(!characterArrayList.get(controle).toString().equals("}"))
                {

                    controle++;
                }
                System.out.println("Fechou o meninao");
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
                throw LexicoException.erroLexico("Caracter invalido " + characterArrayList.get(controle), controle + 1);
            }
            if (t != null) {
                System.out.println("Inserindo o Token: " + t.toString());
                listaToken.add(t);
            } else {
                controle++;
                System.out.println("Skippando pois nao tem token valido");
            }
        }

        for (Token t :
                listaToken) {
            System.out.println(t.toString());
        }
        /*
        String content = new String(Files.readAllBytes(Paths.get("/home/matheus/Desktop/teste1.txt")));
        System.out.println("CONTEUDO >>> "+content);

        test = content.toCharArray();

        // Para cada caracter no arquivo
        for (Character out : test) {
            System.out.println(out+"\n");
            //listaToken.add(new Token("asasa",Simbolo.SATRIBUICAO.getName()));
            if (Character.isDigit(out)) {
                trataDigito();
            }
            if (out == '\n') {
                System.out.println("Quebrou linha");
                linha++;
            }
        }

        System.out.println("O arquivo de entrada tem " + linha + " linhas.");*/
    }

    private Token trataPontuacao(ArrayList<Character> characterArrayList) {
        Token t = new Token();
        StringBuilder pontuacao = new StringBuilder();

        String str;

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
                controle++;
            }
            else
            {
                controle--;
                throw LexicoException.erroLexico("Caracter invalido " + characterArrayList.get(controle), controle + 1);
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

        String str = num.toString();

        return new Token(str, Simbolo.SNUMERO.getName());
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

        System.out.println("Setando lexema para > " + str);
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
            /*case "verdadeiro":
                t.setSimbolo(Simbolo.);
                break;
            case "falso":
                t.setSimbolo("sfalso");
                break;*/
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
        return t;
    }

    private Token trataAtribuicao(ArrayList<Character> characterArrayList) {
        Token t = new Token();
        StringBuilder palavra = new StringBuilder();

        String str;

        palavra.append(characterArrayList.get(controle));
        controle++; // le o próximo caracter

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
