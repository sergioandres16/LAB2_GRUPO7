package com.example.lab2_grupo7.controller;

import com.example.lab2_grupo7.entity.Inventario;
import com.example.lab2_grupo7.entity.Marca;
import com.example.lab2_grupo7.entity.Sede;
import com.example.lab2_grupo7.entity.Tipo;
import com.example.lab2_grupo7.repository.InventarioRepository;
import com.example.lab2_grupo7.repository.MarcaRepository;
import com.example.lab2_grupo7.repository.SedeRepository;
import com.example.lab2_grupo7.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    SedeRepository sedeRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    TipoRepository tipoRepository;

    @GetMapping(value = {"/lista", ""})
    public String listaInventario(Model model) {
        List<Inventario> listaInventario = inventarioRepository.findAll();
        model.addAttribute("listaInventario", listaInventario);
        return "inventario/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoInventario(Model model) {
        //Sede
        List<Sede> listaSede = sedeRepository.findAll();
        model.addAttribute("listaSede", listaSede);

        //Marca
        List<Marca> listaMarca = marcaRepository.findAll();
        model.addAttribute("listaMarca", listaMarca);

        //Tipo
        List<Tipo> listaTipo = tipoRepository.findAll();
        model.addAttribute("listaTipo", listaTipo);

        return "inventario/nuevo";
    }

    @PostMapping(value = {"/save"})
    public String guardarInventario(Inventario inventario, RedirectAttributes attr) {
        if (inventario.getIdinventario() == null) {
            attr.addFlashAttribute("msg", "Inventario creado exitosamente.");
        } else {
            attr.addFlashAttribute("msg", "Inventario editado exitosamente.");
        }
        inventarioRepository.save(inventario);
        return "redirect:/inventario/lista";
    }

    @GetMapping(value = {"/edit"})
    public String editarInventario(Model model,
                                   @RequestParam("id") Integer idinventario) {

        Optional<Inventario> optionalInventario = inventarioRepository.findById(idinventario);

        if (optionalInventario.isPresent()) {
            //Sede
            List<Sede> listaSede = sedeRepository.findAll();
            model.addAttribute("listaSede", listaSede);

            //Marca
            List<Marca> listaMarca = marcaRepository.findAll();
            model.addAttribute("listaMarca", listaMarca);

            //Tipo
            List<Tipo> listaTipo = tipoRepository.findAll();
            model.addAttribute("listaTipo", listaTipo);

            Inventario inventario = optionalInventario.get();
            model.addAttribute("inventario", inventario);

            return "inventario/editar";
        } else {
            return "redirect:/inventario/lista";
        }
    }

    @GetMapping({"/delete"})
    public String borrarInventario(@RequestParam("id") Integer idinventario, RedirectAttributes attr) {
        Optional<Inventario> optionalInventario = inventarioRepository.findById(idinventario);
        if (optionalInventario.isPresent()) {
            inventarioRepository.deleteById(idinventario);
            attr.addFlashAttribute("msg", "Inventario borrado exitosamente.");
        }
        return "redirect:/inventario/lista";
    }
}
