package modelo;

import bbdd.GestorBBDD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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

        // TODO: si quieres guardar hashtags, aquí puedes insertarlos en nota_hashtag
    }
}
