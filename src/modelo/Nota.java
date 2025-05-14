package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Nota {
    private int id;
    private String titulo;
    private String contenido;
    private LocalDate fecha;
    private int usuarioId;
    private ArrayList<Hashtag> hashtags= new ArrayList<>();

    public Nota(int id, String titulo, String contenido, LocalDate fecha, int usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
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

    public ArrayList<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setHashtags(ArrayList<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }


    public void setHashtag(Hashtag hashtag){
        hashtags.add(hashtag);
    }

    @Override
    public String toString() {
        return "[" + fecha + "] " + titulo;
    }
}
