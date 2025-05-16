package vista;

import bbdd.GestorBBDD;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

        // Acción: guardar la nota al pulsar
        btnGuardar.addActionListener(e -> guardarNota());

        // 🎨 ESTILOS VISUALES AÑADIDOS PARA UNIFICAR CON LA APP
        Color fondo = new Color(43, 43, 43);
        Color campos = new Color(30, 30, 30);
        Color textoClaro = new Color(220, 220, 220);
        Color bordeClaro = new Color(100, 100, 100);

        dialogo.getContentPane().setBackground(fondo);
        panelCentro.setBackground(fondo);
        panelBotones.setBackground(fondo);

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

    // ✅ NUEVO MÉTODO AÑADIDO
    private void guardarNota() {
        String titulo = campoTitulo.getText();
        String contenido = campoContenido.getText();
        String textoHashtags = campoHashtags.getText();

        if (!titulo.isEmpty() && !contenido.isEmpty()) {
            try {
                Connection connection = GestorBBDD.getConnection();

                // Insertar nota
                String sqlNota = "INSERT INTO notas(titulo, contenido) VALUES (?, ?)";
                PreparedStatement psNota = connection.prepareStatement(sqlNota, PreparedStatement.RETURN_GENERATED_KEYS);
                psNota.setString(1, titulo);
                psNota.setString(2, contenido);
                psNota.executeUpdate();

                ResultSet rsNota = psNota.getGeneratedKeys();
                int notaId = -1;
                if (rsNota.next()) {
                    notaId = rsNota.getInt(1);
                }
                rsNota.close();
                psNota.close();

                // Procesar hashtags
                if (textoHashtags != null && !textoHashtags.trim().isEmpty()) {
                    String[] tagsSeparados = textoHashtags.split(",");
                    for (int i = 0; i < tagsSeparados.length; i++) {
                        String texto = tagsSeparados[i].trim().toLowerCase();
                        if (texto.startsWith("#")) {
                            texto = texto.substring(1);
                        }


                        // Insertar hashtag si no existe
                        String insertarHashtag = "INSERT IGNORE INTO hashtags(nombre) VALUES (?)";
                        PreparedStatement ps1 = connection.prepareStatement(insertarHashtag);
                        ps1.setString(1, texto);
                        ps1.executeUpdate();
                        ps1.close();

                        // Obtener ID del hashtag
                        String buscarHashtag = "SELECT id FROM hashtags WHERE nombre = ?";
                        PreparedStatement ps2 = connection.prepareStatement(buscarHashtag);
                        ps2.setString(1, texto);
                        ResultSet rs = ps2.executeQuery();

                        int hashtagId = -1;
                        if (rs.next()) {
                            hashtagId = rs.getInt("id");
                        }
                        rs.close();
                        ps2.close();

                        // Insertar en tabla intermedia
                        if (hashtagId != -1) {
                            String insertarRelacion = "INSERT IGNORE INTO nota_hashtag(nota_id, hashtag_id) VALUES (?, ?)";
                            PreparedStatement ps3 = connection.prepareStatement(insertarRelacion);
                            ps3.setInt(1, notaId);
                            ps3.setInt(2, hashtagId);
                            ps3.executeUpdate();
                            ps3.close();
                        }
                    }
                }

                JOptionPane.showMessageDialog(dialogo, "Nota guardada correctamente.");
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
