package com.ubb.IS;
import com.ubb.IS.Modelos.Estado;
import com.ubb.IS.Modelos.Mueble;
import com.ubb.IS.Repositorios.MuebleRepository;
import com.ubb.IS.Servicios.MuebleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MuebleServiceTest {
    @Mock
    private MuebleRepository muebleRepository;
    @InjectMocks
    private MuebleService muebleService;

    @Test
    void testObtenerMuebles() {//test obtener muebles
        List<Mueble> listaMock = new ArrayList<>();
        Mueble mueble1 = new Mueble();
        mueble1.setId_mueble(1);
        mueble1.setNombre_mueble("Silla");
        listaMock.add(mueble1);

        when(muebleRepository.findAll()).thenReturn(listaMock);


        List<Mueble> resultado = muebleService.obtenerMuebles();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Silla", resultado.get(0).getNombre_mueble());
    }

    @Test
    void testGuardarMueble() {//test guardar mueble
        Mueble muebleAEnviar = new Mueble();
        muebleAEnviar.setNombre_mueble("Mesa prueba");

        Mueble muebleDevuelto = new Mueble();
        muebleDevuelto.setId_mueble(1);
        muebleDevuelto.setNombre_mueble("Mesa prueba");
        muebleDevuelto.setEstado(Estado.ACTIVO);


        when(muebleRepository.save(any(Mueble.class))).thenReturn(muebleDevuelto);

        Mueble resultado = muebleService.guardarMueble(muebleAEnviar);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId_mueble());
        assertEquals("Mesa prueba", resultado.getNombre_mueble());

        assertEquals(Estado.ACTIVO, resultado.getEstado());
    }

    @Test
    void testDesactivarMueble() {//test desactivar mueble
        Mueble muebleExistente = new Mueble();
        muebleExistente.setId_mueble(1);
        muebleExistente.setNombre_mueble("Silla");
        muebleExistente.setEstado(Estado.ACTIVO);

        when(muebleRepository.findById(1)).thenReturn(Optional.of(muebleExistente));

        muebleService.desactivarMueble(1);

        assertEquals(Estado.INACTIVO, muebleExistente.getEstado());

        verify(muebleRepository, times(1)).save(muebleExistente);
    }
}
