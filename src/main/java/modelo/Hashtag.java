package modelo;

/* Clase Hashtag que representa una etiqueta usada dentro de una nota.
 *
 * ➤ Almacena el texto del hashtag sin el símbolo "#" (solo la palabra).
 * ➤ Permite acceder o modificar su contenido mediante getters y setters.
 * ➤ Se utiliza tanto para mostrar etiquetas como para asociarlas a una nota. */

public class Hashtag {

    // ---------------------- ATRIBUTOS ----------------------

    private String texto;

    // ---------------------- CONSTRUCTOR ----------------------

    public Hashtag(String texto) {
        this.texto = texto;
    }

    // ---------------------- MÉTODOS ----------------------

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    /* Alias de getTexto(). Lo dejamos por compatibilidad con otras clases si lo necesitan. */

    public String getNombre() {
        return texto;
    }
}
