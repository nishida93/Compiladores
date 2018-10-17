package puc.compiladores.sintatico;

import javax.swing.*;

public class SintaticoException extends Exception {

	public SintaticoException(final String s, final JTextArea textAreaErro) {
		textAreaErro.append(s);
	}

	public static SintaticoException erroSintatico(final String msg, final int linha, final JTextArea textAreaErro) throws SintaticoException {
		throw new SintaticoException(msg + " na linha: " + linha, textAreaErro);
	}

	public static SintaticoException erroFaltandoPontoVirgula(final int linha, final JTextArea textAreaErro) throws SintaticoException {
		throw new SintaticoException("Faltando ';' na linha: " + linha, textAreaErro);
	}

	public static SintaticoException erroFaltandoPonto(final int linha, final JTextArea textAreaErro) throws SintaticoException {
		throw new SintaticoException("Faltando '.' na linha: " + linha, textAreaErro);
	}

	public static SintaticoException erroCaracterInvalido(final String lexema, final int linha, final JTextArea textAreaErro) throws SintaticoException {
		throw new SintaticoException("Caracter invalido '" + lexema + "' na linha: " + linha, textAreaErro);
	}
}
