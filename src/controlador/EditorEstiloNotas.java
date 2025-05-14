package controlador;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.regex.*;

    // Metodo principal para aplicar estilos al contenido de la nota
    public class EditorEstiloNotas {

        // Método para aplicar estilos cuando se detectan ciertos patrones
        public static void aplicarEstilosSiEsNecesario(JTextPane campoContenido) {
            String texto = campoContenido.getText();

            // Buscar patrones específicos como "#", "*", "_", etc.
            if (texto.contains("#")) {
                aplicarEstiloEncabezado(texto, campoContenido.getStyledDocument());
            }
            if (texto.contains("**")) {
                aplicarEstiloNegrita(texto, campoContenido.getStyledDocument());
            }
            if (texto.contains("*")) {
                aplicarEstiloCursiva(texto, campoContenido.getStyledDocument());
            }
            if (texto.contains("~~")) {
                aplicarEstiloTachado(texto, campoContenido.getStyledDocument());
            }
            if (texto.contains("`")) {
                aplicarEstiloCodigo(texto, campoContenido.getStyledDocument());
            }
            if (texto.contains("-")) {
                aplicarEstiloLista(texto, campoContenido.getStyledDocument());
            }
            if (texto.contains(">")) {
                aplicarEstiloCita(texto, campoContenido.getStyledDocument());
            }
        }

        // Aplicar estilo a los encabezados (#, ##, ###, etc.)
        private static void aplicarEstiloEncabezado(String texto, StyledDocument doc) {
            // Expresión regular que captura los encabezados desde # hasta ######, con un texto después
            // El patrón capturará hasta el final de la línea, gracias al `(?=\n|$)`
            Pattern pattern = Pattern.compile("^(#{1,6}) (.*)(?=\n|$)", Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(texto);

            while (matcher.find()) {
                int start = matcher.start(2);  // Empezar desde el texto del encabezado
                int end = matcher.end(2);      // Terminar al final del texto del encabezado
                int numHashes = matcher.group(1).length();  // Número de # (h1, h2, h3, ... h6)

                if (start >= 0 && end <= texto.length()) {
                    final int fontSize = 24 - (numHashes * 2); // Reducir el tamaño de la fuente según el número de # (h1 = 20, h2 = 18, etc.)

                    // Utilizar SwingUtilities.invokeLater para hacer los cambios en el hilo de la interfaz gráfica
                    SwingUtilities.invokeLater(() -> {
                        Style estiloEncabezado = doc.addStyle("encabezado" + numHashes, null);
                        StyleConstants.setFontSize(estiloEncabezado, fontSize);
                        StyleConstants.setBold(estiloEncabezado, true);
                        doc.setCharacterAttributes(start, end - start, estiloEncabezado, false);
                    });
                }
            }
        }



        // Aplicar estilo a los elementos en negrita (**negrita**)
        private static void aplicarEstiloNegrita(String texto, StyledDocument doc) {
            Pattern pattern = Pattern.compile("\\*\\*(.*?)\\*\\*");
            Matcher matcher = pattern.matcher(texto);

            while (matcher.find()) {
                int start = matcher.start(1);
                int end = matcher.end(1);

                if (start >= 0 && end <= texto.length()) {
                    SwingUtilities.invokeLater(() -> {
                        Style estiloNegrita = doc.addStyle("negrita", null);
                        StyleConstants.setBold(estiloNegrita, true);
                        doc.setCharacterAttributes(start, end - start, estiloNegrita, false);
                    });
                }
            }
        }

        // Aplicar estilo a los elementos en cursiva (*cursiva*)
        private static void aplicarEstiloCursiva(String texto, StyledDocument doc) {
            Pattern pattern = Pattern.compile("\\*(.*?)\\*");
            Matcher matcher = pattern.matcher(texto);

            while (matcher.find()) {
                int start = matcher.start(1);
                int end = matcher.end(1);

                if (start >= 0 && end <= texto.length()) {
                    SwingUtilities.invokeLater(() -> {
                        Style estiloCursiva = doc.addStyle("cursiva", null);
                        StyleConstants.setItalic(estiloCursiva, true);
                        doc.setCharacterAttributes(start, end - start, estiloCursiva, false);
                    });
                }
            }
        }

        // Aplicar estilo a los elementos tachados (~~tachado~~)
        private static void aplicarEstiloTachado(String texto, StyledDocument doc) {
            Pattern pattern = Pattern.compile("~~(.*?)~~");
            Matcher matcher = pattern.matcher(texto);

            while (matcher.find()) {
                int start = matcher.start(1);
                int end = matcher.end(1);

                if (start >= 0 && end <= texto.length()) {
                    SwingUtilities.invokeLater(() -> {
                        Style estiloTachado = doc.addStyle("tachado", null);
                        StyleConstants.setStrikeThrough(estiloTachado, true);
                        doc.setCharacterAttributes(start, end - start, estiloTachado, false);
                    });
                }
            }
        }

        // Aplicar estilo a los bloques de código (`código`)
        private static void aplicarEstiloCodigo(String texto, StyledDocument doc) {
            Pattern pattern = Pattern.compile("`([^`]+)`");
            Matcher matcher = pattern.matcher(texto);

            while (matcher.find()) {
                int start = matcher.start(1);
                int end = matcher.end(1);

                if (start >= 0 && end <= texto.length()) {
                    SwingUtilities.invokeLater(() -> {
                        Style estiloCodigo = doc.addStyle("codigo", null);
                        StyleConstants.setFontFamily(estiloCodigo, "Courier New");
                        StyleConstants.setForeground(estiloCodigo, Color.GREEN);
                        doc.setCharacterAttributes(start, end - start, estiloCodigo, false);
                    });
                }
            }
        }

        // Aplicar estilo a las listas (- item)
        private static void aplicarEstiloLista(String texto, StyledDocument doc) {
            Pattern pattern = Pattern.compile("(?m)^- (.*)");
            Matcher matcher = pattern.matcher(texto);

            while (matcher.find()) {
                int start = matcher.start(1);
                int end = matcher.end(1);

                if (start >= 0 && end <= texto.length()) {
                    SwingUtilities.invokeLater(() -> {
                        Style estiloLista = doc.addStyle("lista", null);
                        StyleConstants.setItalic(estiloLista, true);
                        doc.setCharacterAttributes(start, end - start, estiloLista, false);
                    });
                }
            }
        }

        // Aplicar estilo a las citas (> cita)
        private static void aplicarEstiloCita(String texto, StyledDocument doc) {
            Pattern pattern = Pattern.compile("(?m)^> (.*)");
            Matcher matcher = pattern.matcher(texto);

            while (matcher.find()) {
                int start = matcher.start(1);
                int end = matcher.end(1);

                if (start >= 0 && end <= texto.length()) {
                    SwingUtilities.invokeLater(() -> {
                        Style estiloCita = doc.addStyle("cita", null);
                        StyleConstants.setForeground(estiloCita, Color.GRAY);
                        StyleConstants.setItalic(estiloCita, true);
                        doc.setCharacterAttributes(start, end - start, estiloCita, false);
                    });
                }
            }
        }
    }

