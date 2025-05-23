package controlador;

import vista.LoginVista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Clase RegistroControlador
 *
 * ➤ Maneja la lógica del botón de registro desde la vista de LoginVista.
 * ➤ Sustituye a la antigua clase RegistroVista, que fue eliminada.
 * ➤ Ahora el registro de usuario se hace directamente desde el mismo cuadro de login. */

public class RegistroControlador {

    //-----------------------------------------------------------------------------
    // ATRIBUTOS
    //-----------------------------------------------------------------------------

    private LoginVista loginVista;

    //-----------------------------------------------------------------------------
    // CONSTRUCTOR
    //-----------------------------------------------------------------------------

    public RegistroControlador(LoginVista loginVista) {
        this.loginVista = loginVista;

        // Acción que se ejecuta al pulsar el botón "Registrarse"
        this.loginVista.getRegistrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Se recuperan los datos introducidos por el usuario
                String nuevoUsuario = loginVista.getUsuario();
                String nuevaContrasena = loginVista.getContrasena();

                // Validación básica: no permitir campos vacíos
                if (nuevoUsuario.isEmpty() || nuevaContrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(loginVista, "Por favor, rellena todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Se intenta registrar el nuevo usuario
                boolean registrado = UsuarioDAO.registrarUsuario(nuevoUsuario, nuevaContrasena);

                // Aplicar estilo oscuro a los mensajes emergentes
                UIManager.put("OptionPane.background", new java.awt.Color(43, 43, 43));
                UIManager.put("Panel.background", new java.awt.Color(43, 43, 43));
                UIManager.put("OptionPane.messageForeground", java.awt.Color.WHITE);
                UIManager.put("OptionPane.buttonFont", new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 13));

                if (registrado) {
                    // Éxito: mensaje y limpieza de campos
                    JOptionPane.showMessageDialog(loginVista, "Registro exitoso. Ahora puedes iniciar sesión.");
                    loginVista.limpiarCampos();
                } else {
                    // Error: el usuario ya existe o hay fallo de conexión
                    JOptionPane.showMessageDialog(loginVista, "Error al registrar. El usuario ya existe o fallo de conexión.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
