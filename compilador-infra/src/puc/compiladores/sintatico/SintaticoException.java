package puc.compiladores.sintatico;

public class SintaticoException extends Exception {

	public SintaticoException(final String s) {
		super(s);
	}

	public static SintaticoException erroSintatico(final String msg, final int linha) throws SintaticoException {
		throw new SintaticoException(msg + " na linha: " + linha);
	}

	public static SintaticoException erroFaltandoPontoVirgula(final int linha) throws SintaticoException {
		throw new SintaticoException("Faltando ';' na linha: " + linha);
	}

	public static SintaticoException erroFaltandoPonto(final int linha) throws SintaticoException {
		throw new SintaticoException("Faltando '.' na linha: " + linha);
	}

	public static SintaticoException erroCaracterInvalido(final String lexema, final int linha) throws SintaticoException {
		throw new SintaticoException("Caracter invalido '" + lexema + "' na linha: " + linha);
	}
}
