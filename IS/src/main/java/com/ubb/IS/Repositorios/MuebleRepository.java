package com.ubb.IS.Repositorios;

import com.ubb.IS.Modelos.Mueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuebleRepository extends JpaRepository<Mueble, Integer> {
}
