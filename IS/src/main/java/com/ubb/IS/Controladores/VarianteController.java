package com.ubb.IS.Controladores;

import com.ubb.IS.Modelos.Variante;
import com.ubb.IS.Servicios.VarianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/variantes")
@RestController
public class VarianteController {
    @Autowired
    private VarianteService varianteService;

    @GetMapping
    public List<Variante> obtenerVariaciones() {
        return varianteService.obtenerVariantes();
    }

    @GetMapping("/{id}")
    public Optional<Variante> obtenerPorID(@PathVariable Integer id) {
        return varianteService.obtenerPorID(id);
    }

    @PostMapping
    public Variante guardarVariacion(@RequestBody Variante variante) {
        return varianteService.guardarVariante(variante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVariacion(@PathVariable Integer id) {
        varianteService.eliminarVariante(id);
        return ResponseEntity.noContent().build();
    }
}
