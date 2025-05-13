package bbdd;

import java.sql.*;
/*IMPORTANTE:
*   lo que recibe cada método son las líneas de código que se van a ejecutar. Por ej en excecuteSelect(),
*    se le envía en formate String, la línea de código que se quiere ejecutar:
*    Ejemplo: GestroBBDD.executeSelect("SELECT * FROM usuarios")
*    Ejemplo en executeUpdate: GestorBBDD.executeUpdate("INSERT INTO productos(nombre, precio) VALUES('Zapato', 29.99)")*/

public class GestorBBDD {

    // ⬇️⬇️⬇️HAY QUE PONER LA URL REAL DE LA BASE DE DATOS⬇️⬇️⬇️
        private static final String URL = "jdbc:mysql://localhost:3306/tu_base_de_datos";
        private static final String USER = "tu_usuario";
        private static final String PASSWORD = "tu_contraseña";

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

}