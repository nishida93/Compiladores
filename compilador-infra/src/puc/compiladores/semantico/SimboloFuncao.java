package puc.compiladores.semantico;

public class SimboloFuncao extends Simbolo {

    private String tipo;

    public SimboloFuncao(String lexema, String nivel, String rotulo) {
        super(lexema, nivel, rotulo);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "SimboloFuncao{" +
                "tipo='" + tipo + '\'' +
                "} " + super.toString();
    }
}
