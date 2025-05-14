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

        // Crear y configurar el panel de botones
        panelBotones = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar");
        panelBotones.add(btnGuardar);
        dialogo.add(panelBotones, BorderLayout.SOUTH);

        /*btnGuardar.addActionListener(e -> guardarNota());*/
        btnGuardar.addActionListener(e -> dialogo.dispose());

        // 🎨 ESTILOS VISUALES AÑADIDOS PARA UNIFICAR CON LA APP
        Color fondo = new Color(43, 43, 43);
        Color campos = new Color(30, 30, 30);
        Color textoClaro = new Color(220, 220, 220);
        Color bordeClaro = new Color(100, 100, 100);

        dialogo.getContentPane().setBackground(fondo);
        panelCentro.setBackground(fondo);
        panelBotones.setBackground(fondo);

// Borde personalizado y fondo/forma
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

// Estilo de botones unificado
        for (Component c : panelBotones.getComponents()) {
            if (c instanceof JButton) {
                JButton btn = (JButton) c;
                btn.setBackground(new Color(60, 63, 65));
                btn.setForeground(textoClaro);
                btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
                btn.setFocusPainted(false);
                btn.setBorderPainted(false);
                btn.setContentAreaFilled(true);
            }
        }

    }

    public void mostrar() {
        dialogo.setVisible(true);
    }

    // HAY QUE TRABAJAR EL GUARDAR LA NOTA EN LA BBDD⬇️⬇️
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

    // Método para acceder al panel de botones
    public JPanel getPanelBotones() {
        return panelBotones;
    }
}
