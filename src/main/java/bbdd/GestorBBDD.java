package bbdd;

import modelo.Usuario;
import modelo.Hashtag;
import modelo.Nota;

import java.sql.*;
import java.util.prefs.Preferences;
import java.util.List;
import java.util.ArrayList;

public class GestorBBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/notelab";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static ResultSet executeSelect(String sql) throws SQLException {
        connect();
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public static int executeUpdate(String sql) throws SQLException {
        connect();
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(sql);
    }

    public static Connection getConnection() throws SQLException {
        connect();
        return connection;
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

    /**
     * Devuelve todos los hashtags relacionados con una nota concreta.
     * Hace JOIN entre nota_hashtag y hashtags para obtener los textos reales.
     * ➤ Se usa dentro de obtenerTodasLasNotas().
     */
    public static List<Hashtag> obtenerHashtagsDeNota(int notaId) {
        List<Hashtag> hashtags = new ArrayList<>();
        String sql = "SELECT h.id, h.nombre FROM hashtags h " +
                "JOIN nota_hashtag nh ON h.id = nh.hashtag_id " +
                "WHERE nh.nota_id = ?";

        try {
            connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, notaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Hashtag hashtag = new Hashtag(rs.getString("nombre"));
                hashtags.add(hashtag);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hashtags;
    }

    // Método para obtener todas las notas de la base de datos (con hashtags reales)
    public static List<Nota> obtenerTodasLasNotas() {
        List<Nota> listaNotas = new ArrayList<>();
        String sql = "SELECT id, titulo, contenido, fecha_creacion FROM notas ORDER BY fecha_creacion DESC";

        try {
            connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Nota nota = new Nota();
                nota.setId(rs.getInt("id"));
                nota.setTitulo(rs.getString("titulo"));
                nota.setContenido(rs.getString("contenido"));
                nota.setFecha(rs.getDate("fecha_creacion").toLocalDate());

                // Añadir hashtags relacionados desde la tabla intermedia
                nota.setHashtags(obtenerHashtagsDeNota(nota.getId()));

                listaNotas.add(nota);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaNotas;
    }
}
