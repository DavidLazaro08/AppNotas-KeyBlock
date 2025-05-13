package modelo;

public class ContraseñaGuardada {
    private int id;
    private String servicio;
    private String usuario;
    private String contraseñaCodificada;
    private int usuarioId;

    public ContraseñaGuardada(int id, String servicio, String usuario, String contraseñaCodificada, int usuarioId) {
        this.id = id;
        this.servicio = servicio;
        this.usuario = usuario;
        this.contraseñaCodificada = contraseñaCodificada;
        this.usuarioId = usuarioId;
    }

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

    @Override
    public String toString() {
        return servicio + " [" + usuario + "]";
    }
}
