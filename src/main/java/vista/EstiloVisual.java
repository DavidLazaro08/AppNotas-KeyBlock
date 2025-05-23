package vista;

import javax.swing.*;
import java.awt.*;

/** Clase EstiloVisual con métodos estáticos para aplicar estilos gráficos reutilizables.
 *
 * ➤ Centraliza la estética de la aplicación: botones personalizados y fondos con degradado.
 * ➤ Facilita la consistencia visual en toda la interfaz. */

public class EstiloVisual {

    // ---------------------- BOTÓN CIRCULAR PERSONALIZADO ----------------------

    // Crea un botón circular con comportamiento visual al pasar o pulsar el ratón.
    // Lo usamos para el "+" de Añadir Nota. Su estilo recuerda a los botones flotantes de apps móviles.

    public static JButton crearBotonCircular(String texto, int diametro, Color color) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Se aplica un color más oscuro si se pulsa o pasa el ratón
                Color c = color;
                if (getModel().isPressed()) {
                    c = color.darker().darker();
                } else if (getModel().isRollover()) {
                    c = color.darker();
                }

                g2.setColor(c);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(diametro, diametro);
            }
        };

        // Ajustes para evitar bordes, foco o fondos predeterminados
        boton.setFont(new Font("SansSerif", Font.BOLD, 24));
        boton.setForeground(new Color(220, 220, 220));
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);

        return boton;
    }

    // ---------------------- PANEL CON DEGRADADO VERTICAL ----------------------

    // Devuelve un panel con fondo degradado entre dos colores.
    // Se usa para darle un aspecto visual más moderno al panel inicial.

    public static JPanel crearPanelDegradado(Color color1, Color color2) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }
}
