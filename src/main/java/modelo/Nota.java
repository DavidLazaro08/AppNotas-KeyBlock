package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/* Clase Nota que representa una nota individual dentro de la aplicación.
 *
 * ➤ Almacena información como título, contenido, fecha y usuario al que pertenece.
 * ➤ También gestiona la lista de hashtags asociados.
 * ➤ Se utiliza tanto para mostrar como para guardar o editar notas. */

public class Nota {

    // ---------------------- ATRIBUTOS ----------------------

    private int id;
    private String titulo;
    private String contenido;
    private LocalDate fecha;
    private int usuarioId;
    private List<Hashtag> hashtags = new ArrayList<>();

    // ---------------------- CONSTRUCTORES ----------------------

    public Nota() {
        // Constructor vacío necesario para frameworks o compatibilidad
    }

    public Nota(int id, String titulo, String contenido, LocalDate fecha, int usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
    }

    // ---------------------- GETTERS ----------------------

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

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    // ---------------------- SETTERS ----------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public void setHashtag(Hashtag hashtag) {
        hashtags.add(hashtag);
    }

    // ---------------------- TO STRING ----------------------

    @Override
    public String toString() {
        return "[" + fecha + "] " + titulo;
    }
}
