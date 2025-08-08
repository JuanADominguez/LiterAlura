package com.alura.literalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.literalura.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    boolean existsByTitulo(String titulo);
    List<Libro> findByIdioma(String idioma);
	List<Libro> findByTituloContainingIgnoreCase(String titulo);
	List<Libro> findByAutorNombreContainingIgnoreCase(String nombreAutor);
	Optional<Libro> findFirstByTitulo(String titulo);
}