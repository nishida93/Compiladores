package puc.compiladores.lexico;

public class LexicoException extends Exception {

	public LexicoException() {
		super();
	}

	public LexicoException(final String s, final int linha) {
	}

	public static LexicoException erroLexico(final String msg, final int linha) throws LexicoException {
		throw new LexicoException(msg, linha);
	}
}
