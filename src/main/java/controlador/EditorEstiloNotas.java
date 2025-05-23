package controlador;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.Random;
import java.util.regex.*;

/* Clase EditorEstiloNotas encargada de aplicar color y estilo a los hashtags
 * que se escriben en una nota.
 *
 * ➤ Resalta los hashtags en negrita y les asigna un color aleatorio cada vez.
 * ➤ Solo se actualiza cuando se escribe un espacio o se borra algo, para no ralentizar el programa.
 * ➤ Sirve para dar un toque visual a la escritura de notas, sin afectar al contenido real. */

public class EditorEstiloNotas {

    // ---------------------- ATRIBUTOS ----------------------

    // Expresión regular que detecta los hashtags que empiezan por #
    private static final Pattern PATRON_HASHTAG = Pattern.compile("#(\\S+)");

    // ---------------------- MÉTODOS PÚBLICOS ----------------------

    /* Recorre el texto del campo y, si encuentra hashtags, les aplica un estilo especial:
     * negrita y un color aleatorio.
     * Este método se usa para dar formato visual sin modificar el contenido real de la nota. */
    public static void aplicarEstilos(JTextPane campo) {
        StyledDocument doc = campo.getStyledDocument();
        String texto = campo.getText();

        // Se limpia cualquier estilo anterior antes de aplicar los nuevos
        doc.setCharacterAttributes(0, texto.length(), campo.getStyle(StyleContext.DEFAULT_STYLE), true);

        Matcher matcher = PATRON_HASHTAG.matcher(texto);
        while (matcher.find()) {
            int start = matcher.start(1);
            int end = matcher.end(1);

            if (start >= 0 && end <= texto.length()) {
                Style estilo = doc.addStyle("hashtag", null);
                StyleConstants.setBold(estilo, true);
                Color color = generarColorAleatorio(); // Color pastel aleatorio
                StyleConstants.setForeground(estilo, color);
                doc.setCharacterAttributes(start, end - start, estilo, false);
            }
        }
    }

    /* Este método solo actualiza el estilo si se ha escrito un espacio o se ha borrado algo.
     * Así evitamos aplicar el formato continuamente mientras se escribe.
     * Se llama automáticamente desde la clase que gestiona la edición de notas. */
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
                // Ejecutamos la actualización de estilos un poco después, para que el texto ya esté colocado
                SwingUtilities.invokeLater(() -> aplicarEstilos(campo));
            }

        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    // ---------------------- MÉTODO AUXILIAR ----------------------

    /* Genera un color pastel suave para los hashtags, para que no moleste a la vista. */
    private static Color generarColorAleatorio() {
        Random rand = new Random();
        int r = Math.min(rand.nextInt(156) + 100, 255);
        int g = Math.min(rand.nextInt(156) + 100, 255);
        int b = Math.min(rand.nextInt(156) + 100, 255);
        return new Color(r, g, b);
    }

    // ---------------------- RESERVADO ----------------------
    /*Este código estaba preparado para detectar otros estilos además de hashtags,
    pero no se llegó a usar en esta versión del proyecto.

    private static class PatronEstilo {
        final Pattern pattern;
        final BiConsumer<Style, Matcher> aplicarFormato;

        PatronEstilo(String regex, BiConsumer<Style, Matcher> aplicarFormato) {
            this.pattern = Pattern.compile(regex);
            this.aplicarFormato = aplicarFormato;
        }
    }    */
}

