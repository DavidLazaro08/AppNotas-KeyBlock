package vista;

import javax.swing.*;
import java.awt.*;

/**
 * PanelNotas con estilo oscuro, inspirado en editores tipo IntelliJ o Eclipse.
 * Fondo gris oscuro, texto claro, y diseño minimalista para enfoque de desarrollo.
 */
public class PanelNotas extends JPanel {

    public PanelNotas() {
        setLayout(new BorderLayout());
        setBackground(new Color(43, 43, 43)); // Fondo oscuro

        JLabel mensaje = new JLabel("Aquí se mostrarán tus notas 🗒️", SwingConstants.CENTER);
        mensaje.setFont(new Font("Consolas", Font.PLAIN, 20)); // Fuente tipo código
        mensaje.setForeground(new Color(187, 187, 187)); // Gris claro como texto típico
        mensaje.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));

        add(mensaje, BorderLayout.CENTER);
    }
}
