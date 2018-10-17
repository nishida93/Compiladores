package puc.compiladores.lexico;

import javax.swing.*;

public class LexicoException extends Exception {

	public LexicoException(final String s, final JTextArea textAreaErro) {
	    textAreaErro.append(s);
	}

	public static LexicoException erroLexico(final String msg, final int linha, final JTextArea textAreaErro) throws LexicoException {
		throw new LexicoException(msg + " na linha: " + linha, textAreaErro);
	}
}
