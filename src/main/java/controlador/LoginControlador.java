package controlador;


import bbdd.GestorBBDD;
import modelo.Usuario;

import javax.swing.*;
import java.sql.SQLException;

public class LoginControlador {
    public LoginControlador() {
        // Controlador del login
    }


    public static void registrarYGuardarUltimoUsuario(JTextPane nombrePane, JTextPane contraseñaPane) throws SQLException {
        String nombre = nombrePane.getText().trim();
        String contraseña = contraseñaPane.getText().trim();

        // Crear el usuario con los datos
        Usuario nuevoUsuario = new Usuario(0, nombre, contraseña, "normal"); // Asume "normal" como tipo de usuario

        // Guardar el nuevo usuario en la base de datos (inserción, y obtener el ID generado)
        String sql = "INSERT INTO usuarios (nombre, contraseña, tipo) VALUES ('" + nombre + "', '" + contraseña + "', 'normal')";

        // Guardar el último usuario en las preferencias
        GestorBBDD.guardarUltimoUsuario(nuevoUsuario);
    }}