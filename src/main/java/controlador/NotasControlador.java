package controlador;

import modelo.*;
import vista.EditarNotaVista;
import vista.PrincipalVista;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.regex.*;

/* Clase NotasControlador que gestiona la creación y edición de notas.
 *
 * ➤ Controla el vínculo entre la vista de edición y el modelo de nota.
 * ➤ Aplica estilo visual a hashtags y guarda en base de datos.
 * ➤ Se encarga de refrescar la interfaz tras guardar la nota. */

public class NotasControlador {

    // ---------------------- HASHTAGS ----------------------

    /* Extrae todos los hashtags válidos del campo de texto y los añade a la nota.
     * ➤ Se usa cuando el usuario escribe en el campo correspondiente. */
    public static void procesarHashtags(String textoHashtags, Nota nota) {
        Pattern pattern = Pattern.compile("#([\\p{L}0-9_-]+)");
        Matcher matcher = pattern.matcher(textoHashtags);
        while (matcher.find()) {
            String texto = matcher.group(1);
            nota.setHashtag(new Hashtag(texto));
        }
    }

    // ---------------------- NUEVA NOTA ----------------------

    /* Lanza la vista de edición para crear una nueva nota vacía.
     * ➤ Se obtiene el ID del usuario logueado para asignarlo a la nota.
     * ➤ Al guardar, se cierra la ventana y se refresca el panel principal. */
    public static void crearYEditarNota(JFrame padre) {
        EditarNotaVista vista = new EditarNotaVista(padre);

        int idUsuario = 1; // Por defecto (aunque normalmente esto nunca debería usarse)
        if (padre instanceof PrincipalVista) {
            Usuario u = ((PrincipalVista) padre).getUsuarioLogueado();
            if (u != null) {
                idUsuario = u.getId();
            }
        }

        Nota nota = new Nota(0, "", "", LocalDate.now(), idUsuario);
        ActualizarNota.vincularCampos(vista, nota);

        vista.getBtnGuardar().addActionListener(e -> {
            try {
                NotaDAO.guardarNota(nota);

                // Estilo oscuro en mensajes de éxito o error
                UIManager.put("OptionPane.background", new Color(43, 43, 43));
                UIManager.put("Panel.background", new Color(43, 43, 43));
                UIManager.put("OptionPane.messageForeground", Color.WHITE);
                UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.PLAIN, 13));

                JOptionPane.showMessageDialog(vista.getDialogo(), "Nota guardada en la base de datos.");
                vista.getDialogo().dispose();

                if (padre instanceof PrincipalVista) {
                    ((PrincipalVista) padre).refrescarNotas();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista.getDialogo(), "Error al guardar la nota.");
            }
        });

        vista.mostrar();
    }

    // ---------------------- EDITAR NOTA EXISTENTE ----------------------

    /* Lanza el editor para una nota ya existente (recibe título y contenido).
     * ➤ Se genera un nuevo objeto Nota pero con el contenido ya precargado.
     * ➤ Guarda como si fuera una nueva nota, ya que no estamos modificando la original. */
    public static void EditarNota(JFrame padre, String titulo, String contenido) {
        EditarNotaVista vista = new EditarNotaVista(padre);

        int idUsuario = 1;
        if (padre instanceof PrincipalVista) {
            Usuario u = ((PrincipalVista) padre).getUsuarioLogueado();
            if (u != null) {
                idUsuario = u.getId();
            }
        }

        Nota nota = new Nota(0, titulo, contenido, LocalDate.now(), idUsuario);
        ActualizarNota.vincularCampos(vista, nota);

        // Precargamos los campos visuales con la información original
        vista.getCampoTitulo().setText(titulo);
        vista.getCampoContenido().setText(contenido);

        vista.getBtnGuardar().addActionListener(e -> {
            try {
                NotaDAO.guardarNota(nota);
                JOptionPane.showMessageDialog(vista.getDialogo(), "Nota guardada en la base de datos.");
                vista.getDialogo().dispose();

                if (padre instanceof PrincipalVista) {
                    ((PrincipalVista) padre).refrescarNotas();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista.getDialogo(), "Error al guardar la nota.");
            }
        });

        vista.mostrar();
    }
}
