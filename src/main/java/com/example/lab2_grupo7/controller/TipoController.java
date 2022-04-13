package com.example.lab2_grupo7.controller;

import com.example.lab2_grupo7.entity.Tipo;
import com.example.lab2_grupo7.entity.Trabajador;
import com.example.lab2_grupo7.repository.TipoRepository;
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
@RequestMapping("/tipos")
public class TipoController {

    @Autowired
    TipoRepository tipoRepository;

    @Autowired
    TrabajadorRepository trabajadorRepository;

    @GetMapping("/lista")
    public String listarTipo(Model model){
        List<Tipo> tipoList = tipoRepository.findAll();
        model.addAttribute("lista", tipoList);
        return "tipos/list";
    }

    @GetMapping("/crear")
    public String crearTipo(){ return "tipos/create"; }

    @GetMapping("/editar")
    public String editarTipo(@RequestParam("id") int id, Model model){
        Optional<Tipo> tipoOptional = tipoRepository.findById(id);
        if (tipoOptional.isPresent()) {
            Tipo tipo = tipoOptional.get();
            model.addAttribute("tipo", tipo);

            List<Trabajador> list = trabajadorRepository.buscarPorSede(id);
            model.addAttribute("trabajadores", list);
            model.addAttribute("tipos", tipo);
            return "tipos/edit";
        } else {
            return "redirect:/tipos/lista";
        }
    }

    @PostMapping("/guardar")
    public String guardarTipo(Tipo tipo, RedirectAttributes attributes){
        tipoRepository.save(tipo);
        return "redirect:/tipos/lista";
    }

    @GetMapping("/borrar")
    public String borrarTipo(@RequestParam("id") int id, RedirectAttributes attributes){
        Optional<Tipo> tipoOptional = tipoRepository.findById(id);
        if(tipoOptional.isPresent()){
            Tipo tipo = tipoOptional.get();
            tipoRepository.deleteById(id);
        }
        return "redirect:/tipos/lista";
    }

}
