package modelo;

import java.util.ArrayList;

public class Usuario {
    private int id;
    private String nombre;
    private String contraseña;
    private String tipo; // "admin" o "normal"
    private ArrayList<Nota> notas;

    public Usuario(int id, String nombre, String contraseña, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean esAdmin() {
        return "admin".equalsIgnoreCase(tipo);
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ")";
    }
}
