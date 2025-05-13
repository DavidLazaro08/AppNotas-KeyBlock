package vista;

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
                // Simula abrir una ventana secundaria modal (como ejemplo visto en VentanaSecundaria.java)
                JDialog dialogo = new JDialog(PrincipalVista.this, "Crear Nota", true);
                dialogo.setSize(300, 200);
                dialogo.setLocationRelativeTo(null);
                dialogo.setLayout(new BorderLayout());

                JLabel etiqueta = new JLabel("Aquí se crearán las notas");
                etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
                dialogo.add(etiqueta, BorderLayout.CENTER);

                JButton btnCerrar = new JButton("Cerrar");
                btnCerrar.addActionListener(ev -> dialogo.dispose());
                dialogo.add(btnCerrar, BorderLayout.SOUTH);

                dialogo.setVisible(true);
            }
        });

        setVisible(true);
    }
}