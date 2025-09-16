package com.josuejimenez.AplicacionAhorcado.service;

import com.josuejimenez.AplicacionAhorcado.controller.ValidacionCorreo;
import com.josuejimenez.AplicacionAhorcado.model.Palabras;
import com.josuejimenez.AplicacionAhorcado.model.Usuarios;
import com.josuejimenez.AplicacionAhorcado.repository.PalabrasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PalabrasServiceImplements implements PalabrasService{

    private final PalabrasRepository palabrasRepository;

    public PalabrasServiceImplements(PalabrasRepository palabrasRepository) {
        this.palabrasRepository = palabrasRepository;
    }


    @Override
    public List<Palabras> getAllPalabras() {
        return palabrasRepository.findAll();
    }

    @Override
    public Palabras getPalabrasById(Integer id) {
        return palabrasRepository.findById(id).orElse(null);
    }

    @Override
    public Palabras savePalabras(Palabras palabras) {
        return palabrasRepository.save(palabras);
    }

    @Override
    public Palabras updatePalabras(Integer id, Palabras palabras) {
        return palabrasRepository.save(palabras);
    }

    @Override
    public void deletePalabras(Integer id) {
        palabrasRepository.deleteById(id);
    }
}
