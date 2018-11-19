package puc.compiladores.sintatico;

import puc.compiladores.geracao.GeracaoCodigo;
import puc.compiladores.lexico.Lexico;
import puc.compiladores.lexico.LexicoException;
import puc.compiladores.lexico.Simbolo;
import puc.compiladores.lexico.Token;
import puc.compiladores.posfixa.Posfixa;
import puc.compiladores.semantico.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Sintatico {

	public static final String LABEL_CONSTANT = "L";

	private int controle;
	private Lexico lx;
	private Semantico semantico;
	private GeracaoCodigo geracaoCodigo;
	private Token tk;
	private Token previousTk;
	private JTextArea textAreaErro;
	private JTextArea textAreaCodigo;
	private ArrayList<SimboloVariavel> simboloVariavelArrayList;
	private String lexemaEscopo;
	private String lexemaEscopoAux;
	private ArrayList<String> arrayExpressao;
	private ArrayList<String> arrayExpressaoTipos;
	private int rotulo;
	private int posicaoVariaveis;
	private ArrayList<String> arrayPosfixa;
	private Posfixa posfixa;

	public Sintatico(File arquivo, JTextArea textAreaError, JTextArea textAreaCod) throws Exception {
        simboloVariavelArrayList = new ArrayList<>();
		textAreaError.setText("");
	    textAreaErro = textAreaError;
	    textAreaCodigo = textAreaCod;
		lx = new Lexico(arquivo, textAreaErro, textAreaCodigo);
		semantico = new Semantico();
		geracaoCodigo = new GeracaoCodigo();
		arrayExpressaoTipos = new ArrayList<>();
		controle = 0;
		rotulo = 1;
		posicaoVariaveis = 0;

		while (!lx.isFileOver(controle)) {
			tk = sintaticoBuscaToken();
			if (tk.getSimbolo().equals(Simbolo.SPROGRAMA.getName())) {
				tk = sintaticoBuscaToken();
				if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
				    semantico.insereTabelaSimbolos(new SimboloPrograma(tk.getLexema(),"",""));
				    geracaoCodigo.generateFile(tk.getLexema());
				    geracaoCodigo.generateStart();
					tk = sintaticoBuscaToken();
					if (tk.getSimbolo().equals(Simbolo.SPONTOVIRGULA.getName())) {
						analisaBloco();
						if (tk == null) {
							throw SintaticoException.erroFaltandoPonto(previousTk.getLinha(), textAreaErro, textAreaCodigo);
						}
						tk = sintaticoBuscaToken();
						if (previousTk.getSimbolo().equals(Simbolo.SPONTO.getName()) && tk == null) {
							textAreaErro.setText("EXECUTADO COM SUCESSO");
							textAreaErro.setForeground(Color.GREEN);
							geracaoCodigo.generateHlt();
							geracaoCodigo.generateClose();
						} else {
							throw SintaticoException.erroSintatico("Caracter invalido " + tk.getLexema(), tk.getLinha(), textAreaErro, textAreaCodigo);
						}
					} else {
						throw SintaticoException.erroFaltandoPontoVirgula(tk.getLinha(), textAreaErro, textAreaCodigo);
					}
				} else {
					throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textAreaErro, textAreaCodigo);
				}
			} else {
				throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textAreaErro, textAreaCodigo);
			}
		}
	}

	private Token sintaticoBuscaToken() throws LexicoException {
		previousTk = tk;
		Token t = lx.getToken();
		incrementaControle();
		return t;
	}

	private void incrementaControle() {
		controle++;
	}

	private void analisaBloco() throws SintaticoException, LexicoException, SemanticoException {
		tk = sintaticoBuscaToken();
		analisaEtVariaveis();
		//semantico.printaVariaveis();
		analisaSubRotinas();
		//semantico.printaVariaveis();
		analisaComandos();
		//semantico.printaVariaveis();
	}

	private void analisaEtVariaveis() throws SintaticoException, LexicoException, SemanticoException {
		if (tk.getSimbolo().equals(Simbolo.SVAR.getName())) {
			tk = sintaticoBuscaToken();
			if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
				while (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
					analisaVariaveis();
					if (tk.getSimbolo().equals(Simbolo.SPONTOVIRGULA.getName())) {
						tk = sintaticoBuscaToken();
					} else {
						throw SintaticoException.erroFaltandoPontoVirgula(tk.getLinha(), textAreaErro, textAreaCodigo);
					}
				}
			} else {
				throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textAreaErro, textAreaCodigo);
			}
		}
	}

	private void analisaVariaveis() throws SintaticoException, LexicoException, SemanticoException {
		while(!tk.getSimbolo().equals(Simbolo.SDOISPONTOS.getName())) {
			if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
				System.out.println("O identificador eh >>> " + tk.getLexema());
                if (!semantico.existeDuplicidadeVariavel(tk.getLexema())) {
                    simboloVariavelArrayList.add(new SimboloVariavel(tk.getLexema(), "", "", "", posicaoVariaveis));
                    semantico.insereTabelaSimbolos(new SimboloVariavel(tk.getLexema(), "", "", "", posicaoVariaveis));
                    posicaoVariaveis++;
                    tk = sintaticoBuscaToken();
                    if (tk.getSimbolo().equals(Simbolo.SVIRGULA.getName()) || tk.getSimbolo().equals(Simbolo.SDOISPONTOS.getName())) {
                        if (tk.getSimbolo().equals(Simbolo.SVIRGULA.getName())) {
                            tk = sintaticoBuscaToken();
                            if (tk.getSimbolo().equals(Simbolo.SDOISPONTOS.getName())) {
                                throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textAreaErro, textAreaCodigo);
                            }
                        }
                    } else {
                        throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textAreaErro, textAreaCodigo);
                    }
                } else {
                    throw SemanticoException.erroSemantico("Variavel duplicada", tk.getLinha(), textAreaErro, textAreaCodigo);
                }
			} else {
				throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textAreaErro, textAreaCodigo);
			}
		}
		tk = sintaticoBuscaToken();
		analisaTipo();
	}

	private void analisaTipo() throws SintaticoException, LexicoException {
		if (!tk.getSimbolo().equals(Simbolo.SINTEIRO.getName()) && !tk.getSimbolo().equals(Simbolo.SBOOLEANO.getName())) {
			throw SintaticoException.erroSintatico("Tipo de variavel invalido", tk.getLinha(), textAreaErro, textAreaCodigo);
		} else {
            for (SimboloVariavel simboloVariavel:
                 simboloVariavelArrayList) {
                //System.out.println("Colocando tipo na variavel " + simboloVariavel.getLexema() + " e o tipo é " + tk.getSimbolo());
                semantico.colocaTipoTabela(simboloVariavel.getLexema(), tk.getSimbolo().equals(Simbolo.SINTEIRO.getName()) ? "inteiro" : "booleano");
                //semantico.imprime();
            }
            simboloVariavelArrayList = new ArrayList<>();
			tk = sintaticoBuscaToken();
		}
	}

	private void analisaComandos() throws SintaticoException, LexicoException, SemanticoException {
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
					throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textAreaErro, textAreaCodigo);
				}
			}
			// System.out.println("Finalizando func/proc " + lexemaEscopo);
			if (lexemaEscopo != null) {
				System.out.println("Desempilhando ate > " + lexemaEscopo);
				semantico.desempilhaSimbolos(lexemaEscopo);
				lexemaEscopo = null;
			}
			if (lexemaEscopoAux != null) {
				System.out.println("Desempilhando ate > " + lexemaEscopoAux);
				semantico.desempilhaSimbolos(lexemaEscopoAux);
				lexemaEscopoAux = null;
			}
			tk = sintaticoBuscaToken();
		} else {
			throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textAreaErro, textAreaCodigo);
		}
	}

	private void analisaComandoSimples() throws SintaticoException, LexicoException, SemanticoException {
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

	private void analisaAtribChProcedimento() throws SintaticoException, LexicoException, SemanticoException {
		Token token = tk;
		tk = sintaticoBuscaToken();
		if (tk.getSimbolo().equals(Simbolo.SATRIBUICAO.getName())) {
			analisaAtribuicao();
		} else {
			analisaChamadaProcedimento(token);
		}
	}

	private void analisaLeia() throws SintaticoException, LexicoException, SemanticoException {
		tk = sintaticoBuscaToken();
		if (tk.getSimbolo().equals(Simbolo.SABREPARENTESES.getName())) {
			tk = sintaticoBuscaToken();
			if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
			    if (semantico.pesquisaDeclaracaoVariavelTabelaSimbolos(tk.getLexema())) {
			    	if (semantico.isTipoInteiro(tk.getLexema())) {
						tk = sintaticoBuscaToken();
						if (tk.getSimbolo().equals(Simbolo.SFECHAPARENTESES.getName())) {
							tk = sintaticoBuscaToken();
						} else {
							throw SintaticoException.erroSintatico("[ANALISA LEIA] Faltou fechar parenteses", tk.getLinha(), textAreaErro, textAreaCodigo);
						}
					} else {
			    		throw SemanticoException.erroSemantico("Incompatibilidade de tipo", tk.getLinha(), textAreaErro, textAreaCodigo);
					}
                } else {
			        throw SemanticoException.erroSemantico("Variavel nao declarada", tk.getLinha(), textAreaErro, textAreaCodigo);
                }
			} else {
				throw SintaticoException.erroSintatico("[ANALISA LEIA] Faltou passar identificador como parametro", tk.getLinha(), textAreaErro, textAreaCodigo);
			}
		} else {
			throw SintaticoException.erroSintatico("[ANALISA LEIA] Faltou abrir parenteses", tk.getLinha(), textAreaErro, textAreaCodigo);
		}
	}

	private void analisaEscreva() throws SintaticoException, LexicoException, SemanticoException {
		tk = sintaticoBuscaToken();
		if (tk.getSimbolo().equals(Simbolo.SABREPARENTESES.getName())) {
			tk = sintaticoBuscaToken();
			if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
				if (semantico.pesquisaDeclaracaoVariavelTabelaSimbolos(tk.getLexema())) {
					if (semantico.isTipoInteiro(tk.getLexema())) {
						geracaoCodigo.generateLdv(semantico.buscaPosicaoSimbolo(tk));
						geracaoCodigo.generatePrn();
						tk = sintaticoBuscaToken();
						if (tk.getSimbolo().equals(Simbolo.SFECHAPARENTESES.getName())) {
							tk = sintaticoBuscaToken();
						} else {
							throw SintaticoException.erroSintatico("[ANALISA ESCREVA] Faltou fechar parenteses", tk.getLinha(), textAreaErro, textAreaCodigo);
						}
					} else {
						throw SemanticoException.erroSemantico("Incompatibilidade de tipo", tk.getLinha(), textAreaErro, textAreaCodigo);
					}
				} else {
					throw SemanticoException.erroSemantico("Variavel nao declarada", tk.getLinha(), textAreaErro, textAreaCodigo);
				}
			} else {
				throw SintaticoException.erroSintatico("[ANALISA ESCREVA] Faltou passar identificador como parametro", tk.getLinha(), textAreaErro, textAreaCodigo);
			}
		} else {
			throw SintaticoException.erroSintatico("[ANALISA ESCREVA] Faltou abrir parenteses", tk.getLinha(), textAreaErro, textAreaCodigo);
		}
	}

	// geracao done
	private void analisaEnquanto() throws SintaticoException, LexicoException, SemanticoException {
		int auxrot1 = 0,
			auxrot2 = 0;
		auxrot1 = geracaoCodigo.getRotulo();
		geracaoCodigo.generateLabel(LABEL_CONSTANT + rotulo);
		rotulo++;
		tk = sintaticoBuscaToken();
		arrayExpressao = new ArrayList<>();
		arrayExpressaoTipos = new ArrayList<>();
		analisaExpressao();
		System.out.println(":::EXPRESSAO PARA COMANDO ENQUANTO:::");
		printaExpressao(arrayExpressao);
		System.out.println(":::EXPRESSAO TIPOS PARA COMANDO ENQUANTO:::");
		printaExpressaoTipos();
		arrayExpressao = new ArrayList<>();
		arrayExpressaoTipos = new ArrayList<>();
		if (tk.getSimbolo().equals(Simbolo.SFACA.getName())) {
			auxrot2 = rotulo;
			tk = sintaticoBuscaToken();
			analisaComandoSimples();
			geracaoCodigo.generateJump("JMP", "L"+rotulo);
			rotulo++;
			geracaoCodigo.generateJump("JMP", "L"+auxrot1);
			geracaoCodigo.generateLabel("L" + auxrot2);
		} else {
			throw SintaticoException.erroSintatico("[ANALISA ENQUANTO] Faltou a palavra 'faca'", tk.getLinha(), textAreaErro, textAreaCodigo);
		}
	}

	private void analisaSe() throws SintaticoException, LexicoException, SemanticoException {
		tk = sintaticoBuscaToken();
		arrayExpressao = new ArrayList<>();
		arrayExpressaoTipos = new ArrayList<>();
		analisaExpressao();
		System.out.println(":::EXPRESSAO PARA COMANDO SE:::");
		printaExpressao(arrayExpressao);
		System.out.println(":::EXPRESSAO TIPOS PARA COMANDO SE:::");
		printaExpressaoTipos();
		arrayExpressao = new ArrayList<>();
		arrayExpressaoTipos = new ArrayList<>();
		if (tk.getSimbolo().equals(Simbolo.SENTAO.getName())) {
			tk = sintaticoBuscaToken();
			analisaComandoSimples();
			if (tk.getSimbolo().equals(Simbolo.SSENAO.getName())) {
				tk = sintaticoBuscaToken();
				analisaComandoSimples();
			}
		} else {
			throw SintaticoException.erroSintatico("[ANALISA SE] Faltou a palavra 'entao'", tk.getLinha(), textAreaErro, textAreaCodigo);
		}
	}

	private void analisaSubRotinas() throws SintaticoException, LexicoException, SemanticoException {
		int auxrot = 0,
			flag = 0;

		if (tk.getSimbolo().equals(Simbolo.SPROCEDIMENTO.getName()) ||
				tk.getSimbolo().equals(Simbolo.SFUNCAO.getName())) {
			// implementacao da geracao
			auxrot = rotulo;
			geracaoCodigo.generateJump("JMP", LABEL_CONSTANT + rotulo);
			rotulo++;
			flag = 1;
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
				throw SintaticoException.erroFaltandoPontoVirgula(tk.getLinha(), textAreaErro, textAreaCodigo);
			}
		}

		if(flag == 1)
		{
			geracaoCodigo.generateLabel(LABEL_CONSTANT + auxrot);
		}
	}

	private void analisaDeclaracaoProcedimento() throws SintaticoException, LexicoException, SemanticoException {
        tk = sintaticoBuscaToken();
		if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
		    if (!semantico.pesquisaDeclaracaoProcedimentoTabelaSimbolos(tk.getLexema())) {
				if (lexemaEscopo != null) { // para nao perder procedimentos dentro de outros procedimentos
					lexemaEscopoAux = lexemaEscopo;
				}
		    	lexemaEscopo = tk.getLexema();
                semantico.insereTabelaSimbolos(new SimboloProcedimento(tk.getLexema(), "", LABEL_CONSTANT + rotulo));
				geracaoCodigo.generateLabel(LABEL_CONSTANT + rotulo);
				rotulo++;
                tk = sintaticoBuscaToken();
                if (tk.getSimbolo().equals(Simbolo.SPONTOVIRGULA.getName())) {
                    analisaBloco();
                } else {
                    throw SintaticoException.erroFaltandoPontoVirgula(tk.getLinha(), textAreaErro, textAreaCodigo);
                }
            } else {
		        throw SemanticoException.erroSemantico("Procedimento ja existente", tk.getLinha(), textAreaErro, textAreaCodigo);
            }
		} else {
			throw SintaticoException.erroSintatico("Faltou declarar nome do procedimento", tk.getLinha(), textAreaErro, textAreaCodigo);
		}
	}

	private void analisaDeclaracaoFuncao() throws SintaticoException, LexicoException, SemanticoException {
	    Token temp;
		tk = sintaticoBuscaToken();
		if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
            if (!semantico.pesquisaDeclaracaoFuncaoTabelaSimbolos(tk.getLexema())) {
                temp = tk;
				geracaoCodigo.generateLabel(LABEL_CONSTANT + rotulo);
                semantico.insereTabelaSimbolos(new SimboloFuncao(tk.getLexema(), "", LABEL_CONSTANT + rotulo));
				rotulo++;
				if (lexemaEscopo != null) { // para nao perder procedimentos dentro de outros procedimentos
					lexemaEscopoAux = lexemaEscopo;
				}
				lexemaEscopo = tk.getLexema();

                tk = sintaticoBuscaToken();
                if (tk.getSimbolo().equals(Simbolo.SDOISPONTOS.getName())) {
                    tk = sintaticoBuscaToken();
                    if (tk.getSimbolo().equals(Simbolo.SINTEIRO.getName()) ||
                            tk.getSimbolo().equals(Simbolo.SBOOLEANO.getName())) {
                        if (tk.getSimbolo().equals(Simbolo.SINTEIRO.getName())) {
                            semantico.colocaTipoTabela(temp.getLexema(), "inteiro");
                        } else {
                            semantico.colocaTipoTabela(temp.getLexema(), "booleano");
                        }
                        tk = sintaticoBuscaToken();
                        if (tk.getSimbolo().equals(Simbolo.SPONTOVIRGULA.getName())) {
                            analisaBloco();
                        }
                    } else {
                        throw SintaticoException.erroSintatico("Faltou declarar tipo da funcao", tk.getLinha(), textAreaErro, textAreaCodigo);
                    }
                } else {
                    throw SintaticoException.erroSintatico("Faltou caracter ':'", tk.getLinha(), textAreaErro, textAreaCodigo);
                }
            } else {
                throw SemanticoException.erroSemantico("Funcao ja declarada", tk.getLinha(), textAreaErro, textAreaCodigo);
            }
		} else {
			throw SintaticoException.erroSintatico("Fatlou declarar nome da funcao", tk.getLinha(), textAreaErro, textAreaCodigo);
		}
	}

	private void analisaExpressao() throws SintaticoException, LexicoException, SemanticoException {

		analisaExpressaoSimples();
		if (tk.getSimbolo().equals(Simbolo.SMAIOR.getName()) ||
				tk.getSimbolo().equals(Simbolo.SMAIORIG.getName()) ||
                tk.getSimbolo().equals(Simbolo.SIG.getName()) ||
				tk.getSimbolo().equals(Simbolo.SMENOR.getName()) ||
				tk.getSimbolo().equals(Simbolo.SMENORIG.getName()) ||
				tk.getSimbolo().equals(Simbolo.SDIF.getName())) {
			arrayExpressao.add(tk.getLexema());
			arrayExpressaoTipos.add(tk.getLexema());
			tk = sintaticoBuscaToken();
			analisaExpressaoSimples();
		}
	}

	private void printaExpressao(ArrayList<String> array) {
		for (String valor : array) {
			System.out.println(valor);
		}
	}

	private void printaExpressaoTipos() {
		for (String valor : arrayExpressaoTipos) {
			System.out.println(valor);
		}
	}

	private void analisaExpressaoSimples() throws SintaticoException, LexicoException, SemanticoException {
		// Unario
		if (tk.getSimbolo().equals(Simbolo.SMAIS.getName()) ||
				tk.getSimbolo().equals(Simbolo.SMENOS.getName())) {
			// add
			arrayExpressao.add(tk.getLexema() + "u");
			arrayExpressaoTipos.add(tk.getLexema() + "u");
            tk = sintaticoBuscaToken();
        }
        analisaTermo();
        while (tk.getSimbolo().equals(Simbolo.SMAIS.getName()) ||
                tk.getSimbolo().equals(Simbolo.SMENOS.getName()) ||
                tk.getSimbolo().equals(Simbolo.SOU.getName())) {
			arrayExpressao.add(tk.getLexema());
			arrayExpressaoTipos.add(tk.getLexema());
            tk = sintaticoBuscaToken();
            analisaTermo();
        }

	}

	private void analisaTermo() throws SintaticoException, LexicoException, SemanticoException {
		analisaFator();
		while (tk.getSimbolo().equals(Simbolo.SMULT.getName()) ||
				tk.getSimbolo().equals(Simbolo.SDIV.getName()) ||
				tk.getSimbolo().equals(Simbolo.SE.getName())) {
			arrayExpressao.add(tk.getLexema());
			arrayExpressaoTipos.add(tk.getLexema());
			tk = sintaticoBuscaToken();
			analisaFator();
		}
	}

	private void analisaFator() throws SintaticoException, LexicoException, SemanticoException {
		if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
			arrayExpressao.add(tk.getLexema());
			arrayExpressaoTipos.add(semantico.pegaTipoVariavel(tk.getLexema()));
			if (semantico.pesquisaDeclaracaoFuncaoTabelaSimbolos(tk.getLexema())) { // eh uma funcao
				analisaChamadaFuncao();
			} else if (semantico.pesquisaDeclaracaoVariavelTabelaSimbolos(tk.getLexema())) {
				tk = sintaticoBuscaToken();
			} else {
				throw SemanticoException.erroSemantico("Variavel ou funcao nao declarada", tk.getLinha(), textAreaErro, textAreaCodigo);
			}
		} else if (tk.getSimbolo().equals(Simbolo.SNUMERO.getName())) {
			arrayExpressao.add(tk.getLexema());
			arrayExpressaoTipos.add(tk.getLexema());
			tk = sintaticoBuscaToken();
		} else if (tk.getSimbolo().equals(Simbolo.SNAO.getName())) {
			arrayExpressao.add(tk.getLexema());
			arrayExpressaoTipos.add(tk.getLexema());
			tk = sintaticoBuscaToken();
			analisaFator();
		} else if (tk.getSimbolo().equals(Simbolo.SABREPARENTESES.getName())) {
			arrayExpressao.add(tk.getLexema());
			arrayExpressaoTipos.add(tk.getLexema());
			tk = sintaticoBuscaToken();
			analisaExpressao();
			if (tk.getSimbolo().equals(Simbolo.SFECHAPARENTESES.getName())) {
				arrayExpressao.add(tk.getLexema());
				arrayExpressaoTipos.add(tk.getLexema());
				tk = sintaticoBuscaToken();
			} else {
				throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textAreaErro, textAreaCodigo);
			}
		} else if (tk.getLexema().equals("verdadeiro") ||
				tk.getLexema().equals("falso")) {
			tk = sintaticoBuscaToken();
		} else {
			throw SintaticoException.erroCaracterInvalido(tk.getLexema(), tk.getLinha(), textAreaErro, textAreaCodigo);
		}
	}

	private void analisaChamadaFuncao() throws LexicoException, SemanticoException {
		if (semantico.pesquisaDeclaracaoFuncaoTabelaSimbolos(tk.getLexema())) {
			geracaoCodigo.generateCall(semantico.buscaRotuloFuncao(tk.getLexema()));
			tk = sintaticoBuscaToken();
		} else {
			throw SemanticoException.erroSemantico("Chamada de funcao invalida, pois funcao nao existe", tk.getLinha(), textAreaErro, textAreaCodigo);
		}
	}

	private void analisaChamadaProcedimento(final Token token) throws SemanticoException, LexicoException {
		if (!semantico.pesquisaDeclaracaoProcedimentoTabelaSimbolos(token.getLexema())) {
			throw SemanticoException.erroSemantico("Chamada de procedimento invalida, pois procedimento nao existe", tk.getLinha(), textAreaErro, textAreaCodigo);
		}
		geracaoCodigo.generateCall(semantico.buscaRotuloProcedimento(token.getLexema()));
	}

	private void analisaAtribuicao() throws SintaticoException, LexicoException, SemanticoException {
        tk = sintaticoBuscaToken();
		posfixa = new Posfixa();
		arrayExpressao = new ArrayList<>();
		arrayExpressaoTipos = new ArrayList<>();
        analisaExpressao();

		System.out.println(":::EXPRESSAO PARA ANALISA ATRIBUICAO:::");
		printaExpressao(arrayExpressao);
		System.out.println(":::EXPRESSAO TIPOS PARA ANALISA ATRIBUICAO:::");
		printaExpressaoTipos();
		arrayExpressao = new ArrayList<>();
		arrayExpressaoTipos = new ArrayList<>();
        arrayPosfixa = new ArrayList<>();
        arrayPosfixa = posfixa.trataPofixa(arrayExpressao);
		System.out.println(":::EXPRESSAO PARA ANALISA ATRIBUICAO POSFIXA:::");
		printaExpressao(arrayPosfixa);
		arrayExpressao = new ArrayList<>();
		arrayPosfixa = new ArrayList<>();
	}
}
