package controlador;

import modelo.*;
import vista.EditarNotaVista;
import vista.PrincipalVista;
import javax.swing.JFrame;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotasControlador {

    public static void procesarHashtags(String textoHashtags, Nota nota) {
        // El patrón detecta solo palabras precedidas por #
        Pattern pattern = Pattern.compile("#([\\p{L}0-9_-]+)");
        Matcher matcher = pattern.matcher(textoHashtags);

        while (matcher.find()) {
            String texto = matcher.group(1); // Extrae el texto sin el '#'
            Hashtag hashtag = new Hashtag(texto);
            nota.setHashtag(hashtag); // Agrega el hashtag a la nota
        }
    }

    public static void crearYEditarNota(JFrame padre) {
        // Crear la vista del panel
        EditarNotaVista vista = new EditarNotaVista(padre);

        // Crear la nota vacía
        // HAY QUE: Ajustar el usuarioId según la app
        Nota nota = new Nota(0, "", "", LocalDate.now(), 1);

        // Vincular los campos
        ActualizarNota.vincularCampos(vista, nota);

        // Mostrar la nota
        vista.mostrar();

        // Tras cerrar el diálogo muestro la nota por consola
        System.out.println("✔ Nota creada:");
        System.out.println("Título: " + nota.getTitulo());
        System.out.println("Contenido: " + nota.getContenido());
        System.out.println("Hashtags:");

        if (nota.getHashtags() != null) {
            for (var notas : nota.getHashtags()) {
                System.out.println("#" + notas.getTexto());
            }
        }
    }
}
