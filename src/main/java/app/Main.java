package app;

import vista.LoginVista;
import controlador.LoginControlador;

/** Clase Main que inicia la aplicación.
 *
 * ➤ Muestra la ventana de Login al arrancar el programa.
 * ➤ Inicializa el controlador correspondiente. */

public class Main {
    public static void main(String[] args) {
        LoginVista loginVista = new LoginVista();
        new LoginControlador(loginVista);
    }
}

/* Código de prueba para verificar conexión a la base de datos

 import bbdd.GestorBBDD;
 import java.sql.ResultSet;
 import java.sql.SQLException;

 // Esta versión alternativa del main fue utilizada en fases tempranas del proyecto
 // para comprobar si la conexión con la base de datos funcionaba correctamente

  public class Main {
     public static void main(String[] args) {
         try {
             // Se abre la conexión usando el gestor
             GestorBBDD.connect();

             // Se lanza una consulta de prueba: obtener todos los usuarios
             ResultSet rs = GestorBBDD.executeSelect("SELECT * FROM usuarios");

             // Se imprime en consola el nombre de cada usuario que se recupere
             while (rs.next()) {
                 System.out.println("Usuario: " + rs.getString("nombre"));
             }

             // Se cierra la conexión
             GestorBBDD.disconnect();

         } catch (SQLException e) {
             // En caso de error, se muestra por consola
             System.out.println("Error al conectar con la base de datos:");
             e.printStackTrace();
         }
     }
 } */
