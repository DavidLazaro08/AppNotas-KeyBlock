package controlador;

import modelo.*;
import vista.EditarNotaVista;
import vista.PrincipalVista;

import javax.swing.*;
import java.time.LocalDate;
import java.util.regex.*;

/* Clase NotasControlador que gestiona la creación y edición de notas.
 *
 * ➤ Controla el vínculo entre la vista de edición y el modelo de nota.
 * ➤ Aplica estilo visual a hashtags y guarda en base de datos.
 * ➤ Se encarga de refrescar la interfaz tras guardar la nota. */

public class NotasControlador {

    // ---------------------- HASHTAGS ----------------------

    public static void procesarHashtags(String textoHashtags, Nota nota) {
        Pattern pattern = Pattern.compile("#([\\p{L}0-9_-]+)");
        Matcher matcher = pattern.matcher(textoHashtags);
        while (matcher.find()) {
            String texto = matcher.group(1);
            nota.setHashtag(new Hashtag(texto));
        }
    }

    // ---------------------- NUEVA NOTA ----------------------

    public static void crearYEditarNota(JFrame padre) {
        EditarNotaVista vista = new EditarNotaVista(padre);

        // ✅ Obtener usuario logueado para asignar su ID
        int idUsuario = 1; // valor por defecto
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
