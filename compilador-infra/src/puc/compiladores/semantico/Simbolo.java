package puc.compiladores.semantico;

public class Simbolo {

    private String lexema;
    private String nivel;
    private String rotulo;

    public Simbolo(String lexema, String nivel, String rotulo) {
        this.lexema = lexema;
        this.nivel = nivel;
        this.rotulo = rotulo;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    @Override
    public String toString() {
        return "Simbolo{" +
                "lexema='" + lexema + '\'' +
                ", nivel='" + nivel + '\'' +
                ", rotulo='" + rotulo + '\'' +
                '}';
    }
}
