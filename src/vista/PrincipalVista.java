package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** Clase PrincipalVista que representa la ventana principal del programa.
 * Usa BorderLayout como distribución principal y un panel central con CardLayout
 * para ir cambiando entre vistas internas (como notas y contraseñas).
 *
 * ➤ Inspirado en teoría módulo 1.3 (BorderLayout), 1.6 (CardLayout)
 * ➤ Requisitos del proyecto: uso de interfaz gráfica organizada, mínimo 2 vistas conectadas.
 * ➤ Estilo visual mejorado con botón "+" central y hover (MouseListener - teoría 1.4)
 */

public class PrincipalVista extends JFrame {

    // ---------------------- ATRIBUTOS ----------------------
    private JPanel panelCartas;
    private CardLayout cardLayout;

    // ---------------------- CONSTRUCTOR ----------------------
    public PrincipalVista() {
        setTitle("NotasApp - Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---------------------- PANEL SUPERIOR ----------------------
        JLabel titulo = new JLabel("📝 Mis Notas y Más", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // ---------------------- PANEL CENTRAL CON CARDLAYOUT ----------------------
        cardLayout = new CardLayout();
        panelCartas = new JPanel(cardLayout);
        JPanel panelNotas = new PanelNotas();
        JPanel panelContras = new PanelContras();
        panelCartas.add(panelNotas, "Notas");
        panelCartas.add(panelContras, "Contras");
        add(panelCartas, BorderLayout.CENTER);

        // ---------------------- PANEL INFERIOR DE NAVEGACIÓN (simplificado y adaptativo) ----------------------
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10)); // centrado y con márgenes
        panelInferior.setPreferredSize(new Dimension(800, 100));
        panelInferior.setBackground(new Color(245, 245, 245));

        JButton btnVerNotas = new JButton("📄 Ver Notas");
        JButton btnVerContras = new JButton("🔐 Contraseñas");
        JButton botonMas = EstiloVisual.crearBotonCircular("+", 80, new Color(33, 150, 243));
        botonMas.setFont(new Font("SansSerif", Font.BOLD, 36)); // más presencia visual


// Añadir los botones en orden
        panelInferior.add(btnVerNotas);
        panelInferior.add(botonMas);
        panelInferior.add(btnVerContras);

// Añadir el panel al JFrame
        add(panelInferior, BorderLayout.SOUTH);


        // ---------------------- EVENTOS ----------------------
        btnVerNotas.addActionListener(e -> cardLayout.show(panelCartas, "Notas"));
        btnVerContras.addActionListener(e -> cardLayout.show(panelCartas, "Contras"));

        setVisible(true);
    }

    // ---------------------- MAIN DE PRUEBA ----------------------
    public static void main(String[] args) {
        new PrincipalVista();
    }
}