package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> autoresVivosEn(int year) {
        return autorRepository.findByAñoNacimientoLessThanEqualAndAñoMuerteGreaterThanEqual(year, year);
    }

	
}