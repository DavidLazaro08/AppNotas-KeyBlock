package app;

import bbdd.GestorBBDD;
import vista.PrincipalVista;
import controlador.LoginControlador;
import vista.LoginVista;

import java.sql.ResultSet;
import java.sql.SQLException;


import controlador.LoginControlador;
import vista.LoginVista;

public class Main {
    public static void main(String[] args) {
        LoginVista loginVista = new LoginVista();
        new controlador.LoginControlador(loginVista);
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