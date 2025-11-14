package com.ubb.IS.Repositorios;

import com.ubb.IS.Modelos.Variante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VarianteRepository extends JpaRepository<Variante, Integer> {
}
