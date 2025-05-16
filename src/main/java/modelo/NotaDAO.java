package modelo;

import bbdd.GestorBBDD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotaDAO {

    public static void guardarNota(Nota nota) throws SQLException {
        Connection con = GestorBBDD.getConnection();
        String sql = "INSERT INTO notas(titulo, contenido, fecha_creacion, usuario_id) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setString(1, nota.getTitulo());
        stmt.setString(2, nota.getContenido());
        stmt.setDate(3, java.sql.Date.valueOf(nota.getFecha()));
        stmt.setInt(4, nota.getUsuarioId());
        stmt.executeUpdate();

        // Obtener el ID generado de la nota
        int idGenerado = -1;
        ResultSet claves = stmt.getGeneratedKeys();
        if (claves.next()) {
            idGenerado = claves.getInt(1);
        }
        claves.close();
        stmt.close();

        // 🔁 Guardar hashtags y relación con la nota
        for (Hashtag tag : nota.getHashtags()) {
            String texto = tag.getTexto().toLowerCase().trim();

            // 1. Insertar hashtag si no existe
            String sqlInsertHashtag = "INSERT IGNORE INTO hashtags(nombre) VALUES('" + texto + "')";
            GestorBBDD.executeUpdate(sqlInsertHashtag);

            // 2. Obtener su ID
            String sqlGetId = "SELECT id FROM hashtags WHERE nombre = '" + texto + "'";
            ResultSet rs = GestorBBDD.executeSelect(sqlGetId);

            int hashtagId = -1;
            if (rs.next()) {
                hashtagId = rs.getInt("id");
            }
            rs.close();

            // 3. Insertar relación en nota_hashtag
            if (hashtagId != -1) {
                String sqlRelacion = "INSERT IGNORE INTO nota_hashtag(nota_id, hashtag_id) VALUES(" + idGenerado + ", " + hashtagId + ")";
                GestorBBDD.executeUpdate(sqlRelacion);
            }
        }

        // ✅ Sincronizar el objeto nota tras guardar completamente
        nota.setId(idGenerado);
        nota.setHashtags(GestorBBDD.obtenerHashtagsDeNota(idGenerado));
    }
}
