package modelo;

import modelo.Hashtag;
import modelo.Nota;
import vista.EditarNotaVista;
import controlador.EditorEstiloNotas;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.util.ArrayList;

import static controlador.NotasControlador.procesarHashtags;

public class ActualizarNota {

    public static void vincularCampos(EditarNotaVista vista, Nota nota) {
        // 1. Vincular título
        vincularCampo(vista.getCampoTitulo(), (texto,e) -> nota.setTitulo(texto));

        // 2. Vincular contenido
        vincularCampo(vista.getCampoContenido(), (texto,e) -> {
            nota.setContenido(texto);
            // Ya no aplicamos los estilos en cada cambio, solo lo haremos cuando se pulse el botón de actualizar
            // EditorEstiloNotas.aplicarEstilosOptimizado(vista.getCampoContenido(), e);
        });

        // 3. Vincular hashtags
        vincularCampo(vista.getCampoHashtags(), (texto,e) -> {
            if (nota.getHashtags() == null) {
                nota.setHashtags(new ArrayList<>());
            } else {
                nota.getHashtags().clear();
            }

            procesarHashtags(texto, nota);
        });
    }

    private static void vincularCampo(JTextComponent campo, CampoListener listener) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            // El listener se activa cuando se inserta, borra o actualiza el campo que sea (título, contenido o hashtags)
            public void insertUpdate(DocumentEvent e) { actualizar(e); }
            public void removeUpdate(DocumentEvent e) { actualizar(e); }
            public void changedUpdate(DocumentEvent e) { actualizar(e); }

            private void actualizar(DocumentEvent e) {
                listener.cambio(campo.getText(),e);
            }
        });
    }

    // Functional interface solo tiene 1 único metodo
    @FunctionalInterface
    private interface CampoListener {
        void cambio(String texto, DocumentEvent e);
    }
}
