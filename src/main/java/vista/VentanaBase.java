package vista;

import javax.swing.*;
import java.awt.*;

/* Clase VentanaBase que actúa como plantilla base para todas las ventanas principales de la app.
 *
 * ➤ Aplica un diseño oscuro coherente con toda la aplicación.
 * ➤ Define fuentes, colores y estilos reutilizables.
 * ➤ Incluye un método para crear botones con apariencia moderna (tipo IDE).
 * ➤ Intenta cargar el icono KEYBLOCK como imagen representativa de la aplicación. */

public class VentanaBase extends JFrame {

    // ---------------------- ATRIBUTOS DE ESTILO ----------------------

    // Fuentes predeterminadas
    protected Font fuenteTitulo = new Font("SansSerif", Font.BOLD, 26);
    protected Font fuenteNormal = new Font("SansSerif", Font.PLAIN, 14);

    // Colores comunes usados en toda la app
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

        // Intenta cargar el icono de la aplicación
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/ICON_KEYBLOCK.png"));
            setIconImage(icono.getImage());
        } catch (Exception e) {
            System.out.println("⚠️ Icono no encontrado.");
        }
    }

    // ---------------------- MÉTODO: Crear botón personalizado ----------------------

    /**
     * Crea un botón con aspecto moderno y adaptado al estilo oscuro de la app.
     * Incluye comportamiento al pasar el ratón y al hacer clic.
     *
     * @param texto  Texto del botón
     * @param fuente Fuente que se aplicará
     * @return JButton estilizado
     */
    protected JButton crearBotonEstiloIDE(String texto, Font fuente) {
        JButton boton = new JButton(texto);
        boton.setFont(fuente);
        boton.setForeground(colorTextoClaro);
        boton.setBackground(colorBoton);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(true);
        boton.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        // Efectos de hover y clic para mejorar la experiencia visual
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
