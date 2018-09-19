package puc.compiladores.lexico;

public class Token {

    private String simbolo;
    private  String lexema;
    private int linha;

    public Token() {}

    public Token(String lexema,String simbolo) {
        this.simbolo = simbolo;
        this.lexema = lexema;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    @Override
    public String toString() {
        return "Token{" +
                "simbolo='" + simbolo + '\'' +
                ", lexema='" + lexema + '\'' +
                ", linha=" + linha +
                '}';
    }
}
