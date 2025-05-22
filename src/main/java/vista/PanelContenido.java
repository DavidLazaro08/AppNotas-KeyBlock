package vista;

import controlador.NotasControlador;
import modelo.Hashtag;
import modelo.Nota;
import bbdd.GestorBBDD;
import controlador.NotaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.List;

import static controlador.NotasControlador.crearYEditarNota;

/* Clase PanelContenido que centraliza las tres secciones visuales principales de la app:
 * Notas, Contraseñas y Panel de Administración.
 *
 * ➤ Usa CardLayout (Módulo 1.6) para cambiar de panel sin recargar ventanas.
 * ➤ Permite alternar vistas desde PrincipalVista sin tener que usar múltiples ventanas. */

public class PanelContenido extends JPanel {

    // ---------------------- ATRIBUTOS ----------------------

    private CardLayout cardLayout;
    private JPanel panelNotas;
    private JPanel panelContras;
    private JPanel panelAdmin;
    private JTable tablaAdmin;
    private List<Nota> listaNotas;

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

    public void mostrarNotas(PrincipalVista principalVista) {
        panelNotas.removeAll();
        refrescarNotas(principalVista);
        cardLayout.show(this, "Notas");
    }

    public void mostrarContras() {
        cardLayout.show(this, "Contras");
    }

    public void mostrarAdmin() {
        cargarNotasAdmin();
        cardLayout.show(this, "Admin");
    }

    // ---------------------- PANEL NOTAS ----------------------

    private JPanel crearPanelNotas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(43, 43, 43));
        return panel;
    }

    private void refrescarNotas(PrincipalVista principalVista) {
        panelNotas.removeAll();
        panelNotas.setLayout(new BorderLayout());

        listaNotas = GestorBBDD.obtenerTodasLasNotas();

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

            //aquí se están creando las tarjetas con las que existen en la bbdd
            for (int i = 0; i < listaNotas.size(); i++) {
                Nota nota = listaNotas.get(i);
                boolean alternar = i % 2 == 0;
                JPanel tarjeta = crearTarjetaNota(nota, alternar);
                //vinculo el id de la nota al cuadro de la nota
                tarjeta.putClientProperty("notaId",nota.getId());

                //a cada tarjeta le añado un listener para generar abrir el editor
                tarjeta.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int idNota = (int) tarjeta.getClientProperty("notaId");
                        contenedor.remove(tarjeta);
                        NotasControlador.EditarNota(principalVista, nota.getTitulo(),nota.getContenido());
                    }
                });

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
        panel.setBackground(new Color(36, 36, 36));

        JLabel mensaje = new JLabel("Aquí irán tus contraseñas 🔐", SwingConstants.CENTER);
        mensaje.setFont(new Font("Consolas", Font.PLAIN, 20));
        mensaje.setForeground(new Color(187, 187, 187));
        mensaje.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));

        panel.add(mensaje, BorderLayout.CENTER);
        return panel;
    }

    // ---------------------- PANEL ADMINISTRADOR ----------------------

    private JPanel crearPanelAdmin() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(40, 40, 40));

        tablaAdmin = new JTable();
        tablaAdmin.setBackground(new Color(30, 30, 30));
        tablaAdmin.setForeground(Color.WHITE);
        tablaAdmin.setGridColor(new Color(60, 60, 60));
        tablaAdmin.setSelectionBackground(new Color(70, 70, 70));
        tablaAdmin.setSelectionForeground(Color.WHITE);
        tablaAdmin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaAdmin.setRowHeight(28);
        tablaAdmin.getTableHeader().setBackground(new Color(25, 25, 25));
        tablaAdmin.getTableHeader().setForeground(Color.LIGHT_GRAY);
        tablaAdmin.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBackground(new Color(60, 63, 65));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnRefrescar.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        btnRefrescar.addActionListener(e -> cargarNotasAdmin());

        panel.add(new JScrollPane(tablaAdmin), BorderLayout.CENTER);
        panel.add(btnRefrescar, BorderLayout.SOUTH);

        return panel;
    }

    private void cargarNotasAdmin() {
        try {
            Connection conn = GestorBBDD.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM notas");

            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();
            String[] nombres = new String[columnas];
            for (int i = 0; i < columnas; i++) {
                nombres[i] = meta.getColumnName(i + 1);
            }

            DefaultTableModel modelo = new DefaultTableModel(nombres, 0);

            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

            tablaAdmin.setModel(modelo);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
    }



}
