package vista;

import javax.swing.*;
import java.awt.*;

public class EditarNotaVista {
    private final JDialog dialogo;
    private final JTextField campoTitulo;
    private final JTextPane campoHashtags;
    private final JTextPane campoContenido;
    private final JPanel panelBotones;
    private final JButton btnGuardar;

    public EditarNotaVista(JFrame padre) {
        dialogo = new JDialog(padre, "Nueva Nota", true);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(null);
        dialogo.setLayout(new BorderLayout());

        campoTitulo = new JTextField();
        campoTitulo.setBorder(BorderFactory.createTitledBorder("Título"));
        dialogo.add(campoTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        campoHashtags = new JTextPane();
        campoHashtags.setBorder(BorderFactory.createTitledBorder("Hashtags"));
        JScrollPane scrollHashtags = new JScrollPane(campoHashtags);
        scrollHashtags.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        panelCentro.add(scrollHashtags);

        campoContenido = new JTextPane();
        campoContenido.setBorder(BorderFactory.createTitledBorder("Contenido"));
        panelCentro.add(new JScrollPane(campoContenido));

        dialogo.add(panelCentro, BorderLayout.CENTER);

        panelBotones = new JPanel(new FlowLayout());
        btnGuardar = new JButton("Guardar");
        panelBotones.add(btnGuardar);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
    }

    public void mostrar() {
        dialogo.setVisible(true);
    }

    public JDialog getDialogo() { return dialogo; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JTextField getCampoTitulo() { return campoTitulo; }
    public JTextPane getCampoHashtags() { return campoHashtags; }
    public JTextPane getCampoContenido() { return campoContenido; }
    public JPanel getPanelBotones() { return panelBotones; }
}
