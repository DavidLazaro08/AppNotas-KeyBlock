package controlador;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.Random;
import java.util.regex.*;

/* Clase EditorEstiloNotas encargada de aplicar color y estilo a los hashtags
   que se escriben en una nota.
 *
 * ➤ Resalta los hashtags en negrita y les asigna un color aleatorio cada vez.
 * ➤ Reacciona solo cuando se pulsa espacio o se borra algo, para no recargar el programa.
 * ➤ Se usa en los campos de edición de nota para dar un aspecto más visual y moderno. */

public class EditorEstiloNotas {

    // ---------------------- ATRIBUTOS ----------------------

    // Patrón para detectar hashtags válidos: palabras precedidas por "#"
    private static final Pattern PATRON_HASHTAG = Pattern.compile("#(\\S+)");

    // ---------------------- MÉTODOS PÚBLICOS ----------------------

    /* Aplica estilos (negrita + color aleatorio) a los hashtags en el campo proporcionado. */

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
                Color color = generarColorAleatorio(); // ✅ ahora método interno
                StyleConstants.setForeground(estilo, color);
                doc.setCharacterAttributes(start, end - start, estilo, false);
            }
        }
    }

    /* Aplica estilos solo si se presiona espacio o se borra texto.
     * Pensado para optimizar rendimiento mientras se escribe. */

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

    // ---------------------- MÉTODO AUXILIAR ----------------------

    /* Genera un color pastel aleatorio para destacar visualmente hashtags. */

    private static Color generarColorAleatorio() {
        Random rand = new Random();
        int r = Math.min(rand.nextInt(156) + 100, 255);
        int g = Math.min(rand.nextInt(156) + 100, 255);
        int b = Math.min(rand.nextInt(156) + 100, 255);
        return new Color(r, g, b);
    }

    // ---------------------- RESERVADO (no usado por ahora) ----------------------
    /*
    private static class PatronEstilo {
        final Pattern pattern;
        final BiConsumer<Style, Matcher> aplicarFormato;

        PatronEstilo(String regex, BiConsumer<Style, Matcher> aplicarFormato) {
            this.pattern = Pattern.compile(regex);
            this.aplicarFormato = aplicarFormato;
        }
    }
    */
}
