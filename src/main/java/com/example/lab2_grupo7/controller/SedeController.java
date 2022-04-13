package com.example.lab2_grupo7.controller;

import com.example.lab2_grupo7.entity.Sede;
import com.example.lab2_grupo7.entity.Trabajador;
import com.example.lab2_grupo7.repository.SedeRepository;
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
@RequestMapping("/sedes")
public class SedeController {

    @Autowired
    SedeRepository sedeRepository;

    @Autowired
    TrabajadorRepository trabajadorRepository;

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
    public String guardarSede(Sede sede, RedirectAttributes attributes, @RequestParam("action") int action){
        if (action == 1){
            attributes.addFlashAttribute("msg", "Sede eliminada exitosamente");
        }else if (action == 2){
            attributes.addFlashAttribute("msg", "Sede eliminada exitosamente");
        }
        sedeRepository.save(sede);
        return "redirect:/sedes/lista";
    }

    @GetMapping("/editar")
    public String editarSede(Model model, @RequestParam("id") int id){
        Optional<Sede> sedeOptional = sedeRepository.findById(id);
        if (sedeOptional.isPresent()){
            Sede sede = sedeOptional.get();
            List<Trabajador> list = trabajadorRepository.buscarPorSede(id);
            model.addAttribute("trabajadores",list);
            model.addAttribute("sede",sede);
            return "sedes/edit";
        }else{
            return "redirect:/sedes/lista";
        }
    }

    //Falta modificar por que hay trabajadores dentro de ellas, primero se deberia eliminar los trabajadores
    @GetMapping("/borrar")
    public String borrarSede(RedirectAttributes attributes, @RequestParam("id") int id){
        Optional<Sede> optionalSede = sedeRepository.findById(id);

        if (optionalSede.isPresent()) {
            sedeRepository.deleteById(id);
            attributes.addFlashAttribute("msg", "Sede eliminada exitosamente");
        }
        return "redirect:/shipper/list";
    }

}
