package com.alura.literalura.client;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class GutendexClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String BASE_URL = "https://gutendex.com/books?search=";

    public Optional<Libro> buscarLibro(String titulo) {
        try {
            String url = BASE_URL + titulo;
            String json = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(json);
            JsonNode resultados = root.get("results");

            if (resultados.isEmpty()) return Optional.empty();

            JsonNode libroJson = resultados.get(0);
            String tituloLibro = libroJson.get("title").asText();
            String idioma = libroJson.get("languages").get(0).asText();
            int descargas = libroJson.get("download_count").asInt();

            JsonNode autorJson = libroJson.get("authors").get(0);
            String nombreAutor = autorJson.get("name").asText();
            Integer nacimiento = autorJson.get("birth_year").isNull() ? null : autorJson.get("birth_year").asInt();
            Integer muerte = autorJson.get("death_year").isNull() ? null : autorJson.get("death_year").asInt();

            Autor autor = new Autor(nombreAutor, nacimiento, muerte);
            Libro libro = new Libro(tituloLibro, idioma, descargas, autor);

            return Optional.of(libro);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}