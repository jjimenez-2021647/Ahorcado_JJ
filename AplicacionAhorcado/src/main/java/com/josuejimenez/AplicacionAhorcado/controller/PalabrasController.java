package com.josuejimenez.AplicacionAhorcado.controller;

import com.josuejimenez.AplicacionAhorcado.model.Palabras;
import com.josuejimenez.AplicacionAhorcado.model.Usuarios;
import com.josuejimenez.AplicacionAhorcado.service.PalabrasService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/palabras")
public class PalabrasController {
    private final PalabrasService palabrasService;

    public PalabrasController(PalabrasService palabrasService) {
        this.palabrasService = palabrasService;
    }

    @GetMapping
    public List<Palabras> getAllPalabras(){
        return palabrasService.getAllPalabras();
    }

    @GetMapping("/{id}")
    public Palabras getPalabrasById(@PathVariable Integer id){
        return palabrasService.getPalabrasById(id);
    }

    @PostMapping
    public String createPalabras(@RequestBody Palabras palabras){
        try{
            Palabras result = palabrasService.savePalabras(palabras);
            if ("Mensaje-Palabra".equals(result.getPalabra())) {
                return "La palabra ya está registrada";
            }
            if ("Mensaje-Pista".equals(result.getPista())) {
                return "La pista ya está registrada";
            }
            return "Palabra agregada exitosamente";
        }catch (CorreoInvalido e){
            return e.getMessage();
        }
    }

    @PutMapping("/{id}")
    public String updatePalabras(@PathVariable Integer id, @RequestBody Palabras palabras){
        try{
            Palabras result = palabrasService.updatePalabras(id, palabras);
            if ("Mensaje-Palabra".equals(result.getPalabra())) {
                return "La palabra ya está registrada";
            }
            if ("Mensaje-Pista".equals(result.getPista())) {
                return "La pista ya está registrada";
            }
            return "Palabra actualizada correctamente";
        }catch (CorreoInvalido e){
            return e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public String deletePalabras(@PathVariable Integer id){
        Palabras palabras = palabrasService.getPalabrasById(id);
        if (palabras == null) {
            return "Error: La palabra no existe o ya fue eliminado";
        }
        palabrasService.deletePalabras(id);
        return "Palabra eliminada correctamente";
    }
}
