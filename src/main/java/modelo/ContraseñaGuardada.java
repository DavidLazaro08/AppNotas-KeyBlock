package modelo;

/* Clase ContraseñaGuardada que representa una contraseña almacenada por el usuario.
 *
 * ➤ Se utilizaría para mostrar y gestionar contraseñas dentro del gestor de contraseñas de la app.
 * ➤ Almacena datos como el servicio (página/app), el usuario usado y la contraseña codificada.
 * ➤ Incluye el ID del usuario al que pertenece, para asociarlo en base de datos.
 * ➤ Tiene un toString personalizado para mostrar la contraseña de forma legible en listas. */

public class ContraseñaGuardada {

    //-----------------------------------------------------------------------------
    // ATRIBUTOS
    //-----------------------------------------------------------------------------

    private int id;
    private String servicio;
    private String usuario;
    private String contraseñaCodificada;
    private int usuarioId;

    //-----------------------------------------------------------------------------
    // CONSTRUCTOR
    //-----------------------------------------------------------------------------

    /* Al crear una nueva contraseña, se le asignan todos los campos relevantes.
     * ➤ El ID puede ser 0 si aún no se ha guardado en base de datos. */
    public ContraseñaGuardada(int id, String servicio, String usuario, String contraseñaCodificada, int usuarioId) {
        this.id = id;
        this.servicio = servicio;
        this.usuario = usuario;
        this.contraseñaCodificada = contraseñaCodificada;
        this.usuarioId = usuarioId;
    }

    //-----------------------------------------------------------------------------
    // GETTERS
    //-----------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public String getServicio() {
        return servicio;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseñaCodificada() {
        return contraseñaCodificada;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    //-----------------------------------------------------------------------------
    // TO STRING
    //-----------------------------------------------------------------------------

    /* Se sobrescribe toString() para que, al mostrar este objeto en una lista visual,
     * aparezca el nombre del servicio seguido del usuario en corchetes. */
    @Override
    public String toString() {
        return servicio + " [" + usuario + "]";
    }
}
