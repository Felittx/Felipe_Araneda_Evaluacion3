package com.ubb.IS.Controladores;

import com.ubb.IS.Modelos.Cotizacion;
import com.ubb.IS.Modelos.Venta;
import com.ubb.IS.Servicios.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cotizaciones")
public class CotizacionController {
    @Autowired
    private CotizacionService cotizacionService;

    @GetMapping
    public List<Cotizacion> obtenerCotizaciones() {
        return cotizacionService.obtenerCotizaciones();
    }

    @GetMapping("/{id}")
    public Optional<Cotizacion> obtenerCotizacionPorId(@PathVariable Integer id) {
        return cotizacionService.obtenerCotizacionPorId(id);
    }

    @PostMapping
    public Cotizacion crearCotizacion(@RequestBody Cotizacion cotizacion) {
        return cotizacionService.crearCotizacion(cotizacion);
    }

    @PostMapping("/{id}/confirmar-venta")
    public Venta confirmarVenta(@PathVariable Integer id) {
        return cotizacionService.confirmarVenta(id);
    }
}
