package app;

import bbdd.GestorBBDD;
import vista.PrincipalVista;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {

        // Usamos el Main para simplemente lanzar la interfaz.
        new PrincipalVista();
    }
}

/*
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