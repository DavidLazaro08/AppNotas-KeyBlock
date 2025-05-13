package vista;

import bbdd.GestorBBDD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Clase PrincipalVista que representa la ventana principal del programa.
 * Basado en la teoría del módulo 1.3 (BorderLayout), 1.4 (Eventos) y ejemplos
 * como PanelVerde/PanelRojo y VentanaSecundaria. */

public class PrincipalVista extends JFrame {

    private JButton btnNuevaNota;
    private JTextArea areaNotas;

    public PrincipalVista() {
        // Configuración inicial del JFrame (teoría 1.1)
        setTitle("NotasApp");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana
        setLayout(new BorderLayout()); // Layout principal (apuntes 1.3)

        // Crear panel superior con un título
        JLabel titulo = new JLabel("Mis Notas");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        // Crear panel central con JTextArea dentro de JScrollPane
        areaNotas = new JTextArea("Aquí se mostrarán tus notas...");
        areaNotas.setEditable(false);
        areaNotas.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(areaNotas), BorderLayout.CENTER);  // teoría: JScrollPane = contenedor intermedio (1.2)

        // Crear panel inferior (FlowLayout) con botón
        JPanel panelInferior = new JPanel(new FlowLayout());
        btnNuevaNota = new JButton("Crear nueva nota");
        panelInferior.add(btnNuevaNota);
        add(panelInferior, BorderLayout.SOUTH);

        // Escuchador de eventos para el botón (1.4 EVENTOS)
        btnNuevaNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear ventana secundaria (modal) para nueva nota
                JDialog dialogo = new JDialog(PrincipalVista.this, "Nueva Nota", true);
                dialogo.setSize(400, 300);
                dialogo.setLocationRelativeTo(null);
                dialogo.setLayout(new BorderLayout());

                // Panel para el título
                JTextField campoTitulo = new JTextField();
                campoTitulo.setBorder(BorderFactory.createTitledBorder("Título"));
                dialogo.add(campoTitulo, BorderLayout.NORTH);

                // Panel para el contenido (como <textarea>)
                JTextPane campoContenido = new JTextPane();
                campoContenido.setBorder(BorderFactory.createTitledBorder("Contenido"));
                dialogo.add(new JScrollPane(campoContenido), BorderLayout.CENTER);

                // Panel inferior con botón de guardar
                JPanel panelBotones = new JPanel(new FlowLayout());
                JButton btnGuardar = new JButton("Guardar");
                panelBotones.add(btnGuardar);
                dialogo.add(panelBotones, BorderLayout.SOUTH);

                // Acción del botón guardar
                btnGuardar.addActionListener(ev -> {
                    String titulo = campoTitulo.getText();
                    String contenido = campoContenido.getText();

                    if (!titulo.isEmpty() && !contenido.isEmpty()) {
                        try {
                            // Guarda en la base de datos
                            GestorBBDD.executeUpdate("INSERT INTO notas(titulo, contenido) VALUES('" + titulo + "', '" + contenido + "')");
                            JOptionPane.showMessageDialog(dialogo, "Nota guardada.");
                            dialogo.dispose();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(dialogo, "Error al guardar la nota.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(dialogo, "Por favor completa todos los campos.");
                    }
                });

                dialogo.setVisible(true);
            }
        });


        setVisible(true);
    }
}