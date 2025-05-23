package modelo;

import java.util.ArrayList;
import java.util.prefs.Preferences;

/* Clase Usuario que representa a un usuario del sistema, ya sea normal o administrador.
 *
 * ➤ Almacena nombre, contraseña, tipo (rol) y su lista de notas asociadas.
 * ➤ Permite saber si el usuario es admin y mostrarlo en texto.
 * ➤ Gestiona las preferencias locales para recordar el último usuario logueado. */

public class Usuario {

    // ---------------------- ATRIBUTOS ----------------------

    private int id;                    // ID del usuario (clave primaria)
    private String nombre;            // Nombre de usuario
    private String contraseña;        // Contraseña (sin cifrar en esta versión)
    private String tipo;              // Puede ser "admin" o "normal"
    private ArrayList<Nota> notas;    // Lista de notas creadas (no usada activamente en esta versión)

    // Instancia de Preferences (almacén local de claves/valores por usuario del sistema)
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

    /* Devuelve true si el usuario tiene rol "admin". */
    public boolean esAdmin() {
        return "admin".equalsIgnoreCase(tipo);
    }

    /* Representación textual: nombre (tipo) */
    @Override
    public String toString() {
        return nombre + " (" + tipo + ")";
    }

    /* Guarda en las preferencias del sistema el último usuario logueado.
     * ➤ Se recuerda solo el ID y el nombre (no la contraseña).
     * ➤ Esto permite cargarlo automáticamente si se desea. */
    public static void guardarUltimoUsuario(Usuario usuario) {
        prefs.putInt("ultimoUsuarioId", usuario.getId());
        prefs.put("ultimoUsuarioNombre", usuario.getNombre());
    }

    /* Recupera los datos del último usuario guardado en local.
     * ➤ Por simplicidad, se asume que el tipo es "normal" y la contraseña vacía. */
    public static Usuario obtenerUltimoUsuario() {
        int ultimoId = prefs.getInt("ultimoUsuarioId", -1);
        String ultimoNombre = prefs.get("ultimoUsuarioNombre", null);

        if (ultimoId != -1 && ultimoNombre != null) {
            return new Usuario(ultimoId, ultimoNombre, "", "normal");
        }
        return null;
    }

    /* Borra los datos del último usuario guardado.
     * ➤ Se puede usar por ejemplo al cerrar sesión. */
    public static void borrarUltimoUsuario() {
        prefs.remove("ultimoUsuarioId");
        prefs.remove("ultimoUsuarioNombre");
    }
}
