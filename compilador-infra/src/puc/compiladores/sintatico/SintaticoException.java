package puc.compiladores.sintatico;

import java.awt.Color;

import javax.swing.*;
import javax.swing.text.DefaultHighlighter;

public class SintaticoException extends Exception {

	public SintaticoException(final String s, final JTextArea textAreaErro, final JTextArea textAreaCodigo, final int linha) {
        DefaultHighlighter highlighter = (DefaultHighlighter)textAreaCodigo.getHighlighter();
        DefaultHighlighter.DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.getHSBColor(00,80,100));
        highlighter.setDrawsLayeredHighlights(true);
        try {
            int start = textAreaCodigo.getLineStartOffset(linha - 1);
            int end = textAreaCodigo.getLineEndOffset(linha - 1);
            highlighter.addHighlight(start, end, painter);
        } catch (Exception e) {
            System.out.println(e);
        }
		textAreaErro.append(s);
		textAreaErro.setForeground(Color.RED);
	}

	public static SintaticoException erroSintatico(final String msg, final int linha, final JTextArea textAreaErro, final JTextArea textAreaCodigo) throws SintaticoException {
		throw new SintaticoException(msg + " na linha: " + linha, textAreaErro, textAreaCodigo, linha);
	}

	public static SintaticoException erroFaltandoPontoVirgula(final int linha, final JTextArea textAreaErro, final JTextArea textAreaCodigo) throws SintaticoException {
		throw new SintaticoException("Faltando ';' na linha: " + linha, textAreaErro, textAreaCodigo, linha);
	}

	public static SintaticoException erroFaltandoPonto(final int linha, final JTextArea textAreaErro, final JTextArea textAreaCodigo) throws SintaticoException {
		throw new SintaticoException("Faltando '.' na linha: " + linha, textAreaErro, textAreaCodigo, linha);
	}

	public static SintaticoException erroCaracterInvalido(final String lexema, final int linha, final JTextArea textAreaErro, final JTextArea textAreaCodigo) throws SintaticoException {
		throw new SintaticoException("Caracter invalido '" + lexema + "' na linha: " + linha, textAreaErro, textAreaCodigo, linha);
	}
}
