package puc.compiladores.lexico;

public class LexicoException extends Exception {

	public LexicoException(final String s) {
		super(s);
	}

	public static LexicoException erroLexico(final String msg, final int linha) throws LexicoException {
		throw new LexicoException(msg + " na linha: " + linha);
	}
}
