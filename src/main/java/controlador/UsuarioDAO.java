package controlador;

import bbdd.GestorBBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** Clase UsuarioDAO que gestiona las operaciones relacionadas con usuarios en la base de datos.
 *
 * ➤ Permite validar si un usuario existe con su contraseña.
 * ➤ Permite registrar nuevos usuarios asignándoles por defecto el rol "usuario".
 * ➤ Verifica si un usuario es administrador (rol = admin).
 *
 * ➤ Se dejaron todos los métodos son estáticos porque no se guarda ningún estado interno.
 *    Esto simplifica su uso y lo hace coherente con otras clases como NotaDAO. */

public class UsuarioDAO {

    // ---------------------- MÉTODO: Validar credenciales ----------------------

    public static boolean validarUsuario(String nombreUsuario, String contrasena) {
        boolean valido = false;
        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contraseña = ?";
        try (Connection conn = GestorBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            valido = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return valido;
    }

    // ---------------------- MÉTODO: Registrar nuevo usuario ----------------------

    public static boolean registrarUsuario(String nombreUsuario, String contrasena) {
        String sql = "INSERT INTO usuarios (nombre, contraseña, rol) VALUES (?, ?, 'usuario')";
        try (Connection conn = GestorBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasena);
            stmt.executeUpdate();
            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- MÉTODO: Verificar si el usuario es admin ----------------------

    public static boolean esAdmin(String nombreUsuario) {
        String sql = "SELECT rol FROM usuarios WHERE nombre = ?";
        try (Connection conn = GestorBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String rol = rs.getString("rol");
                return "admin".equalsIgnoreCase(rol);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
