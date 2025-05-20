package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaBase extends JFrame {

    protected Font fuenteTitulo = new Font("SansSerif", Font.BOLD, 26);
    protected Font fuenteNormal = new Font("SansSerif", Font.PLAIN, 14);

    protected Color colorFondoOscuro = new Color(25, 25, 25);
    protected Color colorTextoClaro = new Color(220, 220, 220);
    protected Color colorBoton = new Color(60, 63, 65);
    protected Color colorBotonHover = new Color(50, 53, 57);

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

    protected JButton crearBotonEstiloIDE(String texto, Font fuente) {
        JButton boton = new JButton(texto);
        boton.setFont(fuente);
        boton.setForeground(colorTextoClaro);
        boton.setBackground(colorBoton);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(true);
        boton.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        // Hover
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

