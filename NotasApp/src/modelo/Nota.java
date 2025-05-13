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

    public Nota() {}
}