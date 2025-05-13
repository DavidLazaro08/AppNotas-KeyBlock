package vista;

import javax.swing.*;
import java.awt.*;

/** PanelContras representa la vista secundaria para gestión de contraseñas.
 *  Por ahora es solo un diseño inicial a la espera de conectar con datos reales.
 *
 * ➤ Asociado a "Contras" en el CardLayout desde PrincipalVista. */
public class PanelContras extends JPanel {

    public PanelContras() {
        setLayout(new BorderLayout());
        setBackground(new Color(232, 245, 233)); // Verde muy suave

        JLabel mensaje = new JLabel("Aquí irán tus contraseñas 🔐", SwingConstants.CENTER);
        mensaje.setFont(new Font("SansSerif", Font.PLAIN, 20));
        mensaje.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
        add(mensaje, BorderLayout.CENTER);
    }
}
