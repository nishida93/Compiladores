package puc.compiladores.sintatico;

import puc.compiladores.lexico.Lexico;
import puc.compiladores.lexico.Simbolo;
import puc.compiladores.lexico.Token;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Sintatico {

	private int controle;
	private Lexico lx;
	private Token tk;
	private JTextArea textArea;

	public Sintatico(File arquivo, JTextArea textAreaErro) throws Exception {

	    textArea = textAreaErro;
		lx = new Lexico(arquivo, textArea);
		//Thread.sleep(5000);
		controle = 0;

		while (lx.isTokenValid(controle)) {
			tk = sintaticoBuscaToken();
			if (tk.getSimbolo().equals(Simbolo.SPROGRAMA.getName())) {
				tk = sintaticoBuscaToken();
				if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
					tk = sintaticoBuscaToken();
					if (tk.getSimbolo().equals(Simbolo.SPONTOVIRGULA.getName())) {
						analisaBloco();
						if (tk.getSimbolo().equals(Simbolo.SPONTO.getName()) && !lx.isTokenValid(controle)) {
							System.out.println("SINTATICO EXECUTADO COM SUCESSO");
							textAreaErro.setText("EXECUTADO COM SUCESSO");
							textAreaErro.setForeground(Color.GREEN);
						} else {
							throw SintaticoException.erroFaltandoPonto(tk.getLinha(), textArea);
						}
					} else {
						throw SintaticoException.erroFaltandoPontoVirgula(tk.getLinha(), textArea);
					}
				} else {
					throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textArea);
				}
			} else {
				throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textArea);
			}
		}
	}

	private Token sintaticoBuscaToken() {
		Token t = lx.getToken(controle);
		incrementaControle();
		return t;
	}

	private void incrementaControle() {
		controle++;
	}

	private void analisaBloco() throws SintaticoException {
		tk = sintaticoBuscaToken();
		analisaEtVariaveis();
		analisaSubRotinas();
		analisaComandos();
	}

	private void analisaEtVariaveis() throws SintaticoException {
		if (tk.getSimbolo().equals(Simbolo.SVAR.getName())) {
			tk = sintaticoBuscaToken();
			if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
				while (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
					analisaVariaveis();
					if (tk.getSimbolo().equals(Simbolo.SPONTOVIRGULA.getName())) {
						tk = sintaticoBuscaToken();
					} else {
						throw SintaticoException.erroFaltandoPontoVirgula(tk.getLinha(), textArea);
					}
				}
			} else {
				throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textArea);
			}
		}
	}

	private void analisaVariaveis() throws SintaticoException {
		while(!tk.getSimbolo().equals(Simbolo.SDOISPONTOS.getName())) {
			if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
				tk = sintaticoBuscaToken();
				if (tk.getSimbolo().equals(Simbolo.SVIRGULA.getName()) || tk.getSimbolo().equals(Simbolo.SDOISPONTOS.getName())) {
					if (tk.getSimbolo().equals(Simbolo.SVIRGULA.getName())) {
						tk = sintaticoBuscaToken();
						if (tk.getSimbolo().equals(Simbolo.SDOISPONTOS.getName())) {
							throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textArea);
						}
					}
				} else {
					throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textArea);
				}
			} else {
				throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textArea);
			}
		}
		tk = sintaticoBuscaToken();
		analisaTipo();
	}

	private void analisaTipo() throws SintaticoException {
		if (!tk.getSimbolo().equals(Simbolo.SINTEIRO.getName()) && !tk.getSimbolo().equals(Simbolo.SBOOLEANO.getName())) {
			throw SintaticoException.erroSintatico("Tipo de variavel invalido", tk.getLinha(), textArea);
		} else {
			tk = sintaticoBuscaToken();
		}
	}

	private void analisaComandos() throws SintaticoException {

		if (tk.getSimbolo().equals(Simbolo.SINICIO.getName())) {
			tk = sintaticoBuscaToken();
			analisaComandoSimples();
			while (!tk.getSimbolo().equals(Simbolo.SFIM.getName())) {
				if (tk.getSimbolo().equals(Simbolo.SPONTOVIRGULA.getName())) {
					tk = sintaticoBuscaToken();
                    if (!tk.getSimbolo().equals(Simbolo.SFIM.getName())) {
						analisaComandoSimples();
					}
				} else {
					throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textArea);
				}
			}
			tk = sintaticoBuscaToken();
		} else {
			throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textArea);
		}
	}

	private void analisaComandoSimples() throws SintaticoException {
		if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
			analisaAtribChProcedimento();
		} else if (tk.getSimbolo()
					 .equals(Simbolo.SSE.getName())) {
			analisaSe();
		} else if (tk.getSimbolo()
					 .equals(Simbolo.SENQUANTO.getName())) {
			analisaEnquanto();
		} else if (tk.getSimbolo()
					 .equals(Simbolo.SLEIA.getName())) {
			analisaLeia();
		} else if (tk.getSimbolo()
					 .equals(Simbolo.SESCREVA.getName())) {
			analisaEscreva();
		} else {
			analisaComandos();
		}
	}

	private void analisaAtribChProcedimento() throws SintaticoException {
		tk = sintaticoBuscaToken();
		if (tk.getSimbolo().equals(Simbolo.SATRIBUICAO.getName())) {
			analisaAtribuicao();
		} else {
			analisaChamadaProcedimento();
		}
	}

	private void analisaLeia() throws SintaticoException {
		tk = sintaticoBuscaToken();
		if (tk.getSimbolo().equals(Simbolo.SABREPARENTESES.getName())) {
			tk = sintaticoBuscaToken();
			if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
				tk = sintaticoBuscaToken();
				if (tk.getSimbolo().equals(Simbolo.SFECHAPARENTESES.getName())) {
					tk = sintaticoBuscaToken();
				} else {
					throw SintaticoException.erroSintatico("[ANALISA LEIA] Faltou fechar parenteses", tk.getLinha(), textArea);
				}
			} else {
				throw SintaticoException.erroSintatico("[ANALISA LEIA] Faltou passar identificador como parametro", tk.getLinha(), textArea);
			}
		} else {
			throw SintaticoException.erroSintatico("[ANALISA LEIA] Faltou abrir parenteses", tk.getLinha(), textArea);
		}
	}

	private void analisaEscreva() throws SintaticoException {
		tk = sintaticoBuscaToken();
		if (tk.getSimbolo().equals(Simbolo.SABREPARENTESES.getName())) {
			tk = sintaticoBuscaToken();
			if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
				tk = sintaticoBuscaToken();
				if (tk.getSimbolo().equals(Simbolo.SFECHAPARENTESES.getName())) {
					tk = sintaticoBuscaToken();
				} else {
					throw SintaticoException.erroSintatico("[ANALISA ESCREVA] Faltou fechar parenteses", tk.getLinha(), textArea);
				}
			} else {
				throw SintaticoException.erroSintatico("[ANALISA ESCREVA] Faltou passar identificador como parametro", tk.getLinha(), textArea);
			}
		} else {
			throw SintaticoException.erroSintatico("[ANALISA ESCREVA] Faltou abrir parenteses", tk.getLinha(), textArea);
		}
	}

	private void analisaEnquanto() throws SintaticoException {
		tk = sintaticoBuscaToken();
		analisaExpressao();
		if (tk.getSimbolo().equals(Simbolo.SFACA.getName())) {
			tk = sintaticoBuscaToken();
			analisaComandoSimples();
		} else {
			throw SintaticoException.erroSintatico("[ANALISA ENQUANTO] Faltou a palavra 'faca'", tk.getLinha(), textArea);
		}
	}

	private void analisaSe() throws SintaticoException {
		tk = sintaticoBuscaToken();
		analisaExpressao();
		if (tk.getSimbolo().equals(Simbolo.SENTAO.getName())) {
			tk = sintaticoBuscaToken();
			analisaComandoSimples();
			if (tk.getSimbolo().equals(Simbolo.SSENAO.getName())) {
				tk = sintaticoBuscaToken();
				analisaComandoSimples();
			}
		} else {
			throw SintaticoException.erroSintatico("[ANALISA SE] Faltou a palavra 'entao'", tk.getLinha(), textArea);
		}
	}

	private void analisaSubRotinas() throws SintaticoException {
		// int flag = 0;
		if (tk.getSimbolo().equals(Simbolo.SPROCEDIMENTO.getName()) ||
				tk.getSimbolo().equals(Simbolo.SFUNCAO.getName())) {
			// implementacao do semantico
		}
		while (tk.getSimbolo().equals(Simbolo.SPROCEDIMENTO.getName()) ||
				tk.getSimbolo().equals(Simbolo.SFUNCAO.getName())) {
			if (tk.getSimbolo().equals(Simbolo.SPROCEDIMENTO.getName())) {
				analisaDeclaracaoProcedimento();
			} else {
				analisaDeclaracaoFuncao();
			}

			if (tk.getSimbolo().equals(Simbolo.SPONTOVIRGULA.getName())) {
				tk = sintaticoBuscaToken();
			} else {
				throw SintaticoException.erroFaltandoPontoVirgula(tk.getLinha(), textArea);
			}
		}
	}

	private void analisaDeclaracaoProcedimento() throws SintaticoException {
			tk = sintaticoBuscaToken();
		if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
			tk = sintaticoBuscaToken();
			if (tk.getSimbolo().equals(Simbolo.SPONTOVIRGULA.getName())) {
				analisaBloco();
			} else {
				throw SintaticoException.erroFaltandoPontoVirgula(tk.getLinha(), textArea);
			}
		} else {
			throw SintaticoException.erroSintatico("Faltou declarar nome do procedimento", tk.getLinha(), textArea);
		}
	}

	private void analisaDeclaracaoFuncao() throws SintaticoException {
		tk = sintaticoBuscaToken();
		if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
			tk = sintaticoBuscaToken();
			if (tk.getSimbolo().equals(Simbolo.SDOISPONTOS.getName())) {
				tk = sintaticoBuscaToken();
				if (tk.getSimbolo().equals(Simbolo.SINTEIRO.getName()) ||
				tk.getSimbolo().equals(Simbolo.SBOOLEANO.getName())) {
					tk = sintaticoBuscaToken();
					if (tk.getSimbolo().equals(Simbolo.SPONTOVIRGULA.getName())) {
						analisaBloco();
					}
				} else {
					throw SintaticoException.erroSintatico("Faltou declarar tipo da funcao", tk.getLinha(), textArea);
				}
			} else {
				throw SintaticoException.erroSintatico("Faltou caracter ':'", tk.getLinha(), textArea);
			}
		} else {
			throw SintaticoException.erroSintatico("Fatlou declarar nome da funcao", tk.getLinha(), textArea);
		}
	}

	private void analisaExpressao() throws SintaticoException {
		analisaExpressaoSimples();
		if (tk.getSimbolo().equals(Simbolo.SMAIOR.getName()) ||
				tk.getSimbolo().equals(Simbolo.SMAIORIG.getName()) ||
                tk.getSimbolo().equals(Simbolo.SIG.getName()) ||
				tk.getSimbolo().equals(Simbolo.SMENOR.getName()) ||
				tk.getSimbolo().equals(Simbolo.SMENORIG.getName()) ||
				tk.getSimbolo().equals(Simbolo.SDIF.getName())) {
			tk = sintaticoBuscaToken();
			analisaExpressaoSimples();
		}

	}

	private void analisaExpressaoSimples() throws SintaticoException {
		if (tk.getSimbolo().equals(Simbolo.SMAIS.getName()) ||
				tk.getSimbolo().equals(Simbolo.SMENOS.getName())) {
            tk = sintaticoBuscaToken();
        }
        analisaTermo();
        while (tk.getSimbolo().equals(Simbolo.SMAIS.getName()) ||
                tk.getSimbolo().equals(Simbolo.SMENOS.getName()) ||
                tk.getSimbolo().equals(Simbolo.SOU.getName())) {
            tk = sintaticoBuscaToken();
            analisaTermo();
        }

	}

	private void analisaTermo() throws SintaticoException {
		analisaFator();
		while (tk.getSimbolo().equals(Simbolo.SMULT.getName()) ||
				tk.getSimbolo().equals(Simbolo.SDIV.getName()) ||
				tk.getSimbolo().equals(Simbolo.SE.getName())) {
			tk = sintaticoBuscaToken();
			analisaFator();
		}
	}

	private void analisaFator() throws SintaticoException {
		if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
			analisaChamadaFuncao();
		} else if (tk.getSimbolo().equals(Simbolo.SNUMERO.getName())) {
			tk = sintaticoBuscaToken();
		} else if (tk.getSimbolo().equals(Simbolo.SNAO.getName())) {
			tk = sintaticoBuscaToken();
			analisaFator();
		} else if (tk.getSimbolo().equals(Simbolo.SABREPARENTESES.getName())) {
			tk = sintaticoBuscaToken();
			analisaExpressao();
			if (tk.getSimbolo().equals(Simbolo.SFECHAPARENTESES.getName())) {
				tk = sintaticoBuscaToken();
			} else {
				throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textArea);
			}
		} else if (tk.getLexema().equals("verdadeiro") ||
				tk.getLexema().equals("falso")) {
			tk = sintaticoBuscaToken();
		} else {
			throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textArea);
		}
	}

	private void analisaChamadaFuncao() {
		tk = sintaticoBuscaToken();
	}

	private void analisaChamadaProcedimento() {

	}

	private void analisaAtribuicao() throws SintaticoException {
        tk = sintaticoBuscaToken();

        analisaExpressao();
	}
}
