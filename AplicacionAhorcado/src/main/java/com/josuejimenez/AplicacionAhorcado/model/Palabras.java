package com.josuejimenez.AplicacionAhorcado.model;

import jakarta.persistence.*;

@Entity
@Table(name="Palabras")
public class Palabras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="codigo_palabra")
    private Integer id;

    @Column(name="palabra")
    private String palabra;

    @Column(name="pista")
    private String pista;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getPista() {
        return pista;
    }

    public void setPista(String pista) {
        this.pista = pista;
    }
}
