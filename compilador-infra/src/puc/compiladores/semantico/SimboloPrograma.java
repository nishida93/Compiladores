package puc.compiladores.semantico;

public class SimboloPrograma extends Simbolo {


    public SimboloPrograma(String lexema, String nivel, String rotulo) {
        super(lexema, nivel, rotulo);
    }

    public boolean verificaTipo(Object o) {
        return o instanceof SimboloPrograma;
    }
}
