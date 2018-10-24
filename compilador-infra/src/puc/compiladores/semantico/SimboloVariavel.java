package puc.compiladores.semantico;

public class SimboloVariavel extends Simbolo {

    /**
     * inteiro
     * booleano
     */
    public String tipoVariavel;


    public SimboloVariavel(String lexema, String nivel, String rotulo, String tipoVariavel) {
        super(lexema, nivel, rotulo);
        this.tipoVariavel = tipoVariavel;
    }

    public String getTipoVariavel() {
        return tipoVariavel;
    }

    public void setTipoVariavel(String tipoVariavel) {
        this.tipoVariavel = tipoVariavel;
    }

    @Override
    public String toString() {
        return super.toString() + ", SimboloVariavel{" +
                "tipoVariavel='" + tipoVariavel + '\'' +
                '}';
    }
}
