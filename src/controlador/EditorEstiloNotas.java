package controlador;

import modelo.Colors;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.regex.*;

public class EditorEstiloNotas {

    // Patrón para detectar hashtags válidos: palabras precedidas por "#"
    private static final Pattern PATRON_HASHTAG = Pattern.compile("#(\\S+)");

    /**
     * Aplica estilos (negrita) a los hashtags en el campo proporcionado.
     */
    public static void aplicarEstilos(JTextPane campo) {
        StyledDocument doc = campo.getStyledDocument();
        String texto = campo.getText();

        // Limpiar estilos previos
        doc.setCharacterAttributes(0, texto.length(), campo.getStyle(StyleContext.DEFAULT_STYLE), true);

        Matcher matcher = PATRON_HASHTAG.matcher(texto);
        while (matcher.find()) {
            int start = matcher.start(1);
            int end = matcher.end(1);

            if (start >= 0 && end <= texto.length()) {
                Style estilo = doc.addStyle("hashtag", null);
                StyleConstants.setBold(estilo, true);
                Color color = Colors.generarColorAleatorio();
                StyleConstants.setForeground(estilo, color);
                doc.setCharacterAttributes(start, end - start, estilo, false);
            }
        }
    }

    /**
     * Aplica estilos solo si se presionó espacio o se borró algo.
     */
    public static void aplicarEstilosOptimizado(JTextPane campo, DocumentEvent e) {
        int offset = e.getOffset();
        DocumentEvent.EventType tipo = e.getType();

        try {
            String texto = campo.getDocument().getText(0, campo.getDocument().getLength());

            boolean debeActualizar = false;

            if (tipo == DocumentEvent.EventType.REMOVE) {
                debeActualizar = true;
            } else if (tipo == DocumentEvent.EventType.INSERT) {
                int length = e.getLength();
                if (offset + length <= texto.length()) {
                    String insertado = texto.substring(offset, offset + length);
                    if (insertado.contains(" ")) {
                        debeActualizar = true;
                    }
                }
            }

            if (debeActualizar) {
                SwingUtilities.invokeLater(() -> aplicarEstilos(campo));
            }

        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
}
