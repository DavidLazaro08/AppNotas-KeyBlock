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
        vincularCampo(vista.getCampoTitulo(), texto -> nota.setTitulo(texto));

        // 2. Vincular contenido
        vincularCampo(vista.getCampoContenido(), texto -> {
            nota.setContenido(texto);
            // Llamar a EditorEstiloNotas para aplicar estilos cada vez que el contenido cambie
            EditorEstiloNotas.aplicarEstilosSiEsNecesario(vista.getCampoContenido());
        });

        // 3. Vincular hashtags
        vincularCampo(vista.getCampoHashtags(), texto -> {
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
            public void insertUpdate(DocumentEvent e) { actualizar(); }
            public void removeUpdate(DocumentEvent e) { actualizar(); }
            public void changedUpdate(DocumentEvent e) { actualizar(); }

            private void actualizar() {
                listener.cambio(campo.getText());
            }
        });
    }

    // Functional interface solo tiene 1 único metodo
    @FunctionalInterface
    private interface CampoListener {
        void cambio(String texto);
    }
}
