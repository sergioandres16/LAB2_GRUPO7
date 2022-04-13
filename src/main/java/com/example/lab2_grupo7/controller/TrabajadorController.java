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
@RequestMapping("/trabajadores")
public class TrabajadorController {

    @Autowired
    TrabajadorRepository trabajadorRepository;
    @Autowired
    SedeRepository sedeRepository;

    @GetMapping("/lista")
    public String listarTrabajadores(Model model){
        List<Trabajador> list = trabajadorRepository.findAll();
        model.addAttribute("lista",list);
        return "trabajadores/list";
    }

    @GetMapping("/crear")
    public String crearTrabajadores(Model model){
        List<Sede> sedeList = sedeRepository.findAll();
        model.addAttribute("sedes",sedeList);
        return "trabajadores/create";
    }

    @PostMapping("/guardar")
    public String guardarTrabajador(Trabajador trabajador, RedirectAttributes attributes, @RequestParam("action") int action){
        if (action == 1){
            attributes.addFlashAttribute("msg", "Trabajador creado exitosamente");
        }else if (action == 2){
            attributes.addFlashAttribute("msg", "Trabajador editado exitosamente");
        }
        trabajadorRepository.save(trabajador);
        return "redirect:/trabajador/lista";
    }

    @GetMapping("/editar")
    public String editarTrabajador(Model model, @RequestParam("id") String id){
        Optional<Trabajador> trabajadorOptional = trabajadorRepository.findById(id);
        if (trabajadorOptional.isPresent()){
            Trabajador trabajador = trabajadorOptional.get();
            List<Sede> sedeList = sedeRepository.findAll();
            model.addAttribute("sedes",sedeList);
            model.addAttribute("trabajador",trabajador);
            return "trabajadores/edit";
        }else{
            return "redirect:/trabajadores/lista";
        }

    }

    @GetMapping("/borrar")
    public String borrarSede(RedirectAttributes attributes, @RequestParam("id") String id){
        Optional<Trabajador> optionalTrabajador = trabajadorRepository.findById(id);
        if (optionalTrabajador.isPresent()) {
            trabajadorRepository.deleteById(id);
            attributes.addFlashAttribute("msg", "Trabajador eliminado exitosamente");
        }
        return "redirect:/trabajador/lista";
    }
}
