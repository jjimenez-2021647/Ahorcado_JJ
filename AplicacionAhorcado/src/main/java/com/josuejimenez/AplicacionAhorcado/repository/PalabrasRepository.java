package com.josuejimenez.AplicacionAhorcado.repository;

import com.josuejimenez.AplicacionAhorcado.model.Palabras;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PalabrasRepository extends JpaRepository<Palabras, Integer> {
}
