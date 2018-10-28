package puc.compiladores.semantico;

import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;

public class SemanticoException extends Exception {

    public SemanticoException(final String s, final JTextArea textAreaErro, final JTextArea textAreaCodigo, final int linha) {
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

    public static SemanticoException erroSemantico(final String msg, final int linha, final JTextArea textAreaErro, final JTextArea textAreaCodigo) throws SemanticoException {
        throw new SemanticoException(msg + " na linha: " + linha, textAreaErro, textAreaCodigo, linha);
    }
}
