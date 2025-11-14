package com.ubb.IS.Servicios;

import com.ubb.IS.Excepciones.StockException;
import com.ubb.IS.Modelos.*;
import com.ubb.IS.Repositorios.CotizacionRepository;
import com.ubb.IS.Repositorios.MuebleRepository;
import com.ubb.IS.Repositorios.VarianteRepository;
import com.ubb.IS.Repositorios.VentaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CotizacionService {
    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private MuebleRepository muebleRepository;

    @Autowired
    private VarianteRepository varianteRepository;

    @Autowired
    private VentaRepository ventaRepository;

    public List<Cotizacion> obtenerCotizaciones() {
        return cotizacionRepository.findAll();
    }

    public Optional<Cotizacion> obtenerCotizacionPorId(Integer id) {
        return cotizacionRepository.findById(id);
    }


    @Transactional
    public Cotizacion crearCotizacion(Cotizacion cotizacion) {

        cotizacion.setFecha(LocalDate.now());
        cotizacion.setEstado(EstadoCotizacion.INCOMPLETA);

        float totalCotizacion = 0;

        for (DetalleCotizacion detalle : cotizacion.getDetalles()) {
            Mueble mueble = muebleRepository.findById(detalle.getMueble().getId_mueble())
                    .orElseThrow(() -> new RuntimeException("Mueble no encontrado"));

            Variante variante = varianteRepository.findById(detalle.getVariante().getId_variante())
                    .orElseThrow(() -> new RuntimeException("Variación no encontrada"));

            detalle.setMueble(mueble);
            detalle.setVariante(variante);
            detalle.setCotizacion(cotizacion);

            float precioUnitario = mueble.getPrecio_base() + variante.getValor_adicional();
            detalle.setPrecio_unitario(precioUnitario);
            totalCotizacion =  totalCotizacion + (precioUnitario * detalle.getCantidad());
        }

        cotizacion.setTotal(totalCotizacion);
        return cotizacionRepository.save(cotizacion);
    }


    @Transactional
    public Venta confirmarVenta(Integer idCotizacion) {

        Cotizacion cotizacion = cotizacionRepository.findById(idCotizacion)
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar la cotizacion"));

        if (cotizacion.getEstado() == EstadoCotizacion.COMPLETA) {
            throw new RuntimeException("Ocurrio un problema");
        }


        for (DetalleCotizacion detalle : cotizacion.getDetalles()) {

            if (detalle.getMueble().getStock() < detalle.getCantidad()) {
                throw new StockException("Stock insuficiente para: " + detalle.getMueble().getNombre_mueble());
            }
        }

        for (DetalleCotizacion detalle : cotizacion.getDetalles()) {
            Mueble mueble = detalle.getMueble();
            int nuevoStock = mueble.getStock() - detalle.getCantidad();
            mueble.setStock(nuevoStock);
            muebleRepository.save(mueble);
        }

        cotizacion.setEstado(EstadoCotizacion.COMPLETA);
        cotizacionRepository.save(cotizacion);

        Venta venta = new Venta();
        venta.setCotizacion(cotizacion);
        venta.setFecha_venta(LocalDate.now());

        return ventaRepository.save(venta);
    }
}
