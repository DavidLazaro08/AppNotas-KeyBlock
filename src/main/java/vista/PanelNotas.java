package vista;

import modelo.Hashtag;
import modelo.Nota;
import bbdd.GestorBBDD;
import modelo.NotaDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * PanelNotas con estilo oscuro y scroll.
 * Estética tipo entorno de desarrollo, con tarjetas alternas y hashtags visibles.
 */
public class PanelNotas extends JPanel {

    public PanelNotas() {
        setLayout(new BorderLayout());
        setBackground(new Color(43, 43, 43)); // Fondo oscuro general

        List<Nota> listaNotas = GestorBBDD.obtenerTodasLasNotas();
        mostrarNotas(listaNotas);
    }

    public void mostrarNotas(List<Nota> listaNotas) {
        removeAll();
        setLayout(new BorderLayout());

        if (listaNotas == null || listaNotas.isEmpty()) {
            JLabel mensaje = new JLabel("Aquí se mostrarán tus notas 🗒️", SwingConstants.CENTER);
            mensaje.setFont(new Font("Consolas", Font.PLAIN, 20));
            mensaje.setForeground(new Color(187, 187, 187));
            mensaje.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
            add(mensaje, BorderLayout.CENTER);
        } else {
            JPanel contenedor = new JPanel();
            contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
            contenedor.setBackground(new Color(43, 43, 43));

            for (int i = 0; i < listaNotas.size(); i++) {
                Nota nota = listaNotas.get(i);
                boolean alternar = i % 2 == 0;
                JPanel tarjeta = crearTarjetaNota(nota, alternar);
                contenedor.add(tarjeta);
                contenedor.add(Box.createVerticalStrut(8)); // Separación entre tarjetas
            }

            JScrollPane scroll = new JScrollPane(contenedor);
            scroll.setBorder(null);
            scroll.getVerticalScrollBar().setUnitIncrement(16);
            add(scroll, BorderLayout.CENTER);
        }

        revalidate();
        repaint();
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

        // 🟦 Etiquetas (hashtags)
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

        // 🟦 Panel izquierdo con título y hashtags
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(fondo);
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelTags, BorderLayout.CENTER);

        // 📅 Fecha
        JLabel lblFecha = new JLabel("📅 " + nota.getFecha());
        lblFecha.setFont(new Font("Monospaced", Font.PLAIN, 11));
        lblFecha.setForeground(Color.GRAY);
        lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);

        // 🗑️ Botón de eliminar
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
                NotaDAO.eliminarNotaPorId(nota.getId()); // método que ahora añadiremos
                refrescar(); // recarga la vista (lo añadimos también si no lo tienes aún)
            }
        });

        // Panel derecho (fecha y botón)
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(fondo);
        panelDerecho.add(lblFecha, BorderLayout.NORTH);
        panelDerecho.add(btnEliminar, BorderLayout.SOUTH);

        tarjeta.add(panelSuperior, BorderLayout.CENTER);
        tarjeta.add(panelDerecho, BorderLayout.EAST);

        return tarjeta;
    }

    /**
     * 🔄 Método público para refrescar el listado de notas.
     * Elimina todo lo que haya y vuelve a cargar las notas desde la base de datos.
     */
    public void refrescar() {
        removeAll(); // Elimina los componentes actuales
        setLayout(new BorderLayout());

        List<Nota> listaNotas = GestorBBDD.obtenerTodasLasNotas();

        if (listaNotas == null || listaNotas.isEmpty()) {
            JLabel mensaje = new JLabel("Aquí se mostrarán tus notas 🗒️", SwingConstants.CENTER);
            mensaje.setFont(new Font("Consolas", Font.PLAIN, 20));
            mensaje.setForeground(new Color(187, 187, 187));
            mensaje.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
            add(mensaje, BorderLayout.CENTER);
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
            add(scroll, BorderLayout.CENTER);
        }

        revalidate();
        repaint();
    }


}
