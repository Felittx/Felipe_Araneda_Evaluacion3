package com.ubb.IS.Servicios;

import com.ubb.IS.Modelos.Variante;
import com.ubb.IS.Repositorios.VarianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VarianteService {
    @Autowired
    private VarianteRepository varianteRepository;

    public List<Variante> obtenerVariantes() {
        return varianteRepository.findAll();
    }

    public Optional<Variante> obtenerPorID(Integer id) {
        return varianteRepository.findById(id);
    }

    public Variante guardarVariante(Variante variacion) {
        return varianteRepository.save(variacion);
    }

    public void eliminarVariante(Integer id) {
        varianteRepository.deleteById(id);
    }
}
