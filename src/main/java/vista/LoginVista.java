package vista;

import javax.swing.*;
import java.awt.*;

public class LoginVista extends JFrame {

    private JTextField tfUsuario;
    private JPasswordField pfContrasena;
    private JButton btnLogin;
    private JButton btnRegistrar;

    public LoginVista() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 180);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        panel.add(new JLabel("Usuario:"));
        tfUsuario = new JTextField();
        panel.add(tfUsuario);

        panel.add(new JLabel("Contraseña:"));
        pfContrasena = new JPasswordField();
        panel.add(pfContrasena);

        btnLogin = new JButton("Iniciar sesión");
        btnRegistrar = new JButton("Registrarse");

        panel.add(btnLogin);
        panel.add(btnRegistrar);

        add(panel);

        setVisible(true);
    }

    // Getters para controlador
    public String getUsuario() {
        return tfUsuario.getText().trim();
    }

    public String getContrasena() {
        return new String(pfContrasena.getPassword());
    }

    public JButton getLoginButton() {
        return btnLogin;
    }

    public JButton getRegistrarButton() {
        return btnRegistrar;
    }

    // Limpia campos de texto
    public void limpiarCampos() {
        tfUsuario.setText("");
        pfContrasena.setText("");
    }
}
