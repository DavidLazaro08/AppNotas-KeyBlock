package modelo;

import java.util.ArrayList;
import java.util.prefs.Preferences;

/* Clase Usuario que representa a un usuario del sistema, ya sea normal o administrador.
 *
 * ➤ Almacena nombre, contraseña, tipo y su lista de notas asociadas.
 * ➤ Ofrece métodos para guardar y recuperar el último usuario logueado usando Preferences de Java.
 * ➤ Incluye utilidad para comprobar si es un administrador y para mostrarlo como texto. */

public class Usuario {

    // ---------------------- ATRIBUTOS ----------------------

    private int id;
    private String nombre;
    private String contraseña;
    private String tipo; // "admin" o "normal"
    private ArrayList<Nota> notas;

    // Instancia para guardar datos localmente (último usuario)
    private static final Preferences prefs = Preferences.userNodeForPackage(Usuario.class);

    // ---------------------- CONSTRUCTOR ----------------------

    public Usuario(int id, String nombre, String contraseña, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.tipo = tipo;
    }

    // ---------------------- GETTERS ----------------------

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

    // ---------------------- OTROS MÉTODOS ----------------------

    public boolean esAdmin() {
        return "admin".equalsIgnoreCase(tipo);
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ")";
    }

    /* Guarda en las preferencias del sistema el último usuario que se ha logueado. */

    public static void guardarUltimoUsuario(Usuario usuario) {
        prefs.putInt("ultimoUsuarioId", usuario.getId());
        prefs.put("ultimoUsuarioNombre", usuario.getNombre());
    }

    /* Recupera los datos del último usuario registrado (solo ID y nombre). */

    public static Usuario obtenerUltimoUsuario() {
        int ultimoId = prefs.getInt("ultimoUsuarioId", -1);
        String ultimoNombre = prefs.get("ultimoUsuarioNombre", null);

        if (ultimoId != -1 && ultimoNombre != null) {
            return new Usuario(ultimoId, ultimoNombre, "", "normal"); // El tipo se asume como "normal"
        }
        return null;
    }

    /* Borra los datos del último usuario guardado en las preferencias. */

    public static void borrarUltimoUsuario() {
        prefs.remove("ultimoUsuarioId");
        prefs.remove("ultimoUsuarioNombre");
    }
}
