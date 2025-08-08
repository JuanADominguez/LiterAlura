package com.alura.literalura.service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    @Autowired
    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    public void guardarLibro(Libro libro) {
        
    	//Buscar si el libro ya existe.
    	Optional<Libro> lExistente = libroRepository.findFirstByTitulo(libro.getTitulo());
    	if (!lExistente.isPresent())
    	{
    		// Buscar autor existente por nombre
            Optional<Autor> existente = autorRepository.findByNombre(libro.getAutor().getNombre());

            // Usar el autor existente o guardar el nuevo
            Autor autor = existente.orElseGet(() -> autorRepository.save(libro.getAutor()));

            // Asociar el autor correcto al libro
            libro.setAutor(autor);

            // Guardar el libro
            libroRepository.save(libro);
            System.out.println("✅ Libro guardado exitosamente:");
            System.out.println(libro);
    	}
    
    }


    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Libro> buscarPorAutor(String nombreAutor) {
        return libroRepository.findByAutorNombreContainingIgnoreCase(nombreAutor);
    }

    //public void eliminarLibro(Long id) {
    //    libroRepository.deleteById(id);
    //}
    public void eliminarLibro(Long id) {
        Scanner scanner = new Scanner(System.in);

        if (!libroRepository.existsById(id)) {
            System.out.println("❌ No se encontró un libro con ese ID.");
            return;
        }

        System.out.print("¿Está seguro que desea eliminar el libro con ID " + id + "? (pulse SI para borrar, cualquier otra tecla para cancelar): ");
        String confirmacion = scanner.nextLine();

        if (confirmacion.equalsIgnoreCase("SI")) {
            libroRepository.deleteById(id);
            System.out.println("🗑️ Libro eliminado.");
        } else {
            System.out.println("✅ Operación cancelada. El libro no fue eliminado.");
        }
    }
    
    public boolean existePorId(Long id) {
        return libroRepository.existsById(id);
    }


}