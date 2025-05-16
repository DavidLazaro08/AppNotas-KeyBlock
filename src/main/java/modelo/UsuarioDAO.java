package modelo;

import bbdd.GestorBBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    // Validar usuario con nombre y contraseña
    public boolean validarUsuario(String nombreUsuario, String contrasena) {
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

    // Registrar usuario nuevo
    public boolean registrarUsuario(String nombreUsuario, String contrasena) {
        String sql = "INSERT INTO usuarios (nombre, contraseña, rol) VALUES (?, ?, 'usuario')";
        try (Connection conn = GestorBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasena);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Verificar si usuario es admin (para tu control en login)
    public boolean esAdmin(String nombreUsuario) {
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
