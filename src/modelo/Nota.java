package modelo;

import java.time.LocalDate;
import java.util.List;

public class Nota {
    private int id;
    private String titulo;
    private String contenido;
    private LocalDate fecha;
    private int usuarioId;
    private List<String> hashtags;

    public Nota(int id, String titulo, String contenido, LocalDate fecha, int usuarioId, List<String> hashtags) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.hashtags = hashtags;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    @Override
    public String toString() {
        return "[" + fecha + "] " + titulo;
    }
}
