package puc.compiladores.semantico;

import java.util.ArrayList;
import java.util.Collections;

public class TabelaSimbolos {

    private ArrayList<Simbolo> pilha;

    public TabelaSimbolos() {
        pilha = new ArrayList<>();
    }

    public void empilha(Simbolo o) {
        System.out.println("Empilhando o simbolo >>> " + o.toString());
        pilha.add(o);
    }

    public boolean verificaDuplicidade(final String lexema) {
        for (Simbolo simbolo: pilha) {
            if (simbolo.getLexema().equals(lexema) && (simbolo instanceof SimboloFuncao || simbolo instanceof SimboloProcedimento || simbolo instanceof SimboloPrograma)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeVariavel(final String lexema) {
        Collections.reverse(pilha);
        for (Simbolo simbolo :
                pilha) {
            //System.out.println("EXISTE VARIAVEL COM SIMBOLO > " + simbolo.toString());
            if (simbolo.getLexema().equals(lexema)) {
                Collections.reverse(pilha);
                return true;
            }
            if (simbolo instanceof SimboloFuncao || simbolo instanceof SimboloProcedimento || simbolo instanceof SimboloPrograma) {
                continue;
            }
        }
        Collections.reverse(pilha);
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

    public void desempilha(final String lexemaEscopo) {
        ArrayList<Simbolo> tmpToRemove = new ArrayList<>();
        Collections.reverse(pilha);
        System.out.println("Desempilhando ate LEXEMA >" + lexemaEscopo);
        for (Simbolo simbolo: pilha) {
            if (simbolo.getLexema().equals(lexemaEscopo)) {
                break;
            } /*else if (simbolo instanceof SimboloPrograma || simbolo instanceof SimboloProcedimento || simbolo instanceof SimboloFuncao) {
                continue;
            }*/
            System.out.println("Desempilhando o simbolo >> " + simbolo.toString());
            tmpToRemove.add(simbolo);
        }
        pilha.removeAll(tmpToRemove);
        Collections.reverse(pilha);
        imprime();
    }

    public boolean verificaSeTipoInteiro(final String lexema) {
        for (Simbolo simbolo : pilha) {
            if (simbolo.getLexema()
                       .equals(lexema) && simbolo instanceof SimboloVariavel && ((SimboloVariavel) simbolo).getTipoVariavel()
                                                                                                           .equals("inteiro")) {
                return true;
            }
        }
        return false;
    }
}
