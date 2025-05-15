package vista;

import modelo.Hashtag;
import modelo.Nota;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * PanelNotas con estilo oscuro, inspirado en editores tipo IntelliJ o Eclipse.
 * Fondo gris oscuro, texto claro, y diseño minimalista para enfoque de desarrollo.
 */
public class PanelNotas extends JPanel {

    public PanelNotas() {
        setLayout(new BorderLayout());
        setBackground(new Color(43, 43, 43)); // Fondo oscuro

        // Este será el contenedor de notas reales o temporales
        JPanel contenedor = new NotasTemporal(); // <-- más adelante se cambiará por uno real

        if (contenedor.getComponentCount() == 0) {
            // Si no hay notas, mostrar un mensaje centrado
            JLabel mensaje = new JLabel("Aquí se mostrarán tus notas 🗒️", SwingConstants.CENTER);
            mensaje.setFont(new Font("Consolas", Font.PLAIN, 20));
            mensaje.setForeground(new Color(187, 187, 187));
            mensaje.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
            add(mensaje, BorderLayout.CENTER);
        } else {
            JScrollPane scroll = new JScrollPane(contenedor);
            scroll.setBorder(null);
            scroll.getVerticalScrollBar().setUnitIncrement(16); // Desplazamiento más suave
            add(scroll, BorderLayout.CENTER);
        }
    }

    /*
    // 🔜 MÉTODO PARA MOSTRAR NOTAS REALES (se activará cuando llegue List<Nota>)

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

    // 🔧 MÉTODO DE ESTILO PARA MOSTRAR UNA NOTA COMO TARJETA

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
            JLabel lbl = new JLabel(tag.toString());
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

        tarjeta.add(panelSuperior, BorderLayout.CENTER);
        tarjeta.add(lblFecha, BorderLayout.EAST);

        return tarjeta;
    }
    */
}
