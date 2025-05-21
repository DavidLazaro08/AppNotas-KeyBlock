package controlador;

import modelo.Nota;
import vista.EditarNotaVista;
import controlador.EditorEstiloNotas;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import javax.swing.JTextPane;
import java.util.ArrayList;

import static controlador.NotasControlador.procesarHashtags;

/* Clase ActualizarNota que se encarga de conectar los campos de texto con la nota que se está editando.
 *
 * ➤ Cada vez que se escribe algo, los cambios se guardan automáticamente en el objeto Nota.
 * ➤ Además, si se están escribiendo hashtags, se procesan en tiempo real y se colorean en pantalla.
 * ➤ Esta clase no guarda nada en la base de datos, solo mantiene sincronizado lo que se ve con lo que se va a guardar.
 * ➤ Usa un pequeño sistema propio para detectar cambios en cada campo y reaccionar de forma ordenada. */

public class ActualizarNota {

    // ---------------------- MÉTODO PÚBLICO ----------------------

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

    // ---------------------- MÉTODO AUXILIAR ----------------------

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

    // ---------------------- INTERFAZ FUNCIONAL ----------------------

    @FunctionalInterface
    private interface CampoListener {
        void cambio(String texto, DocumentEvent e);
    }
}
