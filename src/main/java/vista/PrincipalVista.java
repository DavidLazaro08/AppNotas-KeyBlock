package vista;

import controlador.NotasControlador;
import modelo.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** Clase PrincipalVista que representa la ventana principal del programa.
 * Usa BorderLayout como distribución principal y un panel central con CardLayout
 * para ir cambiando entre vistas internas (como notas, contraseñas o administración).
 *
 * ➤ Inspirado en teoría módulo 1.3 (BorderLayout), 1.6 (CardLayout)
 * ➤ Requisitos del proyecto: uso de interfaz gráfica organizada, mínimo 2 vistas conectadas.
 * ➤ Estilo visual mejorado con botón "+" central y estética tipo entorno de desarrollo. */

public class PrincipalVista extends VentanaBase {

    // ---------------------- ATRIBUTOS ----------------------

    private JPanel panelCartas;
    private CardLayout cardLayout;
    private PanelContenido panelContenido; // ✅ Ahora se usa PanelContenido fusionado
    private JLabel lblTituloCabecera;
    private String usuarioLogueado;

    // ---------------------- CONSTRUCTOR ----------------------

    public PrincipalVista(String usuarioLogueado) {
        super("KeyBlock");
        this.usuarioLogueado = usuarioLogueado;

        // ---------------------- PANEL SUPERIOR ----------------------

        lblTituloCabecera = new JLabel("📝 Mis Notas y Más", SwingConstants.CENTER);
        lblTituloCabecera.setFont(fuenteTitulo);
        lblTituloCabecera.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblTituloCabecera.setOpaque(true);
        lblTituloCabecera.setBackground(colorFondoOscuro);
        lblTituloCabecera.setForeground(colorTextoClaro);
        add(lblTituloCabecera, BorderLayout.NORTH);

        // ---------------------- PANEL LATERAL IZQUIERDO ----------------------

        JPanel panelLateral = new JPanel();
        panelLateral.setPreferredSize(new Dimension(70, 0));
        panelLateral.setBackground(colorFondoOscuro);
        panelLateral.setLayout(new GridLayout(5, 1, 0, 10));

        String[] iconos = {"👤", "🗓️", "🌓", "🛠️", "🔍"};
        String[] tooltips = {"Usuario|Login", "Calendario", "Cambiar Tema", "Configuración", "Buscar Notas"};

        for (int i = 0; i < iconos.length; i++) {
            JButton btn = crearBotonEstiloIDE(iconos[i], fuenteNormal);
            btn.setToolTipText(tooltips[i]);

            if (tooltips[i].equals("Configuración")) {
                btn.addActionListener(e -> {
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    if (usuarioDAO.esAdmin(usuarioLogueado)) {
                        mostrarAdmin();
                    } else {
                        JPanel panel = new JPanel(new GridLayout(2, 2));
                        panel.setBackground(new Color(43, 43, 43));

                        JLabel lblUser = new JLabel("Usuario Admin:");
                        JLabel lblPass = new JLabel("Contraseña:");
                        lblUser.setForeground(Color.WHITE);
                        lblPass.setForeground(Color.WHITE);

                        JTextField tfUsuario = new JTextField();
                        JPasswordField pfClave = new JPasswordField();

                        panel.add(lblUser);
                        panel.add(tfUsuario);
                        panel.add(lblPass);
                        panel.add(pfClave);

                        UIManager.put("OptionPane.background", new Color(43, 43, 43));
                        UIManager.put("Panel.background", new Color(43, 43, 43));
                        UIManager.put("OptionPane.messageForeground", Color.WHITE);
                        UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.PLAIN, 13));

                        int opcion = JOptionPane.showConfirmDialog(this, panel, "Acceso de Administrador", JOptionPane.OK_CANCEL_OPTION);

                        if (opcion == JOptionPane.OK_OPTION) {
                            String user = tfUsuario.getText();
                            String pass = new String(pfClave.getPassword());
                            if (usuarioDAO.validarUsuario(user, pass) && usuarioDAO.esAdmin(user)) {
                                mostrarAdmin();
                            } else {
                                JOptionPane.showMessageDialog(this, "Credenciales incorrectas o sin privilegios", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });
            }

            panelLateral.add(btn);
        }

        add(panelLateral, BorderLayout.WEST);

        // ---------------------- PANEL CENTRAL CON CARDLAYOUT ----------------------

        cardLayout = new CardLayout();
        panelCartas = new JPanel(cardLayout);
        panelCartas.setBackground(colorFondoOscuro);

        panelContenido = new PanelContenido();
        panelCartas.add(panelContenido, "Contenido");

        add(panelCartas, BorderLayout.CENTER);

        // ---------------------- PANEL INFERIOR DE NAVEGACIÓN (modo oscuro y adaptativo) ----------------------

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelInferior.setPreferredSize(new Dimension(800, 100));
        panelInferior.setBackground(new Color(30, 30, 30));

        JButton btnVerNotas = crearBotonEstiloIDE("📄 Ver Notas", fuenteNormal);
        JButton btnVerContras = crearBotonEstiloIDE("🔐 Contraseñas", fuenteNormal);

        JButton botonMas = EstiloVisual.crearBotonCircular("+", 80, colorBoton);
        botonMas.setFont(new Font("SansSerif", Font.BOLD, 36));
        botonMas.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        panelInferior.add(btnVerNotas);
        panelInferior.add(botonMas);
        panelInferior.add(btnVerContras);

        add(panelInferior, BorderLayout.SOUTH);

        // ---------------------- EVENTOS ----------------------

        btnVerNotas.addActionListener(e -> {
            lblTituloCabecera.setText("📝 Mis Notas y Más");
            panelContenido.mostrarNotas();
        });

        btnVerContras.addActionListener(e -> {
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.setBackground(new Color(43, 43, 43));

            JLabel lblUser = new JLabel("Usuario:");
            JLabel lblPass = new JLabel("Contraseña:");
            lblUser.setForeground(Color.WHITE);
            lblPass.setForeground(Color.WHITE);

            JTextField tfUsuario = new JTextField();
            JPasswordField pfClave = new JPasswordField();

            panel.add(lblUser);
            panel.add(tfUsuario);
            panel.add(lblPass);
            panel.add(pfClave);

            UIManager.put("OptionPane.background", new Color(43, 43, 43));
            UIManager.put("Panel.background", new Color(43, 43, 43));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.PLAIN, 13));

            int opcion = JOptionPane.showConfirmDialog(this, panel, "Verificar identidad", JOptionPane.OK_CANCEL_OPTION);

            if (opcion == JOptionPane.OK_OPTION) {
                String user = tfUsuario.getText();
                String pass = new String(pfClave.getPassword());
                if (usuarioDAO.validarUsuario(user, pass)) {
                    lblTituloCabecera.setText("🔐 Gestor de Contraseñas");
                    panelContenido.mostrarContras();
                } else {
                    JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botonMas.addActionListener(e -> {
            NotasControlador.crearYEditarNota(PrincipalVista.this);
        });

        setVisible(true);
    }

    // ---------------------- MÉTODOS PÚBLICOS ----------------------

    public void refrescarNotas() {
        lblTituloCabecera.setText("📝 Mis Notas y Más");
        panelContenido.mostrarNotas();
    }

    public void mostrarAdmin() {
        lblTituloCabecera.setText("⚙️ Panel de Administración");
        panelContenido.mostrarAdmin();
    }

    // ---------------------- MAIN DE PRUEBA ----------------------

    // public static void main(String[] args) {
    //     new PrincipalVista("admin");
    // }
}
