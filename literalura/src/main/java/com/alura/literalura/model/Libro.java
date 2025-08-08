package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer descargas;

    @ManyToOne
    private Autor autor;

    public Libro() {}

    public Libro(String titulo, String idioma, Integer descargas, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargas = descargas;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

	@Override
	public String toString() {
//		return "Libro [id=" + id + ", titulo=" + titulo + ", idioma=" + idioma + ", descargas=" + descargas + ", autor="
//				+ autor + "]";
		return id+ " - "+ titulo + (((autor!= null) ? " - " + autor.getNombre() : "" )+ " - Descargado "+descargas+" veces");
	}
    
    
    
}