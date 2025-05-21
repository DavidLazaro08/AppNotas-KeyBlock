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

/*
 // Código de prueba para verificar conexión a la base de datos
 import bbdd.GestorBBDD;
 import java.sql.ResultSet;
 import java.sql.SQLException;

 public class Main {
     public static void main(String[] args) {
         try {
             GestorBBDD.connect();
             ResultSet rs = GestorBBDD.executeSelect("SELECT * FROM usuarios");
             while (rs.next()) {
                 System.out.println("Usuario: " + rs.getString("nombre"));
             }
             GestorBBDD.disconnect();
         } catch (SQLException e) {
             System.out.println("Error al conectar con la base de datos:");
             e.printStackTrace();
         }
     }
 }
*/
