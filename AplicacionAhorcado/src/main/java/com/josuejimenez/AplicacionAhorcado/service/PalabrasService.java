package com.josuejimenez.AplicacionAhorcado.service;

import com.josuejimenez.AplicacionAhorcado.model.Palabras;

import java.util.List;

public interface PalabrasService {
    List<Palabras> getAllPalabras();
    Palabras getPalabrasById(Integer id);
    Palabras savePalabras(Palabras palabras);
    Palabras updatePalabras(Integer id, Palabras palabras);
    void deletePalabras(Integer id);
    void deletePalabras2();
}
