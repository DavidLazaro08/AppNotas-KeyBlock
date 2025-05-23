package vista;

import controlador.LoginControlador;
import controlador.NotasControlador;
import controlador.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/* Clase PrincipalVista que representa la ventana principal de la aplicación KeyBlock.
 *
 * ➤ Muestra las notas, contraseñas o panel de administración, según el rol del usuario.
 * ➤ Integra un sistema de navegación visual dividido en cabecera, lateral, contenido central y parte inferior.
 * ➤ Usa CardLayout para cambiar entre vistas dentro del panel central.
 * ➤ Permite cerrar sesión, crear nuevas notas o cambiar de vista. */

public class PrincipalVista extends VentanaBase {

    private JPanel panelCartas;
    private CardLayout cardLayout;
    private PanelContenido panelContenido;
    private JLabel lblTituloCabecera;
    private Usuario usuarioLogueado;

    public PrincipalVista(Usuario usuarioLogueado) {
        super("KeyBlock");
        this.usuarioLogueado = usuarioLogueado;

        // ---------------------- PANEL SUPERIOR ----------------------
        // Cabecera con el título principal de la sección actual
        lblTituloCabecera = new JLabel("📝 Mis Notas y Más", SwingConstants.CENTER);
        lblTituloCabecera.setFont(fuenteTitulo);
        lblTituloCabecera.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblTituloCabecera.setOpaque(true);
        lblTituloCabecera.setBackground(colorFondoOscuro);
        lblTituloCabecera.setForeground(colorTextoClaro);
        add(lblTituloCabecera, BorderLayout.NORTH);

        // ---------------------- PANEL LATERAL IZQUIERDO ----------------------
        // Menú lateral con botones verticales de navegación
        JPanel panelLateral = new JPanel();
        panelLateral.setPreferredSize(new Dimension(70, 0));
        panelLateral.setBackground(colorFondoOscuro);
        panelLateral.setLayout(new GridLayout(5, 1, 0, 10));

        String[] iconos = {"👤", "🗓️", "🛠️", "🔍", "🚪"};
        String[] tooltips = {"Usuario|Login", "Calendario", "Configuración", "Buscar Notas", "Cerrar sesión"};

        for (int i = 0; i < iconos.length; i++) {
            JButton btn = crearBotonEstiloIDE(iconos[i], fuenteNormal);
            btn.setToolTipText(tooltips[i]);

            // Opción de configuración: si es admin entra directamente, si no, pide validación
            if (tooltips[i].equals("Configuración")) {
                btn.addActionListener(e -> {
                    if (usuarioLogueado.esAdmin()) {
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
                            if (UsuarioDAO.validarUsuario(user, pass) && UsuarioDAO.esAdmin(user)) {
                                mostrarAdmin();
                            } else {
                                JOptionPane.showMessageDialog(this, "Credenciales incorrectas o sin privilegios", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });
            }

            // Opción de cerrar sesión y volver al login
            if (tooltips[i].equals("Cerrar sesión")) {
                btn.addActionListener(e -> {
                    UIManager.put("OptionPane.background", new Color(43, 43, 43));
                    UIManager.put("Panel.background", new Color(43, 43, 43));
                    UIManager.put("OptionPane.messageForeground", Color.WHITE);
                    UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.PLAIN, 13));

                    int opcion = JOptionPane.showConfirmDialog(this,
                            "¿Deseas cerrar sesión y volver al login?",
                            "Cerrar sesión",
                            JOptionPane.YES_NO_OPTION);

                    if (opcion == JOptionPane.YES_OPTION) {
                        dispose();
                        LoginVista loginVista = new LoginVista();
                        new LoginControlador(loginVista);
                    }
                });
            }

            panelLateral.add(btn);
        }

        add(panelLateral, BorderLayout.WEST);

        // ---------------------- PANEL CENTRAL CON CARDLAYOUT ----------------------
        // Panel que va cambiando entre las vistas: Notas, Contraseñas, Admin...
        cardLayout = new CardLayout();
        panelCartas = new JPanel(cardLayout);
        panelCartas.setBackground(colorFondoOscuro);

        panelContenido = new PanelContenido();
        panelCartas.add(panelContenido, "Contenido");

        add(panelCartas, BorderLayout.CENTER);

        // ---------------------- PANEL INFERIOR DE NAVEGACIÓN ----------------------
        // Botones de acción rápida: ver notas, ver contraseñas y crear nueva nota
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

        // Mostrar lista de notas
        btnVerNotas.addActionListener(e -> {
            lblTituloCabecera.setText("📝 Mis Notas y Más");
            panelContenido.mostrarNotas(this);
        });

        // Mostrar gestor de contraseñas (requiere autenticación)
        btnVerContras.addActionListener(e -> {
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
                if (UsuarioDAO.validarUsuario(user, pass)) {
                    lblTituloCabecera.setText("🔐 Gestor de Contraseñas");
                    panelContenido.mostrarContras();
                } else {
                    JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Abrir diálogo para crear nueva nota
        botonMas.addActionListener(e -> {
            NotasControlador.crearYEditarNota(this);
        });

        setVisible(true);
    }

    // ---------------------- MÉTODOS AUXILIARES ----------------------

    public void refrescarNotas() {
        lblTituloCabecera.setText("📝 Mis Notas y Más");
        panelContenido.mostrarNotas(this);
    }

    public void mostrarAdmin() {
        lblTituloCabecera.setText("⚙️ Panel de Administración");
        panelContenido.mostrarAdmin();
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }
}
