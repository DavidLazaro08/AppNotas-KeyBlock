package vista;

import javax.swing.*;
import java.awt.*;

public class EstiloVisual {

    public static JButton crearBotonCircular(String texto, int diametro, Color color) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? color.darker() : color);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(diametro, diametro);
            }
        };

        boton.setFont(new Font("SansSerif", Font.BOLD, 24));
        boton.setForeground(Color.WHITE);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);

        return boton;
    }

    // Aquí podemos añadir más métodos visuales que hagan falta
}
