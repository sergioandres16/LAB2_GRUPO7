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

@Controller
@RequestMapping("/marca")
public class MarcaController {

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    TrabajadorRepository trabajadorRepository;

    @GetMapping("/listar")
    public String listarMarca(Model model){
        List<Marca> lisOfMarcas = marcaRepository.findAll();
        model.addAttribute("lisOfMarcas", lisOfMarcas);
        return "marca/lista";
    }

    @GetMapping("/crear")
    public String nuevaMarca(){
        return "marca/nuevaMarca";
    }

    @GetMapping("/editar")
    public String editarMarca(@RequestParam("id") int id, Model model) {
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if (marcaOptional.isPresent()) {
            Marca marca = marcaOptional.get();
            model.addAttribute("marca", marca);

            List<Trabajador> trabajadorList = trabajadorRepository.findAllByIdmarca(id);
            model.addAttribute("trabajadorList", trabajadorList);
            return "marca/editarMarca";
        } else {
            return "redirect:/marca/listar";
        }
    }

    @PostMapping("/guardar")
    public String guardarMarca(Marca marca, RedirectAttributes attributes){
        marcaRepository.save(marca);
        return "redirect:/marca/listar";
    }

    @GetMapping("/borrar")
    public String borrarMarca(@RequestParam("id") int id, RedirectAttributes attributes){
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if(marcaOptional.isPresent()){
            Marca marca = marcaOptional.get();
            marcaRepository.deleteById(id);
        }
        return "redirect:/marca/listar";
    }

}
