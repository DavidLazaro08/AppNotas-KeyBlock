package modelo;

public class Hashtag {
    private String texto;

    public Hashtag(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public String toString() {
        return "#" + texto;
    }
}
