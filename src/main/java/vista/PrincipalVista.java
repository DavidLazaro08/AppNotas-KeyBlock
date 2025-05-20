package vista;

import controlador.NotasControlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** Clase PrincipalVista que representa la ventana principal del programa.
 * Usa BorderLayout como distribución principal y un panel central con CardLayout
 * para ir cambiando entre vistas internas (como notas y contraseñas).
 *
 * ➤ Inspirado en teoría módulo 1.3 (BorderLayout), 1.6 (CardLayout)
 * ➤ Requisitos del proyecto: uso de interfaz gráfica organizada, mínimo 2 vistas conectadas.
 * ➤ Estilo visual mejorado con botón "+" central y estética tipo entorno de desarrollo. */

public class PrincipalVista extends VentanaBase {

    // ---------------------- ATRIBUTOS ----------------------

    private JPanel panelCartas;
    private CardLayout cardLayout;
    private PanelContenido panelContenido; // ✅ Ahora se usa PanelContenido fusionado

    // ---------------------- CONSTRUCTOR ----------------------

    public PrincipalVista() {
        super("KeyBlock");

        // ---------------------- PANEL SUPERIOR ----------------------

        JLabel titulo = new JLabel("📝 Mis Notas y Más", SwingConstants.CENTER);
        titulo.setFont(fuenteTitulo);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titulo.setOpaque(true);

        // Fondo superior tipo IDE oscuro
        titulo.setBackground(colorFondoOscuro);
        titulo.setForeground(colorTextoClaro);
        add(titulo, BorderLayout.NORTH);

        // ---------------------- PANEL LATERAL IZQUIERDO (simulación menú tipo IDE) ----------------------

        JPanel panelLateral = new JPanel();
        panelLateral.setPreferredSize(new Dimension(70, 0));
        panelLateral.setBackground(colorFondoOscuro);   // igual que el superior
        panelLateral.setLayout(new GridLayout(5, 1, 0, 10)); // 5 botones con separación

        // Iconos ficticios por ahora
        String[] iconos = {"👤", "🗓️", "🌓", "🛠️", "🔍"};
        String[] tooltips = {"Usuario|Login", "Calendario", "Cambiar Tema", "Configuración", "Buscar Notas"};

        for (int i = 0; i < iconos.length; i++) {
            JButton btn = crearBotonEstiloIDE(iconos[i], fuenteNormal);
            btn.setToolTipText(tooltips[i]);
            panelLateral.add(btn);
        }

        add(panelLateral, BorderLayout.WEST);

        // ---------------------- PANEL CENTRAL CON CARDLAYOUT ----------------------

        cardLayout = new CardLayout();
        panelCartas = new JPanel(cardLayout);
        panelCartas.setBackground(colorFondoOscuro); // Fondo base unificado

        panelContenido = new PanelContenido(); // ✅ Panel fusionado con CardLayout interno
        panelCartas.add(panelContenido, "Contenido");

        add(panelCartas, BorderLayout.CENTER);

        // ---------------------- PANEL INFERIOR DE NAVEGACIÓN (modo oscuro y adaptativo) ----------------------

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelInferior.setPreferredSize(new Dimension(800, 100));
        panelInferior.setBackground(new Color(30, 30, 30)); // Fondo inferior oscuro para contraste

        // Botón para ver notas
        JButton btnVerNotas = crearBotonEstiloIDE("📄 Ver Notas", fuenteNormal);

        // Botón para ver contraseñas
        JButton btnVerContras = crearBotonEstiloIDE("🔐 Contraseñas", fuenteNormal);

        // Botón flotante "+" para crear nueva nota
        JButton botonMas = EstiloVisual.crearBotonCircular("+", 80, colorBoton);
        botonMas.setFont(new Font("SansSerif", Font.BOLD, 36));
        botonMas.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        // Añadir los botones al panel inferior (en orden)
        panelInferior.add(btnVerNotas);
        panelInferior.add(botonMas);
        panelInferior.add(btnVerContras);

        add(panelInferior, BorderLayout.SOUTH);

        // ---------------------- EVENTOS ----------------------

        btnVerNotas.addActionListener(e -> {
            panelContenido.mostrarNotas();
        });

        btnVerContras.addActionListener(e -> {
            panelContenido.mostrarContras();
        });

        botonMas.addActionListener(e -> {
            NotasControlador.crearYEditarNota(PrincipalVista.this); // envío el JFrame
        });

        setVisible(true);
    }

    // 🔄 Método público para refrescar las notas desde fuera
    public void refrescarNotas() {
        panelContenido.mostrarNotas();
    }

    public void mostrarAdmin() {
        panelContenido.mostrarAdmin();
    }


    // ---------------------- MAIN DE PRUEBA ----------------------

    // public static void main(String[] args) {
    //     new PrincipalVista();
    // }
}
