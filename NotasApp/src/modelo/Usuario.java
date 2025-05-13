package modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String contraseña;
    private String tipo; // "admin" o "normal"

    public Usuario() {}

    public boolean esAdmin() {
        return "admin".equals(tipo);
    }
}