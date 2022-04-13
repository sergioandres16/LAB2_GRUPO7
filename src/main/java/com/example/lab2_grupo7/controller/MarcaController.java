package com.example.lab2_grupo7.controller;

import com.example.lab2_grupo7.entity.Marca;
import com.example.lab2_grupo7.entity.Trabajador;
import com.example.lab2_grupo7.repository.MarcaRepository;
import com.example.lab2_grupo7.repository.TrabajadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/marcas")
public class MarcaController {

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    TrabajadorRepository trabajadorRepository;

    @GetMapping("/lista")
    public String listarMarca(Model model){
        List<Marca> marcaList = marcaRepository.findAll();
        model.addAttribute("lista", marcaList);
        return "marcas/list";
    }

    @GetMapping("/crear")
    public String crearMarca(){
        return "marcas/create";
    }

    @GetMapping("/editar")
    public String editarMarca(@RequestParam("id") int id, Model model) {
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if (marcaOptional.isPresent()) {
            Marca marca = marcaOptional.get();
            model.addAttribute("marca", marca);

            List<Trabajador> list = trabajadorRepository.buscarPorSede(id);
            model.addAttribute("trabajadores", list);
            model.addAttribute("marcas", marca);
            return "marcas/edit";
        } else {
            return "redirect:/marcas/lista";
        }
    }

    @PostMapping("/guardar")
    public String guardarMarca(Marca marca, RedirectAttributes attributes){
        if (marca.getId() == null) {
            attributes.addFlashAttribute("msg", "Marca creada exitosamente.");
        } else {
            attributes.addFlashAttribute("msg", "Marca editada exitosamente.");
        }
        marcaRepository.save(marca);
        return "redirect:/marcas/lista";
    }

    @GetMapping("/borrar")
    public String borrarMarca(@RequestParam("id") int id, RedirectAttributes attributes){
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if(marcaOptional.isPresent()){
            Marca marca = marcaOptional.get();
            marcaRepository.deleteById(id);
            attributes.addFlashAttribute("msg", "Marca borrada exitosamente.");

        }
        return "redirect:/marcas/lista";
    }

}
