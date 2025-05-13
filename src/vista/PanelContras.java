package vista;

import javax.swing.*;
import java.awt.*;

/**
 * PanelContras con un tono ligeramente distinto para crear contraste visual respecto a PanelNotas.
 * Inspirado en interfaces oscuras de desarrollo, manteniendo jerarquía y profundidad.
 */
public class PanelContras extends JPanel {

    public PanelContras() {
        setLayout(new BorderLayout());
        setBackground(new Color(36, 36, 36)); // Más oscuro que PanelNotas

        JLabel mensaje = new JLabel("Aquí irán tus contraseñas 🔐", SwingConstants.CENTER);
        mensaje.setFont(new Font("Consolas", Font.PLAIN, 20));
        mensaje.setForeground(new Color(187, 187, 187));
        mensaje.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));

        add(mensaje, BorderLayout.CENTER);
    }
}
