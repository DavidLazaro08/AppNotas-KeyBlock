package vista;

import javax.swing.*;
import java.awt.*;

/** Clase LoginVista que representa la pantalla de inicio de sesión.
 *  Usa un GridLayout para organizar los campos y botones de forma sencilla.
 *
 * ➤ Basado en ejemplos de formularios vistos en clase (Módulo 1.4)
 * ➤ Hereda de VentanaBase para mantener estilo visual unificado e icono común */

public class LoginVista extends VentanaBase {

    // ---------------------- ATRIBUTOS ----------------------

    private JTextField tfUsuario;
    private JPasswordField pfContrasena;
    private JButton btnLogin;
    private JButton btnRegistrar;

    // ---------------------- CONSTRUCTOR ----------------------

    public LoginVista() {
        super("Login");
        setSize(300, 180);
        setLocationRelativeTo(null);

        // ---------------------- PANEL PRINCIPAL (Formulario) ----------------------

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(colorFondoOscuro); // Fondo oscuro como en el resto de la app

        // Campo Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(colorTextoClaro); // ✅ Texto claro para contraste
        panel.add(lblUsuario);

        tfUsuario = new JTextField();
        panel.add(tfUsuario);

        // Campo Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(colorTextoClaro); // ✅ Texto claro para contraste
        panel.add(lblContrasena);

        pfContrasena = new JPasswordField();
        panel.add(pfContrasena);

        // Botones Login y Registro
        btnLogin = crearBotonEstiloIDE("Iniciar sesión", fuenteNormal);
        btnRegistrar = crearBotonEstiloIDE("Registrarse", fuenteNormal);

        panel.add(btnLogin);
        panel.add(btnRegistrar);

        // Añadir panel al contenido
        add(panel);

        setVisible(true);
    }

    // ---------------------- MÉTODOS GETTERS ----------------------

    // Devuelve el texto del campo usuario
    public String getUsuario() {
        return tfUsuario.getText().trim();
    }

    // Devuelve la contraseña como texto
    public String getContrasena() {
        return new String(pfContrasena.getPassword());
    }

    public JButton getLoginButton() {
        return btnLogin;
    }

    public JButton getRegistrarButton() {
        return btnRegistrar;
    }

    // ---------------------- MÉTODOS EXTRA ----------------------

    // Limpia campos de texto
    public void limpiarCampos() {
        tfUsuario.setText("");
        pfContrasena.setText("");
    }
}
