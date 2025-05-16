package modelo;

import modelo.Nota;
import vista.EditarNotaVista;
import controlador.EditorEstiloNotas;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import javax.swing.JTextPane;
import java.util.ArrayList;

import static controlador.NotasControlador.procesarHashtags;

public class ActualizarNota {

    /**
     * 🎨 Vincula los campos de texto con la nota y aplica estilos al hashtag.
     * Solo aplica color, sin guardar la nota automáticamente.
     */
    public static void vincularCampos(EditarNotaVista vista, Nota nota) {
        vincularCampo(vista.getCampoTitulo(), (texto, e) -> nota.setTitulo(texto));
        vincularCampo(vista.getCampoContenido(), (texto, e) -> nota.setContenido(texto));

        vincularCampo(vista.getCampoHashtags(), (texto, e) -> {
            if (nota.getHashtags() == null) {
                nota.setHashtags(new ArrayList<>());
            } else {
                nota.getHashtags().clear();
            }

            procesarHashtags(texto, nota);
            EditorEstiloNotas.aplicarEstilosOptimizado((JTextPane) vista.getCampoHashtags(), e);
        });
    }

    private static void vincularCampo(JTextComponent campo, CampoListener listener) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { actualizar(e); }
            public void removeUpdate(DocumentEvent e) { actualizar(e); }
            public void changedUpdate(DocumentEvent e) { actualizar(e); }

            private void actualizar(DocumentEvent e) {
                listener.cambio(campo.getText(), e);
            }
        });
    }

    @FunctionalInterface
    private interface CampoListener {
        void cambio(String texto, DocumentEvent e);
    }
}

