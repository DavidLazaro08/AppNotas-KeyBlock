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
 * ➤ Todos los métodos son estáticos porque no se necesita guardar estado.
 *    Esto hace que su uso sea más directo y esté en la línea de clases como NotaDAO. */

public class UsuarioDAO {

    //-----------------------------------------------------------------------------
    // MÉTODO: Validar credenciales
    //-----------------------------------------------------------------------------

    /* Comprueba si el nombre y contraseña coinciden con un usuario existente.
     * ➤ Se usa en el login para permitir el acceso.
     * ➤ Devuelve true si encuentra coincidencia en la base de datos. */
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

    //-----------------------------------------------------------------------------
    // MÉTODO: Registrar nuevo usuario
    //-----------------------------------------------------------------------------

    /* Inserta un nuevo usuario con rol "usuario" por defecto.
     * ➤ Usado desde el botón de registro.
     * ➤ Si el nombre ya existe o hay error, devuelve false. */
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

    //-----------------------------------------------------------------------------
    // MÉTODO: Verificar si el usuario es admin
    //-----------------------------------------------------------------------------

    /* Devuelve true si el usuario tiene rol "admin".
     * ➤ Se usa para mostrar o permitir acceso al panel de administración. */
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

    //-----------------------------------------------------------------------------
    // MÉTODO: Obtener objeto Usuario completo
    //-----------------------------------------------------------------------------

    /* Carga el usuario desde la base de datos y lo devuelve como objeto Usuario.
     * ➤ Este método permite trabajar con toda la información del usuario (id, contraseña, rol...).
     * ➤ Muy útil al iniciar sesión para tener todos sus datos disponibles. */
    public static modelo.Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
        String sql = "SELECT * FROM usuarios WHERE nombre = ?";
        try (Connection conn = GestorBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String contrasena = rs.getString("contraseña");
                String rol = rs.getString("rol");

                return new modelo.Usuario(id, nombreUsuario, contrasena, rol);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
