package vista;

import javax.swing.*;
import java.awt.*;

/* Clase VentanaBase que proporciona una base visual común para todas las vistas principales.
 *
 * ➤ Centraliza el estilo oscuro, la fuente y colores comunes.
 * ➤ Carga el icono de la aplicación si está disponible.
 * ➤ Ofrece método reutilizable para crear botones con estética tipo IDE. */

public class VentanaBase extends JFrame {

    // ---------------------- ATRIBUTOS DE ESTILO ----------------------

    protected Font fuenteTitulo = new Font("SansSerif", Font.BOLD, 26);
    protected Font fuenteNormal = new Font("SansSerif", Font.PLAIN, 14);

    protected Color colorFondoOscuro = new Color(25, 25, 25);
    protected Color colorTextoClaro = new Color(220, 220, 220);
    protected Color colorBoton = new Color(60, 63, 65);
    protected Color colorBotonHover = new Color(50, 53, 57);

    // ---------------------- CONSTRUCTOR ----------------------

    public VentanaBase(String titulo) {
        setTitle(titulo);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/ICON_KEYBLOCK.png"));
            setIconImage(icono.getImage());
        } catch (Exception e) {
            System.out.println("⚠️ Icono no encontrado.");
        }
    }

    // ---------------------- MÉTODO: Crear botón personalizado ----------------------

    protected JButton crearBotonEstiloIDE(String texto, Font fuente) {
        JButton boton = new JButton(texto);
        boton.setFont(fuente);
        boton.setForeground(colorTextoClaro);
        boton.setBackground(colorBoton);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(true);
        boton.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        // Hover y feedback visual
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.setBackground(colorBotonHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.setBackground(colorBoton);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                boton.setBackground(colorBotonHover.darker());
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                boton.setBackground(colorBoton);
            }
        });

        return boton;
    }
}
