package com.ubb.IS;
import com.ubb.IS.Excepciones.StockException;
import com.ubb.IS.Modelos.*;
import com.ubb.IS.Repositorios.CotizacionRepository;
import com.ubb.IS.Repositorios.MuebleRepository;
import com.ubb.IS.Repositorios.VarianteRepository;
import com.ubb.IS.Repositorios.VentaRepository;
import com.ubb.IS.Servicios.CotizacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CotizacionServiceTest {

    @Mock
    private CotizacionRepository cotizacionRepository;
    @Mock
    private MuebleRepository muebleRepository;
    @Mock
    private VarianteRepository varianteRepository;
    @Mock
    private VentaRepository ventaRepository;
    @InjectMocks
    private CotizacionService cotizacionService;


    @Test
    void testCrearCotizacion() {
        Mueble mueble = new Mueble();
        mueble.setId_mueble(1);
        mueble.setPrecio_base(50000);

        Variante variante = new Variante();
        variante.setId_variante(2);
        variante.setValor_adicional(10000);

        DetalleCotizacion detalle = new DetalleCotizacion();
        detalle.setMueble(mueble);
        detalle.setVariante(variante);
        detalle.setCantidad(2);

        Cotizacion cotizacionInput = new Cotizacion();
        Set<DetalleCotizacion> detalles = new HashSet<>();
        detalles.add(detalle);
        cotizacionInput.setDetalles(detalles);

        when(muebleRepository.findById(1)).thenReturn(Optional.of(mueble));
        when(varianteRepository.findById(2)).thenReturn(Optional.of(variante));

        when(cotizacionRepository.save(any(Cotizacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cotizacion resultado = cotizacionService.crearCotizacion(cotizacionInput);

        assertNotNull(resultado);
        //total esperado = 2*(50000 + 10000) = 120000
        assertEquals(120000, resultado.getTotal());
        //precio guardado = 50000 + 10000 = 60000
        assertEquals(60000, detalle.getPrecio_unitario());
    }


    @Test
    void testVenta_StockInsuficienten() {
        Mueble mueble = new Mueble();
        mueble.setId_mueble(1);
        mueble.setNombre_mueble("Silla Gamer");
        mueble.setStock(5);

        DetalleCotizacion detalle = new DetalleCotizacion();
        detalle.setMueble(mueble);
        detalle.setCantidad(10);

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId_cotizacion(1);
        cotizacion.setEstado(EstadoCotizacion.INCOMPLETA);
        cotizacion.setDetalles(Set.of(detalle));

        when(cotizacionRepository.findById(1)).thenReturn(Optional.of(cotizacion));


        StockException ex = assertThrows(StockException.class, () -> {
            cotizacionService.confirmarVenta(1);
        });

        assertEquals("Stock insuficiente para: Silla Gamer", ex.getMessage());

        verify(muebleRepository, never()).save(any());
        verify(ventaRepository, never()).save(any());
    }

    @Test
    void testVentaExitosa() {
        Mueble mueble = new Mueble();
        mueble.setId_mueble(1);
        mueble.setStock(20);

        DetalleCotizacion detalle = new DetalleCotizacion();
        detalle.setMueble(mueble);
        detalle.setCantidad(5);

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId_cotizacion(1);
        cotizacion.setEstado(EstadoCotizacion.INCOMPLETA);
        cotizacion.setDetalles(Set.of(detalle));

        when(cotizacionRepository.findById(1)).thenReturn(Optional.of(cotizacion));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Venta ventaResultado = cotizacionService.confirmarVenta(1);

        assertNotNull(ventaResultado);

        // stock debio haber sido descontado(20 - 5 = 15)
        assertEquals(15, mueble.getStock());

        assertEquals(EstadoCotizacion.COMPLETA, cotizacion.getEstado());

        verify(muebleRepository, times(1)).save(mueble);
        verify(cotizacionRepository, times(1)).save(cotizacion);
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }
}