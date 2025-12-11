package com.ubb.IS.Controladores;

import com.ubb.IS.Modelos.Mueble;
import com.ubb.IS.Servicios.MuebleService;
import com.ubb.IS.Servicios.VarianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InterfazController {
    @Autowired
    private MuebleService muebleService;

    @Autowired
    private VarianteService varianteService;

    @GetMapping("/admin")
    public String vistaAdmin(Model model) {
        model.addAttribute("mueble", new Mueble());
        model.addAttribute("listaMuebles", muebleService.obtenerMuebles());
        return "admin";
    }


    @PostMapping("/admin/guardar")
    public String guardarMueble(@ModelAttribute Mueble mueble) {
        muebleService.guardarMueble(mueble);
        return "redirect:/admin"; // Recarga la página
    }

    @GetMapping("/catalogo")//vista para el cliente
    public String vistaCatalogo(Model model) {
        model.addAttribute("muebles", muebleService.obtenerMuebles());
        model.addAttribute("variantes", varianteService.obtenerVariantes());
        return "catalogo";
    }
}
