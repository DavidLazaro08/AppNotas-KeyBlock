package controlador;

import modelo.UsuarioDAO;
import vista.LoginVista;
import vista.PrincipalVista;

import javax.swing.*;

/** Clase LoginControlador que gestiona el comportamiento del login y registro de usuarios.
 *
 * ➤ Se conecta con UsuarioDAO para validar usuarios contra la base de datos.
 * ➤ Si el usuario es válido, se abre la ventana principal. */

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
                PrincipalVista principal = new PrincipalVista(usuario); // ✅ se pasa el usuario logueado
                principal.setVisible(true);

                if (usuarioDAO.esAdmin(usuario)) {
                    principal.mostrarAdmin();
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ---------------------- EVENTO: Registrar usuario ----------------------

        this.vista.getRegistrarButton().addActionListener(e -> {
            String usuario = vista.getUsuario();
            String contrasena = vista.getContrasena();

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, rellena todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean registrado = usuarioDAO.registrarUsuario(usuario, contrasena);
            if (registrado) {
                JOptionPane.showMessageDialog(vista, "Registro exitoso. Ahora puedes iniciar sesión.");
                vista.limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar. El usuario ya existe o fallo de conexión.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}