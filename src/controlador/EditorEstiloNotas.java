package controlador;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.*;
import java.util.regex.*;
import java.util.function.BiConsumer;

public class EditorEstiloNotas {

    // Mapa de patrones con su lógica de estilo
    private static final Map<String, PatronEstilo> patrones = new LinkedHashMap<>();

    static {
        patrones.put("encabezado", new PatronEstilo("(?m)^(#{1,6}) (.+)$", (style, matcher) -> {
            if (matcher.find() && matcher.groupCount() >= 2) {
                int nivel = matcher.group(1).length();
                StyleConstants.setFontSize(style, 24 - (nivel * 2));
                StyleConstants.setBold(style, true);
            }
        }));

        patrones.put("negrita", new PatronEstilo("\\*\\*(.+?)\\*\\*", (style, matcher) -> {
            if (matcher.find() && matcher.groupCount() >= 1) {
                StyleConstants.setBold(style, true);
            }
        }));

        patrones.put("cursiva", new PatronEstilo("(?<!\\*)\\*(?!\\*)(.+?)(?<!\\*)\\*(?!\\*)", (style, matcher) -> {
            if (matcher.find() && matcher.groupCount() >= 1) {
                StyleConstants.setItalic(style, true);
            }
        }));

        patrones.put("tachado", new PatronEstilo("~~(.+?)~~", (style, matcher) -> {
            if (matcher.find() && matcher.groupCount() >= 1) {
                StyleConstants.setStrikeThrough(style, true);
            }
        }));

        patrones.put("codigo", new PatronEstilo("`([^`]+)`", (style, matcher) -> {
            if (matcher.find() && matcher.groupCount() >= 1) {
                StyleConstants.setFontFamily(style, "Courier New");
                StyleConstants.setForeground(style, Color.GREEN);
            }
        }));

        patrones.put("lista", new PatronEstilo("(?m)^- (.+)$", (style, matcher) -> {
            if (matcher.find() && matcher.groupCount() >= 1) {
                StyleConstants.setItalic(style, true);
            }
        }));

        patrones.put("cita", new PatronEstilo("(?m)^> (.+)$", (style, matcher) -> {
            if (matcher.find() && matcher.groupCount() >= 1) {
                StyleConstants.setItalic(style, true);
                StyleConstants.setForeground(style, Color.GRAY);
            }
        }));
    }

    private static boolean esCaracterDeEstilo(char c) {
        return c == '*' || c == '_' || c == '`' || c == '~' || c == '#' || c == '>' || c == '-';
    }

    /**
     * Método principal para aplicar todos los estilos detectados en el texto.
     * Se puede usar manualmente o desde el optimizado.
     */
    public static void aplicarEstilos(JTextPane campoContenido) {
        StyledDocument doc = campoContenido.getStyledDocument();
        String texto = campoContenido.getText();

        // Limpiar estilos existentes
        doc.setCharacterAttributes(0, texto.length(), campoContenido.getStyle(StyleContext.DEFAULT_STYLE), true);

        for (Map.Entry<String, PatronEstilo> entrada : patrones.entrySet()) {
            String nombre = entrada.getKey();
            PatronEstilo patronEstilo = entrada.getValue();
            Matcher matcher = patronEstilo.pattern.matcher(texto);

            while (matcher.find()) {
                // Ahora, verificamos si matcher.find() es true antes de acceder a los grupos
                if (matcher.groupCount() >= 1) {
                    int start = matcher.start(1);
                    int end = matcher.end(1);
                    if (start >= 0 && end <= texto.length()) {
                        int finalStart = start;
                        int finalLength = end - start;

                        SwingUtilities.invokeLater(() -> {
                            // Se añade el estilo de manera eficiente para la porción del texto
                            Style estilo = doc.addStyle(nombre, null);
                            patronEstilo.aplicarFormato.accept(estilo, matcher);
                            doc.setCharacterAttributes(finalStart, finalLength, estilo, false);
                        });
                    }
                }
            }
        }
    }

    /**
     * Método optimizado: solo aplica estilos si se detectan caracteres clave cerca del cambio.
     */
    public static void aplicarEstilosOptimizado(JTextPane campoContenido, DocumentEvent e) {
        int offset = e.getOffset();
        int length = e.getLength();

        try {
            String texto = campoContenido.getDocument().getText(0, campoContenido.getDocument().getLength());
            char anterior = offset > 0 ? texto.charAt(offset - 1) : ' ';
            char actual = offset < texto.length() ? texto.charAt(offset) : ' ';

            // Solo aplicar si el carácter está relacionado con algún estilo
            if (esCaracterDeEstilo(anterior) || esCaracterDeEstilo(actual)) {
                SwingUtilities.invokeLater(() -> aplicarEstilos(campoContenido));
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Clase auxiliar para definir un patrón y su acción de estilo.
     */
    private static class PatronEstilo {
        final Pattern pattern;
        final BiConsumer<Style, Matcher> aplicarFormato;

        PatronEstilo(String regex, BiConsumer<Style, Matcher> aplicarFormato) {
            this.pattern = Pattern.compile(regex);
            this.aplicarFormato = aplicarFormato;
        }
    }
}
