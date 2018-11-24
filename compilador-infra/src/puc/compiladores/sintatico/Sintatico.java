package puc.compiladores.sintatico;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JTextArea;

import puc.compiladores.geracao.GeracaoCodigo;
import puc.compiladores.lexico.Lexico;
import puc.compiladores.lexico.LexicoException;
import puc.compiladores.lexico.Simbolo;
import puc.compiladores.lexico.Token;
import puc.compiladores.posfixa.Posfixa;
import puc.compiladores.semantico.Semantico;
import puc.compiladores.semantico.SemanticoException;
import puc.compiladores.semantico.SimboloFuncao;
import puc.compiladores.semantico.SimboloProcedimento;
import puc.compiladores.semantico.SimboloPrograma;
import puc.compiladores.semantico.SimboloVariavel;

public class Sintatico {

	private static final String LABEL_CONSTANT = "L";

	private int controle;
	private Lexico lx;
	private Semantico semantico = new Semantico();
	private GeracaoCodigo geracaoCodigo = new GeracaoCodigo();
	private Token tk;
	private Token previousTk;
	private JTextArea textAreaErro;
	private JTextArea textAreaCodigo;
	private ArrayList<String> arrayExpressao;
	private ArrayList<String> arrayExpressaoTipos;
	private int rotulo;
	private int posicaoVariaveis;
	private ArrayList<String> arrayPosfixa;
	private Posfixa posfixa;
	private Stack<Scope> pilhaEscopos = new Stack<>();
	private int controleAllocs = 0;
	private boolean flagEnquantoComandos=false;
	private boolean flagSeComandos=false;
	private boolean flagComandosSimples=false;

	public Sintatico(File arquivo, JTextArea textAreaError, JTextArea textAreaCod) throws Exception {
		textAreaError.setText("");
	    textAreaErro = textAreaError;
	    textAreaCodigo = textAreaCod;
		lx = new Lexico(arquivo, textAreaErro, textAreaCodigo);
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
				    pilhaEscopos.push(Scope.createProgramScope());
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
		System.out.println("Quantidade de variaveis declaradas::: " + posicaoVariaveis);

		analisaSubRotinas();
		analisaComandos();
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
		ArrayList<SimboloVariavel> simboloVariavelArrayList = new ArrayList<>();
		while(!tk.getSimbolo().equals(Simbolo.SDOISPONTOS.getName())) {
			if (tk.getSimbolo().equals(Simbolo.SIDENTIFICADOR.getName())) {
				//System.out.println("O identificador eh >>> " + tk.getLexema());
                if (!semantico.existeDuplicidadeVariavel(tk.getLexema())) {
                    simboloVariavelArrayList.add(new SimboloVariavel(tk.getLexema(), "", "", "", posicaoVariaveis));

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
		analisaTipo(simboloVariavelArrayList);
	}

	private void analisaTipo(ArrayList<SimboloVariavel> simboloVariavelArrayList) throws SintaticoException, LexicoException {
		if (!tk.getSimbolo().equals(Simbolo.SINTEIRO.getName()) && !tk.getSimbolo().equals(Simbolo.SBOOLEANO.getName())) {
			throw SintaticoException.erroSintatico("Tipo de variavel invalido", tk.getLinha(), textAreaErro, textAreaCodigo);
		} else {
            for (SimboloVariavel simboloVariavel:
                 simboloVariavelArrayList) {
				System.out.println("Inserindo variavel: " + simboloVariavel.getLexema());
				semantico.insereTabelaSimbolos(new SimboloVariavel(simboloVariavel.getLexema(), tk.getLexema(), posicaoVariaveis));
				geracaoCodigo.geraAlloc(controleAllocs);
				controleAllocs++;
            }
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

			if (!flagEnquantoComandos && !flagSeComandos && !flagComandosSimples) {
				System.out.println("PILHA DE ESCOPOS >>> " + pilhaEscopos.peek().toString());
				final Scope scope = pilhaEscopos.pop();
				if (scope.isProgram()) {
					System.out.println("FINALIZANDO PROGRAM:::" + scope.getName());
				} else if (scope.isFunction()) {
						// TODO implementar o RETURNF
						System.out.println("FINALIZANDO FUNCTION:::" + scope.getName());
					} else if (scope.isProcedure()) {
						// TODO implementar o RETURN
						System.out.println("FINALIZANDO PROCEDURE:::" + scope.getName());
					}
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
			if (!flagEnquantoComandos && !flagSeComandos) {
				flagComandosSimples=true;
			}
			analisaComandos();
			if (!flagEnquantoComandos && !flagSeComandos) {
				flagComandosSimples=false;
			}
		}
	}

	private void analisaAtribChProcedimento() throws SintaticoException, LexicoException, SemanticoException {
		Token token = tk;
		tk = sintaticoBuscaToken();
		if (tk.getSimbolo().equals(Simbolo.SATRIBUICAO.getName())) {
			analisaAtribuicao(token);
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
				if (semantico.pesquisaDeclaracaoVariavelTabelaSimbolos(tk.getLexema()) || semantico.pesquisaDeclaracaoFuncaoTabelaSimbolos(tk.getLexema())) {
					if (semantico.isTipoInteiro(tk.getLexema()) || semantico.pegaTipoFuncao(tk.getLexema()).equals("inteiro")) {
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
		arrayPosfixa = new ArrayList<>();
		posfixa = new Posfixa();
		analisaExpressao(); // deve retornar um booleano

		/*System.out.println(":::EXPRESSAO PARA COMANDO ENQUANTO:::");
		printaExpressao(arrayExpressao);

		System.out.println(":::EXPRESSAO TIPOS PARA COMANDO ENQUANTO:::");
		printaExpressao(arrayExpressaoTipos);

		System.out.println(":::EXPRESSAO TIPOS PARA ANALISA ENQUANTO POSFIXA:::");*/
		arrayPosfixa = posfixa.trataPofixa(arrayExpressaoTipos);
		//printaExpressao(arrayPosfixa);
		if (semantico.validaRetornoExpressao(arrayPosfixa, tk.getLinha(), textAreaErro, textAreaCodigo) == 1) {
			throw SemanticoException.erroSemantico("Incompatibilidade de retorno", tk.getLinha() - 1, textAreaErro, textAreaCodigo);
		}


		arrayExpressao = new ArrayList<>();
		arrayExpressaoTipos = new ArrayList<>();
		arrayPosfixa = new ArrayList<>();
		posfixa = new Posfixa();


		if (tk.getSimbolo().equals(Simbolo.SFACA.getName())) {
			flagEnquantoComandos=true;
			auxrot2 = rotulo;
			tk = sintaticoBuscaToken();
			analisaComandoSimples();
			flagEnquantoComandos=false;
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
		posfixa = new Posfixa();
		analisaExpressao(); // deve retornar booleano



		/*System.out.println(":::EXPRESSAO PARA COMANDO SE:::");
		printaExpressao(arrayExpressao);

		System.out.println(":::EXPRESSAO TIPOS PARA COMANDO SE:::");
		printaExpressao(arrayExpressaoTipos);

		System.out.println(":::EXPRESSAO PARA ANALISA ATRIBUICAO POSFIXA:::");
		arrayPosfixa = posfixa.trataPofixa(arrayExpressao);
		posfixa = new Posfixa(); // limpa os arrays utilizados para a pos fixa
		printaExpressao(arrayPosfixa);

		System.out.println(":::EXPRESSAO TIPOS PARA ANALISA SE POSFIXA:::");*/
		arrayPosfixa = posfixa.trataPofixa(arrayExpressaoTipos);
		//printaExpressao(arrayPosfixa);
		if (semantico.validaRetornoExpressao(arrayPosfixa, tk.getLinha(), textAreaErro, textAreaCodigo) == 1) {
			throw SemanticoException.erroSemantico("Incompatibilidade de retorno", tk.getLinha() - 1, textAreaErro, textAreaCodigo);
		}


		arrayExpressao = new ArrayList<>();
		arrayExpressaoTipos = new ArrayList<>();
		arrayPosfixa = new ArrayList<>();
		posfixa = new Posfixa();


		if (tk.getSimbolo().equals(Simbolo.SENTAO.getName())) {
			tk = sintaticoBuscaToken();
			flagSeComandos=true;
			analisaComandoSimples();
			flagSeComandos=false;
			if (tk.getSimbolo().equals(Simbolo.SSENAO.getName())) {
				tk = sintaticoBuscaToken();
				flagSeComandos=true;
				analisaComandoSimples();
				flagSeComandos=false;
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
				pilhaEscopos.push(Scope.createProcedureScope(tk.getLexema()));

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
				pilhaEscopos.push(Scope.createFunctionScope(tk.getLexema()));
                temp = tk;
				geracaoCodigo.generateLabel(LABEL_CONSTANT + rotulo);
                semantico.insereTabelaSimbolos(new SimboloFuncao(tk.getLexema(), "", LABEL_CONSTANT + rotulo));
				rotulo++;

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
			if (semantico.pesquisaDeclaracaoFuncaoTabelaSimbolos(tk.getLexema())) { // eh uma funcao
				arrayExpressaoTipos.add(semantico.pegaTipoFuncao(tk.getLexema()));
				analisaChamadaFuncao();
			} else if (semantico.pesquisaDeclaracaoVariavelTabelaSimbolos(tk.getLexema())) {
				arrayExpressaoTipos.add(semantico.pegaTipoVariavel(tk.getLexema()));
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
		} else if (tk.getSimbolo().equals(Simbolo.SVERDADEIRO.getName()) ||
				tk.getSimbolo().equals(Simbolo.SFALSO.getName())) {
			// TODO talvez precise de implementacao semantica
			arrayExpressao.add(tk.getLexema());
			arrayExpressaoTipos.add("booleano");
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

	private void analisaAtribuicao(final Token tokenVariavel) throws SintaticoException, LexicoException, SemanticoException {
		//System.out.println("O TOKEN PARA ATRIBUICAO EHHH:" + tokenVariavel.toString());
		//System.out.println(semantico.pegaTipoVariavel(tokenVariavel.getLexema()));
        tk = sintaticoBuscaToken();
		arrayExpressao = new ArrayList<>();
		arrayExpressaoTipos = new ArrayList<>();
		arrayPosfixa = new ArrayList<>();
		posfixa = new Posfixa();
        analisaExpressao(); // deve retornar tipo compativel com a variavel

		System.out.println(":::EXPRESSAO PARA ANALISA ATRIBUICAO:::");
		printaExpressao(arrayExpressao);
		System.out.println(":::EXPRESSAO TIPOS PARA ANALISA ATRIBUICAO:::");
		printaExpressao(arrayExpressaoTipos);
		System.out.println(":::EXPRESSAO PARA ANALISA ATRIBUICAO POSFIXA:::");
		arrayPosfixa = posfixa.trataPofixa(arrayExpressao);
		posfixa = new Posfixa(); // limpa os arrays utilizados para a pos fixa
		printaExpressao(arrayPosfixa);
		System.out.println(":::EXPRESSAO TIPOS PARA ANALISA ATRIBUICAO POSFIXA:::");
		arrayPosfixa = posfixa.trataPofixa(arrayExpressaoTipos);

		//printaExpressao(arrayPosfixa);
		if (semantico.pegaTipoVariavel(tokenVariavel.getLexema()) == null && !semantico.pesquisaDeclaracaoFuncaoTabelaSimbolos(tokenVariavel.getLexema())) {
			throw SemanticoException.erroSemantico("Variavel ou funcao nao declarada", tokenVariavel.getLinha(), textAreaErro, textAreaCodigo);
		}
		// TODO validar se tem atribuicao com o nome da funcao
		/*if (semantico.pesquisaDeclaracaoFuncaoTabelaSimbolos(nomeDaFuncao) && flagFuncao) {
			System.out.println("Atribuicao para funcao, ou seja, retorno declarado");
			flagFuncaoAtribuicao = true;
		}*/

		if ("booleano".equals(semantico.pegaTipoVariavel(tokenVariavel.getLexema())) || "booleano".equals(semantico.pegaTipoVariavel(tokenVariavel.getLexema()))) {
			if(semantico.validaRetornoExpressao(arrayPosfixa, tokenVariavel.getLinha(), textAreaErro, textAreaCodigo) != 0) {
				throw SemanticoException.erroSemantico("[variavel ou funcao booleana] Incompatibilidade durante atribuicao", tokenVariavel.getLinha(), textAreaErro, textAreaCodigo);
			}
		} else {
			if(semantico.validaRetornoExpressao(arrayPosfixa, tokenVariavel.getLinha(), textAreaErro, textAreaCodigo) != 1) {
				throw SemanticoException.erroSemantico("[inteiro] Incompatibilidade durante atribuicao", tokenVariavel.getLinha(), textAreaErro, textAreaCodigo);
			}
		}

		arrayExpressao = new ArrayList<>();
		arrayExpressaoTipos = new ArrayList<>();
		arrayPosfixa = new ArrayList<>();
		posfixa = new Posfixa();

	}
}
