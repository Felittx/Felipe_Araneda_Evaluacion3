package com.ubb.IS.Repositorios;

import com.ubb.IS.Modelos.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {
}
