package modelo;

/* Clase Hashtag que representa una etiqueta usada dentro de una nota.
 *
 * ➤ Almacena el texto del hashtag sin el símbolo "#" (solo la palabra).
 * ➤ Permite acceder o modificar su contenido mediante getters y setters.
 * ➤ Se utiliza tanto para mostrar etiquetas como para asociarlas a una nota. */

public class Hashtag {

    //-----------------------------------------------------------------------------
    // ATRIBUTOS
    //-----------------------------------------------------------------------------

    // Guarda el texto del hashtag (ej: "importante", "idea", etc.)
    private String texto;

    //-----------------------------------------------------------------------------
    // CONSTRUCTOR
    //-----------------------------------------------------------------------------

    /* Al crear un hashtag, se pasa directamente el texto (sin #).
     * El constructor es simple porque esta clase actúa como contenedor. */
    public Hashtag(String texto) {
        this.texto = texto;
    }

    //-----------------------------------------------------------------------------
    // MÉTODOS
    //-----------------------------------------------------------------------------

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    /* Alias de getTexto().
     * Se mantiene este método por si lo usamos con ese nombre en otras partes del código */
    public String getNombre() {
        return texto;
    }
}
