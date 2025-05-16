package controlador;

import modelo.*;
import vista.EditarNotaVista;

import javax.swing.*;
import java.time.LocalDate;
import java.util.regex.*;

public class NotasControlador {

    public static void procesarHashtags(String textoHashtags, Nota nota) {
        Pattern pattern = Pattern.compile("#([\\p{L}0-9_-]+)");
        Matcher matcher = pattern.matcher(textoHashtags);
        while (matcher.find()) {
            String texto = matcher.group(1);
            nota.setHashtag(new Hashtag(texto));
        }
    }

    public static void crearYEditarNota(JFrame padre) {
        EditarNotaVista vista = new EditarNotaVista(padre);

        // ✅ Usamos una sola nota
        Nota nota = new Nota(0, "", "", LocalDate.now(), 1);
        ActualizarNota.vincularCampos(vista, nota);

        JButton btnActualizarEstilos = new JButton("Actualizar Estilos");
        btnActualizarEstilos.addActionListener(e -> EditorEstiloNotas.aplicarEstilos(vista.getCampoContenido()));
        vista.getPanelBotones().add(btnActualizarEstilos);

        vista.getBtnGuardar().addActionListener(e -> {
            try {
                // ⛔ Ya no se crea una nota nueva, usamos la enlazada
                NotaDAO.guardarNota(nota);
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
