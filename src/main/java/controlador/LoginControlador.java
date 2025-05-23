package controlador;

import modelo.Usuario;
import vista.LoginVista;
import vista.PrincipalVista;

import javax.swing.*;

/** Clase LoginControlador que gestiona la lógica de inicio de sesión.
 *
 * ➤ Valida usuario y contraseña usando UsuarioDAO.
 * ➤ Si el login es correcto, abre la ventana principal con el objeto Usuario.
 * ➤ El registro de usuario ahora se gestiona desde LoginVista directamente. */

public class LoginControlador {

    // ---------------------- ATRIBUTOS ----------------------

    private LoginVista vista;

    // ---------------------- CONSTRUCTOR ----------------------

    public LoginControlador(LoginVista vista) {
        this.vista = vista;

        // ---------------------- EVENTO: Iniciar sesión ----------------------

        // Al pulsar el botón de login, se validan los datos introducidos
        this.vista.getLoginButton().addActionListener(e -> {
            String nombre = vista.getUsuario();
            String contrasena = vista.getContrasena();

            // Si el usuario existe y la contraseña coincide...
            if (UsuarioDAO.validarUsuario(nombre, contrasena)) {
                // Se recupera el objeto Usuario completo desde la base de datos
                Usuario usuario = UsuarioDAO.obtenerUsuarioPorNombre(nombre);

                vista.dispose(); // Se cierra la ventana de login
                PrincipalVista principal = new PrincipalVista(usuario); // Se abre la vista principal
                principal.setVisible(true);

                // Si el usuario tiene rol admin, se muestra el panel correspondiente
                if (usuario.esAdmin()) {
                    principal.mostrarAdmin();
                }

            } else {
                // Estilo oscuro para mostrar el mensaje de error visualmente integrado con el resto de la app
                UIManager.put("OptionPane.background", new java.awt.Color(43, 43, 43));
                UIManager.put("Panel.background", new java.awt.Color(43, 43, 43));
                UIManager.put("OptionPane.messageForeground", java.awt.Color.WHITE);
                UIManager.put("OptionPane.buttonFont", new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 13));

                JOptionPane.showMessageDialog(vista, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

