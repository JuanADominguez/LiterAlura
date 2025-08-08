package com.alura.literalura.console;

import com.alura.literalura.client.GutendexClient;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Menu implements CommandLineRunner {

	private final LibroService libroService;
	private final AutorService autorService;
	private final GutendexClient gutendexClient;
	private final Scanner scanner = new Scanner(System.in);

	@Autowired
	public Menu(LibroService libroService, GutendexClient gutendexClient, AutorService autorService) {
		this.libroService = libroService;
		this.gutendexClient = gutendexClient;
		this.autorService = autorService;
	}

	@Override
	public void run(String... args) {
		boolean salir = false;

		while (!salir) {
			System.out.println("\n📚 Menú Principal 📚");
			System.out.println("1. Listar todos los libros registrados");
			// ListarAutores
			System.out.println("2. Listar todos los autores registrados");
			// LIstarAutoresVivosxAño
			System.out.println("3. Listar autores vivos en determinado año");
			System.out.println("4. Buscar libro por título");
			System.out.println("5. Buscar libros por autor");
			System.out.println("6. Cantidad de libros en inglés");
			System.out.println("7. Cantidad de libros en francés");
			// System.out.println("8. Agregar nuevo libro manualmente");
			// System.out.println("9. Eliminar libro guardado por ID");
			System.out.println("0. Salir");
			System.out.print("Seleccione una opción: ");

			int opcion;
			try {
				opcion = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("⚠️ Entrada inválida. Debe ser un número.");
				continue;
			}

			switch (opcion) {
			case 1 -> listarLibros();
			case 2 -> listarAutores();
			case 3 -> listarAutoresVivos();
			case 4 -> buscarPorTitulo();
			case 5 -> buscarPorAutor();
			case 6 -> cantidadIngles();
			case 7 -> cantidadFrances();
			// No piden
			// case 8 -> agregarLibro();
			// case 9 -> eliminarLibro();
			case 0 -> {
				System.out.println("👋 Hasta luego!");
				salir = true;
			}
			default -> System.out.println("⚠️ Opción no válida. Intente de nuevo.");
			}
		}
	}

	private void cantidadFrances() {

		List<Libro> obtenerCantidadLibros = obtenerCantidadLibros("fr");
		mostrarLibros(obtenerCantidadLibros, "Francés");

	}

	private void cantidadIngles() {
		List<Libro> obtenerCantidadLibros = obtenerCantidadLibros("en");

		mostrarLibros(obtenerCantidadLibros, "Inglés");

	}

	private void mostrarLibros(List<Libro> librosxIdioma, String idioma) {

		if (CollectionUtils.isEmpty(librosxIdioma)) {
			System.out.println("No existen libros con ese idioma");
		} else {
			int obtenerCantidadLibros = librosxIdioma.size();
			String txtLibro = (obtenerCantidadLibros > 1) ? "libros" : "libro";

			if (obtenerCantidadLibros > 0) {
				System.out.println("Hay " + obtenerCantidadLibros + " " + txtLibro + " en " + idioma);
			}
			librosxIdioma.forEach(System.out::println);
		}
	}

	private List<Libro> obtenerCantidadLibros(String idioma) {
		// obtener cantidad de libros en el idioma dato.
		List<Libro> librosxIdioma = libroService.obtenerTodos().stream()
				.filter(e -> e.getIdioma() != null && e.getIdioma().equalsIgnoreCase(idioma))
				.collect(Collectors.toList());

		return librosxIdioma;
	}

	private void listarAutoresVivos() {
		// int year = 1915;
		boolean salir = false;
		System.out.print("Ingrese el año a buscar o 0 para regresar al menu: ");
		Scanner autorScanner = new Scanner(System.in);
		while (!salir) {
			if (autorScanner.hasNextInt()) {
				Integer year = autorScanner.nextInt();
				if (year != 0) {
					List<Autor> autor = autorService.autoresVivosEn(year);
					if (autor.isEmpty()) {
						System.out.println("📭 No hay autores registrados.");
					} else {
						autor.forEach(System.out::println);

					}

				}
				salir = true;
			}
		}
		
	}

	private void listarAutores() {
		List<Autor> autor = autorService.listarAutores();
		if (autor.isEmpty()) {
			System.out.println("📭 No hay autores registrados.");
		} else {
			autor.forEach(System.out::println);
		}
	}

	private void listarLibros() {
		List<Libro> libros = libroService.obtenerTodos();
		if (libros.isEmpty()) {
			System.out.println("📭 No hay libros registrados.");
		} else {
			libros.forEach(System.out::println);
		}
	}

	private void buscarPorTitulo() {
		System.out.print("Ingrese el título a buscar: ");
		String titulo = scanner.nextLine();

		Optional<Libro> libroOptional = gutendexClient.buscarLibro(titulo);

		if (libroOptional.isPresent()) {
			Libro libro = libroOptional.get();
			libroService.guardarLibro(libro);

		} else {
			System.out.println("⚠️ No se encontró ningún libro con ese título en la API.");
		}
	}

	private void buscarPorAutor() {
		System.out.print("Ingrese el nombre del autor: ");
		String autor = scanner.nextLine();
		libroService.buscarPorAutor(autor).forEach(System.out::println);
	}

//	private void agregarLibro() {
//		System.out.print("Título del libro: ");
//		String titulo = scanner.nextLine();
//		System.out.print("Nombre del autor: ");
//		String autorNombre = scanner.nextLine();
//
//		Libro libro = new Libro();
//		libro.setTitulo(titulo);
//		libro.setAutor(new Autor(autorNombre, null, null));
//
//		libroService.guardarLibro(libro);
//		System.out.println("✅ Libro guardado manualmente.");
//	}
//
//	private void eliminarLibro() {
//		System.out.print("Ingrese el ID del libro a eliminar: ");
//		try {
//			Long id = Long.parseLong(scanner.nextLine());
//			if (libroService.existePorId(id)) {
//				libroService.eliminarLibro(id);
//				// duplicado System.out.println("🗑️ Libro eliminado.");
//			} else {
//				System.out.println("❌ No se encontró un libro con ese ID.");
//			}
//		} catch (NumberFormatException e) {
//			System.out.println("⚠️ Entrada inválida. Debe ser un número.");
//		}
//	}
}