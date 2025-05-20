package vista;

import modelo.Hashtag;
import modelo.Nota;
import bbdd.GestorBBDD;
import modelo.NotaDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/** Clase PanelContenido que centraliza las tres secciones visuales principales de la app:
 * Notas, Contraseñas y Panel de Administración.
 *
 * ➤ Usa CardLayout (Módulo 1.6) para cambiar de panel sin recargar ventanas.
 * ➤ Sigue el principio de Responsabilidad Única (cada "sección" tiene su propio método de creación).
 * ➤ Permite simplificar la gestión visual desde PrincipalVista o cualquier controlador. */

public class PanelContenido extends JPanel {

    // ---------------------- ATRIBUTOS ----------------------
    private CardLayout cardLayout;
    private JPanel panelNotas;
    private JPanel panelContras;
    private JPanel panelAdmin;

    // ---------------------- CONSTRUCTOR ----------------------
    public PanelContenido() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setBackground(new Color(25, 25, 25));

        panelNotas = crearPanelNotas();
        panelContras = crearPanelContras();
        panelAdmin = crearPanelAdmin();

        add(panelNotas, "Notas");
        add(panelContras, "Contras");
        add(panelAdmin, "Admin");
    }

    // ---------------------- MÉTODOS DE CAMBIO DE VISTA ----------------------

    public void mostrarNotas() {
        refrescarNotas();
        cardLayout.show(this, "Notas");
    }

    public void mostrarContras() {
        cardLayout.show(this, "Contras");
    }

    public void mostrarAdmin() {
        cardLayout.show(this, "Admin");
    }

    // ---------------------- PANEL NOTAS (restaurado con diseño original) ----------------------

    private JPanel crearPanelNotas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(43, 43, 43));
        return panel;
    }

    private void refrescarNotas() {
        panelNotas.removeAll();
        panelNotas.setLayout(new BorderLayout());

        List<Nota> listaNotas = GestorBBDD.obtenerTodasLasNotas();

        if (listaNotas == null || listaNotas.isEmpty()) {
            JLabel mensaje = new JLabel("Aquí se mostrarán tus notas 🗒️", SwingConstants.CENTER);
            mensaje.setFont(new Font("Consolas", Font.PLAIN, 20));
            mensaje.setForeground(new Color(187, 187, 187));
            mensaje.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
            panelNotas.add(mensaje, BorderLayout.CENTER);
        } else {
            JPanel contenedor = new JPanel();
            contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
            contenedor.setBackground(new Color(43, 43, 43));

            for (int i = 0; i < listaNotas.size(); i++) {
                Nota nota = listaNotas.get(i);
                boolean alternar = i % 2 == 0;
                JPanel tarjeta = crearTarjetaNota(nota, alternar);
                contenedor.add(tarjeta);
                contenedor.add(Box.createVerticalStrut(8));
            }

            JScrollPane scroll = new JScrollPane(contenedor);
            scroll.setBorder(null);
            scroll.getVerticalScrollBar().setUnitIncrement(16);
            panelNotas.add(scroll, BorderLayout.CENTER);
        }

        panelNotas.revalidate();
        panelNotas.repaint();
    }

    private JPanel crearTarjetaNota(Nota nota, boolean alternarColor) {
        Color fondo = alternarColor ? new Color(43, 43, 43) : new Color(36, 36, 36);

        JPanel tarjeta = new JPanel(new BorderLayout(10, 10));
        tarjeta.setBackground(fondo);
        tarjeta.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 16));
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(nota.getTitulo());
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(220, 220, 220));

        JPanel panelTags = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelTags.setBackground(fondo);
        panelTags.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        for (Hashtag tag : nota.getHashtags()) {
            JLabel lbl = new JLabel("#" + tag.getTexto());
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
            lbl.setForeground(Color.LIGHT_GRAY);
            lbl.setOpaque(true);
            lbl.setBackground(new Color(30, 30, 30));
            lbl.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
            panelTags.add(lbl);
        }

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(fondo);
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelTags, BorderLayout.CENTER);

        JLabel lblFecha = new JLabel("📅 " + nota.getFecha());
        lblFecha.setFont(new Font("Monospaced", Font.PLAIN, 11));
        lblFecha.setForeground(Color.GRAY);
        lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);

        JButton btnEliminar = new JButton("🗑️");
        btnEliminar.setToolTipText("Eliminar nota");
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setBackground(new Color(60, 63, 65));
        btnEliminar.setForeground(Color.LIGHT_GRAY);
        btnEliminar.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnEliminar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnEliminar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar esta nota?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                NotaDAO.eliminarNotaPorId(nota.getId());
                refrescarNotas();
            }
        });

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(fondo);
        panelDerecho.add(lblFecha, BorderLayout.NORTH);
        panelDerecho.add(btnEliminar, BorderLayout.SOUTH);

        tarjeta.add(panelSuperior, BorderLayout.CENTER);
        tarjeta.add(panelDerecho, BorderLayout.EAST);

        return tarjeta;
    }

    // ---------------------- PANEL CONTRASEÑAS ----------------------

    private JPanel crearPanelContras() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(36, 36, 36)); // Más oscuro que PanelNotas

        JLabel mensaje = new JLabel("Aquí irán tus contraseñas 🔐", SwingConstants.CENTER);
        mensaje.setFont(new Font("Consolas", Font.PLAIN, 20));
        mensaje.setForeground(new Color(187, 187, 187));
        mensaje.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));

        panel.add(mensaje, BorderLayout.CENTER);
        return panel;
    }

    // ---------------------- PANEL ADMIN (placeholder actual) ----------------------

    private JPanel crearPanelAdmin() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 40));
        panel.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("⚙️ Panel de Administración", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        panel.add(lblTitulo, BorderLayout.NORTH);

        JTextArea areaAdmin = new JTextArea("Herramientas de administración aquí...");
        areaAdmin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        areaAdmin.setForeground(Color.WHITE);
        areaAdmin.setBackground(new Color(30, 30, 30));
        areaAdmin.setLineWrap(true);
        areaAdmin.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(areaAdmin);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }
}