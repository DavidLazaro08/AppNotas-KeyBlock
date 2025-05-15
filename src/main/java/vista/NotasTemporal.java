package vista;

import modelo.Hashtag;
import modelo.Nota;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotasTemporal extends JPanel {

    public NotasTemporal() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(43, 43, 43));

        // CREAR NOTAS FICTICIAS
        for (int i = 1; i <= 3; i++) {
            Nota notaDemo = new Nota(i, "Título " + i, "Contenido de ejemplo", LocalDate.now(), 1);
            notaDemo.setHashtags(new ArrayList<>(List.of(
                    new Hashtag("demo" + i),
                    new Hashtag("etiqueta")
            )));

            boolean alternar = i % 2 == 0;
            JPanel tarjeta = crearTarjetaNota(notaDemo, alternar);
            add(tarjeta);
            add(Box.createVerticalStrut(8));
        }

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




}
