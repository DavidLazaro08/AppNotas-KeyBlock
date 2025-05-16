package controlador;

import modelo.UsuarioDAO;
import vista.LoginVista;
import vista.PanelAdmin;
import vista.PrincipalVista;

import javax.swing.*;

public class LoginControlador {

    private LoginVista vista;
    private UsuarioDAO usuarioDAO;

    public LoginControlador(LoginVista vista) {
        this.vista = vista;
        this.usuarioDAO = new UsuarioDAO();

        this.vista.getLoginButton().addActionListener(e -> {
            String usuario = vista.getUsuario();
            String contrasena = vista.getContrasena();

            if (usuarioDAO.validarUsuario(usuario, contrasena)) {
                vista.dispose(); // cerrar login
                if (usuarioDAO.esAdmin(usuario)) {
                    new PanelAdmin().setVisible(true);
                } else {
                    new PrincipalVista().setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

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
