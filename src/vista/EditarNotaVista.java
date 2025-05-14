package vista;

import bbdd.GestorBBDD;

import javax.swing.*;
import java.awt.*;

public class EditarNotaVista {

    private final JDialog dialogo;
    private final JTextField campoTitulo;
    private final JTextPane campoHashtags;
    private final JTextPane campoContenido;

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

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar");
        panelBotones.add(btnGuardar);
        dialogo.add(panelBotones, BorderLayout.SOUTH);

        /*btnGuardar.addActionListener(e -> guardarNota());*/
        btnGuardar.addActionListener(e-> dialogo.dispose());
    }

    public void mostrar() {
        dialogo.setVisible(true);
    }

    //HAY QUE TRABAJAR EL GUARDAR LA NOTA EN LA BBDD⬇️⬇️
    private void guardarNota() {
        String titulo = campoTitulo.getText();
        String hashtags = campoHashtags.getText();
        String contenido = campoContenido.getText();

        if (!titulo.isEmpty() && !contenido.isEmpty()) {
            try {
                GestorBBDD.executeUpdate("INSERT INTO notas(titulo, hashtags, contenido) VALUES('" +
                        titulo + "', '" + hashtags + "', '" + contenido + "')");
                JOptionPane.showMessageDialog(dialogo, "Nota guardada.");
                dialogo.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialogo, "Error al guardar la nota.");
            }
        } else {
            JOptionPane.showMessageDialog(dialogo, "Por favor completa todos los campos.");
        }
    }

    public JTextField getCampoTitulo() {
        return campoTitulo;
    }

    public JTextPane getCampoHashtags() {
        return campoHashtags;
    }

    public JTextPane getCampoContenido() {
        return campoContenido;
    }
}
