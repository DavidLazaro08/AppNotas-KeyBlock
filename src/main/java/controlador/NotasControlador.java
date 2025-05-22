package controlador;

import modelo.*;
import vista.EditarNotaVista;

import javax.swing.*;
import java.time.LocalDate;
import java.util.regex.*;

/* Clase NotasControlador que gestiona la creación y edición de notas.
 *
 * ➤ Controla el vínculo entre la vista de edición y el modelo de nota.
 * ➤ Aplica estilo visual a hashtags y guarda en base de datos.
 * ➤ Se encarga de refrescar la interfaz tras guardar la nota. */

public class NotasControlador {

    /* Extrae los hashtags del texto escrito y los añade al objeto Nota.
     * Usa expresiones regulares para identificar palabras que empiecen por #. */

    public static void procesarHashtags(String textoHashtags, Nota nota) {
        Pattern pattern = Pattern.compile("#([\\p{L}0-9_-]+)");
        Matcher matcher = pattern.matcher(textoHashtags);
        while (matcher.find()) {
            String texto = matcher.group(1);
            nota.setHashtag(new Hashtag(texto));
        }
    }

    /* Crea la ventana de edición de notas y vincula los campos a un objeto Nota.
     * Se utiliza un solo objeto Nota reutilizable y se enlaza con los campos visuales.
     *
     * ➤ Incluye botón para aplicar estilos a hashtags manualmente.
     * ➤ Guarda la nota en la base de datos al pulsar "Guardar". */

    public static void crearYEditarNota(JFrame padre) {
        EditarNotaVista vista = new EditarNotaVista(padre);

        // ✅ Usamos una sola nota
        Nota nota = new Nota(0, "", "", LocalDate.now(), 1);
        ActualizarNota.vincularCampos(vista, nota);

        vista.getBtnGuardar().addActionListener(e -> {
            try {
                // ⛔ Ya no se crea una nota nueva, usamos la enlazada
                NotaDAO.guardarNota(nota);
                JOptionPane.showMessageDialog(vista.getDialogo(),
                        "Nota guardada en la base de datos.");
                vista.getDialogo().dispose();


            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista.getDialogo(),
                        "Error al guardar la nota.");
            }
        });

        vista.mostrar();
    }

    public static void EditarNota(JFrame padre, String titulo, String contenido) {
        EditarNotaVista vista = new EditarNotaVista(padre);

        // Creamos una nota base con datos recibidos
        Nota nota = new Nota(0, titulo, contenido, java.time.LocalDate.now(), 1);

        // Enlazamos campos visuales con la nota
        ActualizarNota.vincularCampos(vista, nota);

        // Prellenar los campos visuales
        vista.getCampoTitulo().setText(titulo);
        vista.getCampoContenido().setText(contenido);


        // Acción de guardar
        vista.getBtnGuardar().addActionListener(e -> {
            try {
                NotaDAO.guardarNota(nota); // actualiza o inserta según lógica interna
                JOptionPane.showMessageDialog(vista.getDialogo(), "Nota guardada en la base de datos.");
                vista.getDialogo().dispose();

                if (padre instanceof vista.PrincipalVista) {
                    ((vista.PrincipalVista) padre).refrescarNotas();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista.getDialogo(), "Error al guardar la nota.");
            }
        });

        vista.mostrar();
    }

}
