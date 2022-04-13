package com.example.lab2_grupo7.controller;

import com.example.lab2_grupo7.entity.Inventario;
import com.example.lab2_grupo7.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    InventarioRepository inventarioRepository;

    @GetMapping(value={ "/lista",""})
    public String listaInventario (Model model){
        List<Inventario> listaInventario = inventarioRepository.findAll();
        model.addAttribute("listaInventario",listaInventario);
        return "inventario/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoInventario(){
        return "inventario/nuevo";
    }

    @PostMapping(value = {"/save"})
    public String guardarInventario(Inventario inventario, RedirectAttributes attr) {
        Optional<Inventario> optionalInventario = inventarioRepository.findById(inventario.getIdinventario());
        if (optionalInventario.isEmpty()){
            inventarioRepository.save(inventario);
        }
        //else { // error
        //    attr.addFlashAttribute("msg", "hola");
        //}
        return "redirect:/inventario/lista";
    }

    @GetMapping(value = {"/edit"})
    public String editarInventario(Model model,
                                 @RequestParam("id") Integer idinventario) {

        Optional<Inventario> optionalInventario = inventarioRepository.findById(idinventario);

        if (optionalInventario.isPresent()) {
            //List<Region> listaRegion = regionRepository.findAll();
            //model.addAttribute("listaRegion", listaRegion);

            Inventario inventario = optionalInventario.get();
            model.addAttribute("inventario", inventario);

            return "inventario/editar";
        } else {
            return "redirect:/inventario/lista";
        }
    }

    @PostMapping(value = {"/update"})
    public String updateInventario(Inventario inventario) {
        inventarioRepository.save(inventario);
        return "redirect:/inventario";
    }


}
