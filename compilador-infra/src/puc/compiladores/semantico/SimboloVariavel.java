package puc.compiladores.semantico;

public class SimboloVariavel extends Simbolo {

    /**
     * inteiro
     * booleano
     */
    private String tipoVariavel;
    private Integer posicao; // para LDVs

    public SimboloVariavel(String lexema, String nivel, String rotulo, String tipoVariavel, Integer posicao) {
        super(lexema, nivel, rotulo);
        this.tipoVariavel = tipoVariavel;
        this.posicao = posicao;
    }

    public SimboloVariavel(final String simboloVariavelLexema, final String lexema, final int posicaoVariaveis) {
        super(simboloVariavelLexema, "", "");
        this.tipoVariavel = lexema;
        this.posicao = posicaoVariaveis;
    }

    public String getTipoVariavel() {
        return tipoVariavel;
    }

    public void setTipoVariavel(String tipoVariavel) {
        this.tipoVariavel = tipoVariavel;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(final Integer posicao) {
        this.posicao = posicao;
    }

    @Override
    public String toString() {
        return "SimboloVariavel{" + "tipoVariavel='" + tipoVariavel + '\'' + ", posicao=" + posicao + "} " + super.toString();
    }
}
