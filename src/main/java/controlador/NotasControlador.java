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
        Nota nota = new Nota(0, "", "", LocalDate.now(), 1);
        ActualizarNota.vincularCampos(vista, nota);

        JButton btnActualizarEstilos = new JButton("Actualizar Estilos");
        btnActualizarEstilos.addActionListener(e -> EditorEstiloNotas.aplicarEstilos(vista.getCampoContenido()));
        vista.getPanelBotones().add(btnActualizarEstilos);

        vista.getBtnGuardar().addActionListener(e -> {
            try {
                NotaDAO.guardarNota(nota);
                JOptionPane.showMessageDialog(vista.getDialogo(), "Nota guardada en la base de datos.");
                vista.getDialogo().dispose();

                // 👇 MUÉVELO AQUÍ DENTRO, DESPUÉS DE CERRAR EL DIALOG
                if (padre instanceof vista.PrincipalVista) {
                    ((vista.PrincipalVista) padre).refrescarNotas();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista.getDialogo(), "Error al guardar la nota.");
            }
        });


        vista.mostrar();

        System.out.println("✔ Nota creada:");
        System.out.println("Título: " + nota.getTitulo());
        System.out.println("Contenido: " + nota.getContenido());
        if (nota.getHashtags() != null) {
            for (var hashtag : nota.getHashtags()) {
                System.out.println("#" + hashtag.getTexto());
            }
        }
    }
}
