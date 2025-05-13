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

        boton.setFont(new Font("SansSerif", Font.BOLD, 24));
        boton.setForeground(Color.WHITE);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);

        return boton;
    }

    // Método para crear degradado.

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

