package com.ubb.IS.Controladores;

import com.ubb.IS.Modelos.Mueble;
import com.ubb.IS.Servicios.MuebleService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/muebles")
public class MuebleController {
    @Autowired
    private MuebleService muebleService;

    @GetMapping
    public ArrayList<Mueble> getMuebles(){
        return this.muebleService.obtenerMuebles();
    }

    @PostMapping
    public Mueble guardarMueble(@RequestBody Mueble mueble){
        return this.muebleService.guardarMueble(mueble);
    }

    @GetMapping(path = "/{id}")
    public Optional<Mueble> obtenerMueblePorID(@PathVariable("id") Integer id){
        return this.muebleService.obtenerPorID(id);
    }

    @PutMapping(path = "/{id}")
    public Mueble actualizarMueblePorID(@RequestBody Mueble request, @PathVariable("id") Integer id){
        return this.muebleService.actualizarPorID(request, id);
    }

    @PatchMapping(path = "/{id}/desactivar")
    public ResponseEntity<String> desactivarMueble(@PathVariable("id") Integer id) {
        try {
            muebleService.desactivarMueble(id);
            return ResponseEntity.ok("El mueble fue desactivado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteMuebleById(@PathVariable("id") Integer id){
        boolean exito = this.muebleService.deleteMueble(id);
        if(exito){
            return ResponseEntity.ok("El mueble fue eliminado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Hubo un error al  intentar eliminar el mueble");
    }
}
