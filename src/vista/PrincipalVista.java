package vista;

import javax.swing.*;
import java.awt.*;

public class PrincipalVista extends JFrame {

    private JButton btnNuevaNota;
    private JTextArea areaNotas;

    public PrincipalVista() {
        setTitle("NotasApp");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Mis Notas");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        areaNotas = new JTextArea("Aquí se mostrarán tus notas...");
        areaNotas.setEditable(false);
        areaNotas.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(areaNotas), BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout());
        btnNuevaNota = new JButton("Crear nueva nota");
        panelInferior.add(btnNuevaNota);
        add(panelInferior, BorderLayout.SOUTH);

        btnNuevaNota.addActionListener(e -> {
            EditarNotaVista vistaEditar = new EditarNotaVista(this);
            vistaEditar.mostrar();
        });

        setVisible(true);
    }
}
