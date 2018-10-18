package puc.compiladores.lexico;

import java.awt.Color;

import javax.swing.*;
import javax.swing.text.DefaultHighlighter;

public class LexicoException extends Exception {

	public LexicoException(final String s, final JTextArea textAreaErro, final JTextArea textAreaCodigo, final int linha) {
		DefaultHighlighter highlighter = (DefaultHighlighter)textAreaCodigo.getHighlighter();
		DefaultHighlighter.DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
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

	public static LexicoException erroLexico(final String msg, final int linha, final JTextArea textAreaErro, final JTextArea textAreaCodigo) throws LexicoException {
		throw new LexicoException(msg + " na linha: " + linha, textAreaErro, textAreaCodigo, linha);
	}
}
