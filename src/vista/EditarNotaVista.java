package vista;

import bbdd.GestorBBDD;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
        dialogo.add(campoTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        campoHashtags = new JTextPane();
        JScrollPane scrollHashtags = new JScrollPane(campoHashtags);
        scrollHashtags.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        panelCentro.add(scrollHashtags);

        campoContenido = new JTextPane();
        panelCentro.add(new JScrollPane(campoContenido));

        dialogo.add(panelCentro, BorderLayout.CENTER);

        panelBotones = new JPanel(new FlowLayout());
        btnGuardar = new JButton("Guardar");
        panelBotones.add(btnGuardar);
        dialogo.add(panelBotones, BorderLayout.SOUTH);

        // Acción provisional para que el botón guardar simplemente cierre la ventana
        //btnGuardar.addActionListener(e -> dialogo.dispose());

        // 🎨 ESTILOS VISUALES AÑADIDOS PARA UNIFICAR CON LA APP
        Color fondo = new Color(43, 43, 43);
        Color campos = new Color(30, 30, 30);
        Color textoClaro = new Color(220, 220, 220);
        Color bordeClaro = new Color(100, 100, 100);

        dialogo.getContentPane().setBackground(fondo);
        panelCentro.setBackground(fondo);
        panelBotones.setBackground(fondo);

        // Bordes personalizados
        campoTitulo.setBackground(campos);
        campoTitulo.setForeground(textoClaro);
        TitledBorder bordeTitulo = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(bordeClaro), "Título");
        bordeTitulo.setTitleColor(textoClaro);
        campoTitulo.setBorder(bordeTitulo);

        campoHashtags.setBackground(campos);
        campoHashtags.setForeground(textoClaro);
        TitledBorder bordeHashtags = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(bordeClaro), "Hashtags");
        bordeHashtags.setTitleColor(textoClaro);
        campoHashtags.setBorder(bordeHashtags);

        campoContenido.setBackground(campos);
        campoContenido.setForeground(textoClaro);
        TitledBorder bordeContenido = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(bordeClaro), "Contenido");
        bordeContenido.setTitleColor(textoClaro);
        campoContenido.setBorder(bordeContenido);

        // Estilo de botón
        btnGuardar.setBackground(new Color(60, 63, 65));
        btnGuardar.setForeground(textoClaro);
        btnGuardar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(true);
    }

    public void mostrar() {
        dialogo.setVisible(true);
    }

    // Método opcional para guardar desde la propia vista (no se usa por defecto)
    // Puedes eliminarlo si prefieres gestionar todo desde el controlador
    /* private void guardarNota() {
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
    } */


    public JTextField getCampoTitulo() {
        return campoTitulo;
    }

    public JTextPane getCampoHashtags() {
        return campoHashtags;
    }

    public JTextPane getCampoContenido() {
        return campoContenido;
    }

    public JPanel getPanelBotones() {
        return panelBotones;
    }

    public JDialog getDialogo() {
        return dialogo;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }
}
