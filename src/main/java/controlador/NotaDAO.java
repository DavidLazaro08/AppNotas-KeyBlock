package controlador;

import bbdd.GestorBBDD;
import modelo.Hashtag;
import modelo.Nota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/* Clase NotaDAO encargada de manejar el acceso a la base de datos para objetos Nota.
 *
 * ➤ Permite guardar una nota con sus hashtags asociados.
 * ➤ Permite eliminar una nota y sus relaciones.
 * ➤ Se comunica con la base de datos a través de la clase GestorBBDD. */

public class NotaDAO {

    //-----------------------------------------------------------------------------
    // GUARDAR NOTA
    //-----------------------------------------------------------------------------

    /* Método que guarda una nueva nota en la base de datos y sus hashtags asociados.
     * ➤ Se usa al crear o editar una nota desde la interfaz.
     * ➤ Internamente inserta los datos y devuelve el ID generado. */

    public static void guardarNota(Nota nota) throws SQLException {
        Connection con = GestorBBDD.getConnection();

        // Insertamos los datos principales de la nota
        String sql = "INSERT INTO notas(titulo, contenido, fecha_creacion, usuario_id) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setString(1, nota.getTitulo());
        stmt.setString(2, nota.getContenido());
        stmt.setDate(3, java.sql.Date.valueOf(nota.getFecha()));
        stmt.setInt(4, nota.getUsuarioId());
        stmt.executeUpdate();

        // Guardamos el ID que nos devuelve la base de datos tras el insert
        int idGenerado = -1;
        ResultSet claves = stmt.getGeneratedKeys();
        if (claves.next()) {
            idGenerado = claves.getInt(1);
        }
        claves.close();
        stmt.close();

        // Por cada hashtag asociado, aseguramos que existe en la tabla de hashtags
        for (Hashtag tag : nota.getHashtags()) {
            String texto = tag.getTexto().toLowerCase().trim();

            // 1. Insertamos el hashtag si no estaba
            String sqlInsertHashtag = "INSERT IGNORE INTO hashtags(nombre) VALUES('" + texto + "')";
            GestorBBDD.executeUpdate(sqlInsertHashtag);

            // 2. Obtenemos el ID del hashtag ya existente o recién insertado
            String sqlGetId = "SELECT id FROM hashtags WHERE nombre = '" + texto + "'";
            ResultSet rs = GestorBBDD.executeSelect(sqlGetId);

            int hashtagId = -1;
            if (rs.next()) {
                hashtagId = rs.getInt("id");
            }
            rs.close();

            // 3. Relacionamos la nota con el hashtag en la tabla intermedia
            if (hashtagId != -1) {
                String sqlRelacion = "INSERT IGNORE INTO nota_hashtag(nota_id, hashtag_id) VALUES(" + idGenerado + ", " + hashtagId + ")";
                GestorBBDD.executeUpdate(sqlRelacion);
            }
        }

        // Sincronizamos el objeto Nota en memoria tras guardarlo
        nota.setId(idGenerado);
        nota.setHashtags(GestorBBDD.obtenerHashtagsDeNota(idGenerado));
    }

    //-----------------------------------------------------------------------------
    // ELIMINAR NOTA
    //-----------------------------------------------------------------------------

    /* Método que elimina una nota y sus relaciones con hashtags.
     * ➤ Primero borra las asociaciones en la tabla intermedia.
     * ➤ Luego elimina la nota como tal. */

    public static void eliminarNotaPorId(int idNota) {
        try {
            // Primero eliminamos relaciones con hashtags
            String sql1 = "DELETE FROM nota_hashtag WHERE nota_id = " + idNota;
            GestorBBDD.executeUpdate(sql1);

            // Después eliminamos la nota
            String sql2 = "DELETE FROM notas WHERE id = " + idNota;
            GestorBBDD.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
