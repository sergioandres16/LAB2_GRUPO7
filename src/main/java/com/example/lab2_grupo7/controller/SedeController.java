package com.example.lab2_grupo7.controller;

import com.example.lab2_grupo7.entity.Sede;
import com.example.lab2_grupo7.repository.SedeRepository;
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
@RequestMapping("/sedes")
public class SedeController {
    SedeRepository sedeRepository;
    
    @GetMapping("/lista")
    public String listarSedes(Model model){
        List<Sede> sedeList = sedeRepository.findAll();
        model.addAttribute("lista", sedeList);
        return "sedes/list";
    }

    @GetMapping("/crear")
    public String crearSede(){
        return "sedes/create";
    }

    @PostMapping("/guardar")
    public String guardarSede(Sede sede, RedirectAttributes redirectAttributes){
        sedeRepository.save(sede);
        return "redirect:/sedes/list";
    }

    @GetMapping("/editar")
    public String editarSede(Model model, @RequestParam("id") int id){
        Optional<Sede> sedeOptional = sedeRepository.findById(id);
        if (sedeOptional.isPresent()){
            Sede sede = sedeOptional.get();
            model.addAttribute("sede",sede);
            return "sedes/edit";
        }else{
            return "redirect:/sedes/list";
        }

    }

}
