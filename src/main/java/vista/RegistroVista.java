package vista;

import javax.swing.*;
import java.awt.*;

public class RegistroVista extends JDialog {
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JButton botonRegistrar;

    public RegistroVista(JFrame parent) {
        super(parent, "Registro de Usuario", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 1));

        campoUsuario = new JTextField();
        campoContrasena = new JPasswordField();
        botonRegistrar = new JButton("Registrarse");

        add(new JLabel("Nombre de usuario:"));
        add(campoUsuario);
        add(new JLabel("Contraseña:"));
        add(campoContrasena);
        add(botonRegistrar);
    }

    public String getUsuario() {
        return campoUsuario.getText();
    }

    public String getContrasena() {
        return new String(campoContrasena.getPassword());
    }

    public JButton getBotonRegistrar() {
        return botonRegistrar;
    }
}
