package modelo;

import java.util.ArrayList;
import java.util.prefs.Preferences;

public class Usuario {
    private int id;
    private String nombre;
    private String contraseña;
    private String tipo; // "admin" o "normal"
    private ArrayList<Nota> notas;

    // Instancia de Preferences para guardar la información de usuario
    private static final Preferences prefs = Preferences.userNodeForPackage(Usuario.class);

    public Usuario(int id, String nombre, String contraseña, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.tipo = tipo;
    }

    // Métodos para obtener los datos del usuario
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

    //guardar el ID del último usuario registrado
    public static void guardarUltimoUsuario(Usuario usuario) {
        prefs.putInt("ultimoUsuarioId", usuario.getId()); // Guardamos el ID del usuario
        prefs.put("ultimoUsuarioNombre", usuario.getNombre()); // Guardamos el nombre del usuario
    }

  //obtener el último usuario registrado
    public static Usuario obtenerUltimoUsuario() {
        int ultimoId = prefs.getInt("ultimoUsuarioId", -1); // Recuperamos el ID del último usuario
        String ultimoNombre = prefs.get("ultimoUsuarioNombre", null); // Recuperamos el nombre del usuario

        // Si hay un ID válido (distinto de -1) y un nombre, devolvemos el último usuario
        if (ultimoId != -1 && ultimoNombre != null) {
            // En un caso real, deberías buscar el usuario en tu base de datos
            // o en una lista de usuarios cargados. Por ahora, devolvemos un usuario de ejemplo.
            // Este código debe ser ajustado para recuperar correctamente el usuario.
            return new Usuario(ultimoId, ultimoNombre, "", "normal"); // Suponiendo que sea de tipo "normal"
        }
        return null; // No hay último usuario guardado
    }

    //borrar las preferencias del último usuario (si es necesario)
    public static void borrarUltimoUsuario() {
        prefs.remove("ultimoUsuarioId");
        prefs.remove("ultimoUsuarioNombre");
    }
}
