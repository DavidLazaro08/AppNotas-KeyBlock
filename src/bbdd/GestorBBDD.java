package bbdd;

import modelo.Usuario;
import java.sql.*;
import java.util.prefs.Preferences;

public class GestorBBDD {

    // ⬇️⬇️⬇️HAY QUE PONER LA URL REAL DE LA BASE DE DATOS⬇️⬇️⬇️
    private static final String URL = "jdbc:mysql://localhost:3306/notelab";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    // Inicializa la conexión
    public static void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida correctamente.");
        }
    }

    // Cierra la conexión
    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Conexión cerrada.");
        }
    }

    // Ejecuta un SELECT y devuelve los resultados
    public static ResultSet executeSelect(String sql) throws SQLException {
        connect();
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    // Ejecuta INSERT, UPDATE o DELETE
    public static int executeUpdate(String sql) throws SQLException {
        connect();
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(sql); // devuelve el número de filas afectadas
    }

    // Obtiene el último usuario conectado desde la base de datos
    public static Usuario obtenerUltimoUsuario() throws SQLException {
        Preferences prefs = Preferences.userNodeForPackage(Usuario.class);
        int ultimoUsuarioId = prefs.getInt("ultimoUsuarioId", -1);

        if (ultimoUsuarioId == -1) {
            return null; // Si no hay ID guardado, no se devuelve ningún usuario
        }

        String sql = "SELECT * FROM usuarios WHERE id = " + ultimoUsuarioId;
        ResultSet rs = executeSelect(sql);

        if (rs.next()) {
            // Aquí mapeamos los resultados de la base de datos a un objeto Usuario
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String contraseña = rs.getString("contraseña");
            String tipo = rs.getString("tipo");

            return new Usuario(id, nombre, contraseña, tipo);
        } else {
            return null; // Si no se encuentra el usuario, devolvemos null
        }
    }

    // Método para guardar el último usuario en las preferencias (cuando inicie sesión)
    public static void guardarUltimoUsuario(Usuario usuario) {
        Preferences prefs = Preferences.userNodeForPackage(Usuario.class);
        prefs.putInt("ultimoUsuarioId", usuario.getId());
        prefs.put("ultimoUsuarioNombre", usuario.getNombre());
    }

    // Método para borrar el último usuario guardado
    public static void borrarUltimoUsuario() {
        Preferences prefs = Preferences.userNodeForPackage(Usuario.class);
        prefs.remove("ultimoUsuarioId");
        prefs.remove("ultimoUsuarioNombre");
    }
}
