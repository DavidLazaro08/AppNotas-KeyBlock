package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Nota {
    public Nota() {
        // Constructor vacío necesario
    }

    private int id;
    private String titulo;
    private String contenido;
    private LocalDate fecha;
    private int usuarioId;
    private List<Hashtag> hashtags = new ArrayList<>();


    public Nota(int id, String titulo, String contenido, LocalDate fecha, int usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getContenido() { return contenido; }
    public LocalDate getFecha() { return fecha; }
    public int getUsuarioId() { return usuarioId; }
    public List<Hashtag> getHashtags() { return hashtags; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public void setHashtags(List<Hashtag> hashtags) { this.hashtags = hashtags; }
    public void setHashtag(Hashtag hashtag) { hashtags.add(hashtag); }

    @Override
    public String toString() {
        return "[" + fecha + "] " + titulo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }


}
