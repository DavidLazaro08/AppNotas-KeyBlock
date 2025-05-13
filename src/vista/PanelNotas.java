package vista;

import javax.swing.*;
import java.awt.*;

/** PanelNotas representa la vista principal donde se mostrarán las notas del usuario.
 *  Por ahora es un placeholder visual preparado para incluir más adelante una lista de notas reales.
 *
 * ➤ Asociado a "Notas" en el CardLayout desde PrincipalVista. */
public class PanelNotas extends JPanel {

    public PanelNotas() {
        setLayout(new BorderLayout());

        JLabel mensaje = new JLabel("Aquí se mostrarán tus notas 🗒️", SwingConstants.CENTER);
        mensaje.setFont(new Font("SansSerif", Font.PLAIN, 20));
        mensaje.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
        add(mensaje, BorderLayout.CENTER);
    }
}
