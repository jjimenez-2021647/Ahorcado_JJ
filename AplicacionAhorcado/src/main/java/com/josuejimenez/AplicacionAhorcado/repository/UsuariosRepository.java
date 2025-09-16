package com.josuejimenez.AplicacionAhorcado.repository;

import com.josuejimenez.AplicacionAhorcado.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
}
