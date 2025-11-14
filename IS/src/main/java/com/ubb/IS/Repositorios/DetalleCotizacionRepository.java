package com.ubb.IS.Repositorios;

import com.ubb.IS.Modelos.DetalleCotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCotizacionRepository extends JpaRepository<DetalleCotizacion, Integer> {
}
