package com.ubb.IS.Servicios;

import com.ubb.IS.Modelos.Estado;
import com.ubb.IS.Modelos.Mueble;
import com.ubb.IS.Repositorios.MuebleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MuebleService {
    @Autowired
    MuebleRepository muebleRepository;

    public ArrayList<Mueble> obtenerMuebles(){
        return(ArrayList<Mueble>) muebleRepository.findAll();
    }

    public Mueble guardarMueble(Mueble mueble) {
        if (mueble.getEstado() == null) {
            mueble.setEstado(Estado.ACTIVO);
        }
        return muebleRepository.save(mueble);
    }

    public Optional<Mueble> obtenerPorID(Integer id){
        return muebleRepository.findById(id);
    }

    public Mueble actualizarPorID(Mueble request, Integer id) {
        Mueble mueble = muebleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mueble no encontrado"));

        mueble.setNombre_mueble(request.getNombre_mueble());
        mueble.setPrecio_base(request.getPrecio_base());
        mueble.setStock(request.getStock());

        return muebleRepository.save(mueble);
    }

    public void desactivarMueble(Integer id) {
        Mueble mueble = muebleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El mueble no fue encontrado"));

        mueble.setEstado(Estado.INACTIVO);
        muebleRepository.save(mueble);
    }

    public boolean deleteMueble(Integer id) {
        try {
            muebleRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
