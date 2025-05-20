package controlador;

import modelo.UsuarioDAO;
import vista.LoginVista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Clase RegistroControlador
 * Maneja la lógica del botón de registro desde la vista de LoginVista.
 *
 * ➤ Sustituye a la antigua clase RegistroVista.
 * ➤ Integra el registro de usuario directamente desde LoginVista.
 * ➤ Se eliminó RegistroVista al centralizar el diseño en un solo cuadro.
 */

public class RegistroControlador {

    private LoginVista loginVista;
    private UsuarioDAO usuarioDAO;

    public RegistroControlador(LoginVista loginVista) {
        this.loginVista = loginVista;
        this.usuarioDAO = new UsuarioDAO();

        // Acción para el botón "Registrarse"
        this.loginVista.getRegistrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoUsuario = loginVista.getUsuario();
                String nuevaContrasena = loginVista.getContrasena();

                if (nuevoUsuario.isEmpty() || nuevaContrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(loginVista, "Por favor, rellena todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean registrado = usuarioDAO.registrarUsuario(nuevoUsuario, nuevaContrasena);

                // Estilo oscuro para los JOptionPane
                UIManager.put("OptionPane.background", new java.awt.Color(43, 43, 43));
                UIManager.put("Panel.background", new java.awt.Color(43, 43, 43));
                UIManager.put("OptionPane.messageForeground", java.awt.Color.WHITE);
                UIManager.put("OptionPane.buttonFont", new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 13));

                if (registrado) {
                    JOptionPane.showMessageDialog(loginVista, "Registro exitoso. Ahora puedes iniciar sesión.");
                    loginVista.limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(loginVista, "Error al registrar. El usuario ya existe o fallo de conexión.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
