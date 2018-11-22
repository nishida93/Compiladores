package puc.compiladores.semantico;

import java.util.ArrayList;
import java.util.Collections;

import puc.compiladores.lexico.Token;

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
        Collections.reverse(pilha);
        for (Simbolo simbolo: pilha) {
            if (simbolo.getLexema().equals(lexema)) {
                Collections.reverse(pilha);
                return true;
            } else if (simbolo instanceof SimboloFuncao || simbolo instanceof SimboloProcedimento || simbolo instanceof SimboloPrograma) {
                break;
            }
        }
        Collections.reverse(pilha);
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
            } else if (simbolo instanceof SimboloFuncao || simbolo instanceof SimboloProcedimento || simbolo instanceof SimboloPrograma) {
                continue;
            }
        }
        Collections.reverse(pilha);
        return false;
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
			} else if (simbolo.getLexema().equals(lexema) && simbolo instanceof SimboloFuncao) {
                ((SimboloFuncao) simbolo).setTipoFuncao(tipo);
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

    public void printaVariaveis() {
        for (Simbolo simbolo : pilha) {
            if (simbolo instanceof SimboloVariavel) {
                System.out.println("PRINT VARIAVEL >>> " + simbolo.toString());
            }
        }
    }

    public String buscaPosicaoSimbolo(final Token tk) {
        for (Simbolo simbolo : pilha) {
            if (simbolo instanceof SimboloVariavel && simbolo.getLexema().equals(tk.getLexema())) {
                return String.valueOf(((SimboloVariavel) simbolo).getPosicao());
            }
        }
        return null;
    }

    public String buscaRotuloProcedimento(final String lexema) {
        for (Simbolo simbolo : pilha) {
            if (simbolo instanceof SimboloProcedimento && simbolo.getLexema().equals(lexema)) {
                return String.valueOf(simbolo.getRotulo());
            }
        }
        return null;
    }

    public String buscaRotuloFuncao(final String lexema) {
        for (Simbolo simbolo : pilha) {
            if (simbolo instanceof SimboloFuncao && simbolo.getLexema().equals(lexema)) {
                return String.valueOf(simbolo.getRotulo());
            }
        }
        return null;
    }

    public String pegaTipoVariavel(final String lexema) {
        for (Simbolo simbolo : pilha) {
            if (simbolo instanceof SimboloVariavel && simbolo.getLexema().equals(lexema)) {
                return String.valueOf(((SimboloVariavel) simbolo).getTipoVariavel());
            }
        }
        return null;
    }

    public String pegaTipoFuncao(String lexema) {
        for (Simbolo simbolo : pilha) {
            if (simbolo instanceof SimboloFuncao && simbolo.getLexema().equals(lexema)) {
                return String.valueOf((((SimboloFuncao) simbolo).getTipoFuncao()));
            }
        }
        return null;
    }
}
