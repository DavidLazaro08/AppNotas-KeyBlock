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
 * ➤ Estilo visual mejorado con botón "+" central y estética tipo entorno de desarrollo.
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
        titulo.setOpaque(true);
        titulo.setBackground(new Color(25, 25, 25)); // Fondo superior tipo IDE oscuro
        titulo.setForeground(new Color(220, 220, 220)); // Texto gris claro
        add(titulo, BorderLayout.NORTH);

        // ---------------------- PANEL LATERAL IZQUIERDO (simulación menú tipo IDE) ----------------------
        JPanel panelLateral = new JPanel();
        panelLateral.setPreferredSize(new Dimension(70, 0)); // ancho fijo estilo barra lateral
        panelLateral.setBackground(new Color(25, 25, 25));   // igual que el superior
        panelLateral.setLayout(new GridLayout(5, 1, 0, 10)); // 5 botones con separación

        // Iconos y tooltips ficticios por ahora
        String[] iconos = {"👤", "🗓️", "🌓", "⚙️", "🔍"};
        String[] tooltips = {"Usuario|Login", "Calendario", "Cambiar Tema", "Configuración", "Buscar Notas"};

        for (int i = 0; i < iconos.length; i++) {
            JButton btn = new JButton(iconos[i]);
            btn.setToolTipText(tooltips[i]);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(60, 63, 65));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(true);
            btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());

            // Hover (oscurece visualmente)
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(new Color(50, 53, 57));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(new Color(60, 63, 65));
                }
            });

            panelLateral.add(btn);
        }




        // Añadir panel al oeste del frame
        add(panelLateral, BorderLayout.WEST);

        // ---------------------- PANEL CENTRAL CON CARDLAYOUT ----------------------
        cardLayout = new CardLayout();
        panelCartas = new JPanel(cardLayout);
        panelCartas.setBackground(new Color(25, 25, 25)); // Fondo base unificado
        JPanel panelNotas = EstiloVisual.crearPanelDegradado(new Color(43, 43, 43), new Color(35, 35, 35));
        JPanel panelContras = EstiloVisual.crearPanelDegradado(new Color(36, 36, 36), new Color(28, 28, 28));
        panelCartas.add(panelNotas, "Notas");
        panelCartas.add(panelContras, "Contras");

        add(panelCartas, BorderLayout.CENTER);

        // ---------------------- PANEL INFERIOR DE NAVEGACIÓN (modo oscuro y adaptativo) ----------------------
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelInferior.setPreferredSize(new Dimension(800, 100));
        panelInferior.setBackground(new Color(30, 30, 30)); // Fondo inferior oscuro para contraste

        // Botón para ver notas
        JButton btnVerNotas = new JButton("📄 Ver Notas");
        btnVerNotas.setForeground(Color.WHITE);
        btnVerNotas.setBackground(new Color(60, 63, 65));
        btnVerNotas.setFocusPainted(false);
        btnVerNotas.setBorderPainted(false);
        btnVerNotas.setContentAreaFilled(true);
        btnVerNotas.setRolloverEnabled(false);
        btnVerNotas.setUI(new javax.swing.plaf.basic.BasicButtonUI()); // Forzamos el estilo básico

        // MouseListener para efecto pressed en btnVerNotas
        btnVerNotas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btnVerNotas.setBackground(new Color(50, 53, 57));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                btnVerNotas.setBackground(new Color(60, 63, 65));
            }
        });

        // Botón para ver contraseñas
        JButton btnVerContras = new JButton("🔐 Contraseñas");
        btnVerContras.setForeground(Color.WHITE);
        btnVerContras.setBackground(new Color(60, 63, 65));
        btnVerContras.setFocusPainted(false);
        btnVerContras.setBorderPainted(false);
        btnVerContras.setContentAreaFilled(true);
        btnVerContras.setRolloverEnabled(false);
        btnVerContras.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        // MouseListener para efecto pressed en btnVerContras
        btnVerContras.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btnVerContras.setBackground(new Color(50, 53, 57));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                btnVerContras.setBackground(new Color(60, 63, 65));
            }
        });

        // Botón flotante "+" para crear nueva nota
        JButton botonMas = EstiloVisual.crearBotonCircular("+", 80, new Color(60, 63, 65));
        botonMas.setFont(new Font("SansSerif", Font.BOLD, 36));
        botonMas.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        // Añadir los botones al panel inferior (en orden)
        panelInferior.add(btnVerNotas);
        panelInferior.add(botonMas);
        panelInferior.add(btnVerContras);

        // Añadir el panel inferior al JFrame
        add(panelInferior, BorderLayout.SOUTH);

        // ---------------------- EVENTOS ----------------------
        btnVerNotas.addActionListener(e -> cardLayout.show(panelCartas, "Notas"));
        btnVerContras.addActionListener(e -> cardLayout.show(panelCartas, "Contras"));
        botonMas.addActionListener(e -> {
            EditarNotaVista vistaEditar = new EditarNotaVista(this);
            vistaEditar.mostrar(); // abrir la vista de edición
        });

        setVisible(true);
    }

//    // ---------------------- MAIN DE PRUEBA ----------------------
//    public static void main(String[] args) {
//        new PrincipalVista();
//    }
}
