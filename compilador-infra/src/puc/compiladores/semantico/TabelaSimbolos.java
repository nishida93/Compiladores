package puc.compiladores.semantico;

import java.util.ArrayList;

public class TabelaSimbolos {

    private ArrayList<Simbolo> pilha;

    public TabelaSimbolos() {
        pilha = new ArrayList<>();
    }

    public void add(Simbolo o) {
        pilha.add(o);
    }

    public boolean existeVariavel(final String lexema) {
        for (Simbolo simbolo :
                pilha) {
            if (simbolo.getLexema().equals(lexema) && simbolo instanceof SimboloVariavel) {
                return true;
            }
        }
        return false;
    }

    public void imprime() {
        for (Simbolo valor:
             pilha) {
            if (valor instanceof SimboloPrograma) {
                System.out.println("Simbolo programa");
            } else if (valor instanceof SimboloProcedimento) {
                System.out.println("Simbolo procedimento");
            } else if (valor instanceof SimboloFuncao) {
                System.out.println("Simbolo funcao");
            } else if (valor instanceof SimboloVariavel) {
                System.out.println("Simbolo variavel");
            }
            System.out.println("VALORES NA PILHA >>> " + valor.toString());
        }
    }

    public boolean existeFuncao(final String lexema) {
        for (Simbolo simbolo :
                pilha) {
            if (simbolo.getLexema().equals(lexema) && simbolo instanceof SimboloFuncao) {
                return true;
            }
        }

        return false;
    }

    public boolean existeProcedimento(final String lexema) {
        for (Simbolo simbolo :
                pilha) {
            if (simbolo.getLexema().equals(lexema) && simbolo instanceof SimboloProcedimento) {
                return true;
            }
        }

        return false;
    }

	public void colocaTipo(final String lexema, final String tipo) {
		for (Simbolo simbolo :
				pilha) {
			if (simbolo.getLexema().equals(lexema) && simbolo instanceof SimboloVariavel) {
				((SimboloVariavel) simbolo).setTipoVariavel(tipo);
			}
		}
	}
}
