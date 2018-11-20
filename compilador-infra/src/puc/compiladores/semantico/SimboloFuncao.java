package puc.compiladores.semantico;

public class SimboloFuncao extends Simbolo {

    private String tipoFuncao;

    public SimboloFuncao(String lexema, String nivel, String rotulo) {
        super(lexema, nivel, rotulo);
    }

    public String getTipoFuncao() {
        return tipoFuncao;
    }

    public void setTipoFuncao(String tipoFuncao) {
        this.tipoFuncao = tipoFuncao;
    }

    @Override
    public String toString() {
        return "SimboloFuncao{" +
                "tipoFuncao='" + tipoFuncao + '\'' +
                "} " + super.toString();
    }
}
