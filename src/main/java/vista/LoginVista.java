package vista;

import javax.swing.*;
import java.awt.*;

/* Clase LoginVista que representa la pantalla de inicio de sesión.
 *
 * ➤ Usa un GridLayout para organizar los campos de usuario y contraseña.
 * ➤ Integra el botón de login y registro en una sola vista.
 * ➤ Aplica estilo visual oscuro heredado desde VentanaBase.
 * ➤ El registro se realiza directamente desde este formulario (sin ventana externa). */

public class LoginVista extends VentanaBase {

    //-----------------------------------------------------------------------------
    // ATRIBUTOS
    //-----------------------------------------------------------------------------

    private JTextField tfUsuario;
    private JPasswordField pfContrasena;
    private JButton btnLogin;
    private JButton btnRegistrar;

    //-----------------------------------------------------------------------------
    // CONSTRUCTOR
    //-----------------------------------------------------------------------------


    public LoginVista() {
        super("Login");
        setSize(300, 180);
        setLocationRelativeTo(null);

        //-----------------------------------------------------------------------------
        // PANEL PRINCIPAL (Formulario)
        //-----------------------------------------------------------------------------

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(colorFondoOscuro); // Fondo oscuro como en el resto de la app

        // Campo Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(colorTextoClaro);
        panel.add(lblUsuario);

        tfUsuario = new JTextField();
        panel.add(tfUsuario);

        // Campo Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(colorTextoClaro);
        panel.add(lblContrasena);

        pfContrasena = new JPasswordField();
        panel.add(pfContrasena);

        // Botones Login y Registro
        btnLogin = crearBotonEstiloIDE("Iniciar sesión", fuenteNormal);
        btnRegistrar = crearBotonEstiloIDE("Registrarse", fuenteNormal);

        panel.add(btnLogin);
        panel.add(btnRegistrar);

        add(panel);

        //-----------------------------------------------------------------------------
        // EVENTO: Registro desde mismo cuadro
        //-----------------------------------------------------------------------------

        // Aquí se controla directamente el registro sin necesidad de ventana nueva.
        // Si se validan correctamente los campos, se intenta guardar en la base de datos.

        btnRegistrar.addActionListener(e -> {
            String nuevoUsuario = tfUsuario.getText().trim();
            String nuevaContrasena = new String(pfContrasena.getPassword());

            if (nuevoUsuario.isEmpty() || nuevaContrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, rellena todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            controlador.UsuarioDAO dao = new controlador.UsuarioDAO();
            boolean registrado = dao.registrarUsuario(nuevoUsuario, nuevaContrasena);

            // Se aplica estilo visual oscuro a las ventanas emergentes
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

    //-----------------------------------------------------------------------------
    // MÉTODOS GETTERS
    //-----------------------------------------------------------------------------

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

    //-----------------------------------------------------------------------------
    // MÉTODOS EXTRA
    //-----------------------------------------------------------------------------

    public void limpiarCampos() {
        tfUsuario.setText("");
        pfContrasena.setText("");
    }
}
