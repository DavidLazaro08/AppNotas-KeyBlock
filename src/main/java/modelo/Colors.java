package modelo;
import java.awt.Color;
import java.util.Random;

public class Colors {

    public static Color generarColorAleatorio() {
        Random rand = new Random();

        // Generar valores RGB en un rango adecuado para colores pastel
        int r = Math.min(rand.nextInt(156) + 100, 255); // Rango entre 150 y 255
        int g = Math.min(rand.nextInt(156) + 100, 255); // Rango entre 150 y 255
        int b = Math.min(rand.nextInt(156) + 100, 255); // Rango entre 150 y 255


        return new Color(r, g, b);
    }
}
