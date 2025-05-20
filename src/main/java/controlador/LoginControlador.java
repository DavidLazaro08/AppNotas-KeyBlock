package controlador;

import modelo.UsuarioDAO;
import vista.LoginVista;
import vista.PrincipalVista;

import javax.swing.*;

/** Clase LoginControlador que gestiona la lógica de inicio de sesión.
 *
 * ➤ Valida usuario y contraseña usando UsuarioDAO.
 * ➤ Si el login es correcto, abre la ventana principal.
 * ➤ El registro de usuario ahora se gestiona desde LoginVista directamente. */

public class LoginControlador {

    // ---------------------- ATRIBUTOS ----------------------

    private LoginVista vista;
    private UsuarioDAO usuarioDAO;

    // ---------------------- CONSTRUCTOR ----------------------

    public LoginControlador(LoginVista vista) {
        this.vista = vista;
        this.usuarioDAO = new UsuarioDAO();

        // ---------------------- EVENTO: Iniciar sesión ----------------------

        this.vista.getLoginButton().addActionListener(e -> {
            String usuario = vista.getUsuario();
            String contrasena = vista.getContrasena();

            if (usuarioDAO.validarUsuario(usuario, contrasena)) {
                vista.dispose(); // cerrar login
                PrincipalVista principal = new PrincipalVista(usuario);
                principal.setVisible(true);

                if (usuarioDAO.esAdmin(usuario)) {
                    principal.mostrarAdmin();
                }
            } else {
                // Estilo oscuro para el mensaje de error
                UIManager.put("OptionPane.background", new java.awt.Color(43, 43, 43));
                UIManager.put("Panel.background", new java.awt.Color(43, 43, 43));
                UIManager.put("OptionPane.messageForeground", java.awt.Color.WHITE);
                UIManager.put("OptionPane.buttonFont", new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 13));

                JOptionPane.showMessageDialog(vista, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
