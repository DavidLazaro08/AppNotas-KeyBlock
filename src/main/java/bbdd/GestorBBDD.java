package bbdd;

import modelo.Usuario;
import modelo.Hashtag;
import modelo.Nota;

import java.sql.*;
import java.util.prefs.Preferences;
import java.util.List;
import java.util.ArrayList;

/** Clase GestorBBDD que centraliza la conexión con la base de datos.
 *
 * ➤ Proporciona métodos para conectar, ejecutar consultas y obtener resultados.
 * ➤ También gestiona funciones adicionales como guardar preferencias del último usuario.
 * ➤ Facilita la recuperación de notas y hashtags relacionados de forma estructurada.
 *
 * ➤ Esta clase pertenece al paquete bbdd (acceso a datos) según la arquitectura en capas. */

public class GestorBBDD {

    // ---------------------- PARÁMETROS DE CONEXIÓN ----------------------

    // URL de conexión local a la base de datos MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/notelab";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    // ---------------------- CONEXIÓN Y DESCONEXIÓN ----------------------

    /** Establece la conexión a la base de datos si aún no existe. */
    public static void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    /** Cierra la conexión abierta, si existe. */
    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /** Devuelve la conexión activa con la base de datos (si no hay, la crea). */
    public static Connection getConnection() throws SQLException {
        connect();
        return connection;
    }

    // ---------------------- CONSULTAS SQL ----------------------

    /** Ejecuta una consulta SELECT y devuelve el resultado.
     *  ➤ Se usa Statement porque el SQL ya viene construido y no cambia en tiempo de ejecución.
     *  ➤ En este proyecto solo se usa con consultas internas, sin intervención del usuario. */

    public static ResultSet executeSelect(String sql) throws SQLException {
        connect();
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    /** Ejecuta una sentencia INSERT, UPDATE o DELETE.
     *  ➤ Este método genérico nos permite ejecutar actualizaciones sin
     *  tener que preparar un statement nuevo. */

    public static int executeUpdate(String sql) throws SQLException {
        connect();
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(sql);
    }

    // ---------------------- PREFERENCIAS DE USUARIO ----------------------

    /** Guarda el último usuario logueado usando Preferences (almacenamiento local).
     *  ➤ Muy útil para recordarlo entre sesiones o depurar más fácilmente. */

    public static void guardarUltimoUsuario(Usuario usuario) {
        Preferences prefs = Preferences.userNodeForPackage(Usuario.class);
        prefs.putInt("ultimoUsuarioId", usuario.getId());
        prefs.put("ultimoUsuarioNombre", usuario.getNombre());
    }

    /** Borra los datos almacenados del último usuario. */
    public static void borrarUltimoUsuario() {
        Preferences prefs = Preferences.userNodeForPackage(Usuario.class);
        prefs.remove("ultimoUsuarioId");
        prefs.remove("ultimoUsuarioNombre");
    }

    // ---------------------- RECUPERACIÓN DE DATOS ----------------------

    /** Devuelve todos los hashtags relacionados con una nota concreta.
     *  ➤ JOIN entre nota_hashtag y hashtags para obtener los textos reales.
     *  ➤ Se usa en obtenerTodasLasNotas() y en obtenerNotasPorUsuario().
     *  ➤ Usa PreparedStatement por seguridad y claridad. */

    public static List<Hashtag> obtenerHashtagsDeNota(int notaId) {
        List<Hashtag> hashtags = new ArrayList<>();
        String sql = """
                SELECT h.id, h.nombre FROM hashtags h
                JOIN nota_hashtag nh ON h.id = nh.hashtag_id
                WHERE nh.nota_id = ?
                """;

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

    /** Devuelve todas las notas de la base de datos, ordenadas por fecha.
     *  ➤ Se usa principalmente por el administrador (ver todo el contenido). */

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

    /** Método placeholder para futuro uso (por ejemplo, generar nuevos IDs si hiciera falta). */
    public static int obtenerIdNota() {
        return 0;
    }

    /** Devuelve todas las notas asociadas a un usuario concreto (por su ID).
     *  ➤ Se utiliza para mostrar solo las notas del usuario que ha iniciado sesión.
     *  ➤ Incluye también los hashtags asociados. */

    public static List<Nota> obtenerNotasPorUsuario(int usuarioId) {
        List<Nota> listaNotas = new ArrayList<>();
        String sql = "SELECT id, titulo, contenido, fecha_creacion FROM notas WHERE usuario_id = ? ORDER BY fecha_creacion DESC";

        try {
            connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Nota nota = new Nota();
                nota.setId(rs.getInt("id"));
                nota.setTitulo(rs.getString("titulo"));
                nota.setContenido(rs.getString("contenido"));
                nota.setFecha(rs.getDate("fecha_creacion").toLocalDate());

                // Se asigna el ID de usuario a la nota cargada
                nota.setUsuarioId(usuarioId);

                // Carga de hashtags asociados
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

