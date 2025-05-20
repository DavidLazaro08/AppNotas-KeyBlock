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

        // ---------------------- EVENTO: Registrar usuario en el mismo cuadro ----------------------

        btnRegistrar.addActionListener(e -> {
            String nuevoUsuario = tfUsuario.getText().trim();
            String nuevaContrasena = new String(pfContrasena.getPassword());

            if (nuevoUsuario.isEmpty() || nuevaContrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, rellena todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            modelo.UsuarioDAO dao = new modelo.UsuarioDAO();
            boolean registrado = dao.registrarUsuario(nuevoUsuario, nuevaContrasena);

            // Aplicar estilo oscuro al JOptionPane
            UIManager.put("OptionPane.background", new Color(43, 43, 43));
            UIManager.put("Panel.background", new Color(43, 43, 43));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.PLAIN, 13));

            if (registrado) {
                JOptionPane.showMessageDialog(this, "Registro exitoso. Ahora puedes iniciar sesión.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar. El usuario ya existe o fallo de conexión.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

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
