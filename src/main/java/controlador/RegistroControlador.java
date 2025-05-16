package controlador;

import modelo.UsuarioDAO;
import vista.RegistroVista;

import javax.swing.*;

public class RegistroControlador {
    private RegistroVista vista;
    private UsuarioDAO usuarioDAO;

    public RegistroControlador(JFrame parent) {
        this.vista = new RegistroVista(parent);
        this.usuarioDAO = new UsuarioDAO();

        vista.getBotonRegistrar().addActionListener(e -> {
            String usuario = vista.getUsuario();
            String contrasena = vista.getContrasena();

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Completa todos los campos");
                return;
            }

            boolean exito = usuarioDAO.registrarUsuario(usuario, contrasena);
            if (exito) {
                JOptionPane.showMessageDialog(vista, "Usuario registrado correctamente");
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "El usuario ya existe o ha habido un error");
            }
        });

        vista.setVisible(true);
    }
}
