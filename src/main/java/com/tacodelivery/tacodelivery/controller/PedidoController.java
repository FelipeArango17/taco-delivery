package com.tacodelivery.tacodelivery.controller;

import com.tacodelivery.tacodelivery.model.Taco;
import com.tacodelivery.tacodelivery.service.TacoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/menu")
@SessionAttributes("carrito")
public class PedidoController {

    private final TacoService tacoService;

    public PedidoController(TacoService tacoService) {
        this.tacoService = tacoService;
    }

    @ModelAttribute("carrito")
    public List<Taco> inicializarCarrito() {
        return new ArrayList<>();
    }

    @GetMapping
    public String mostrarMenu(Model model) {
        // Agrupamos tacos por nombre (una sola versión de cada tipo)
        List<Taco> tacos = new ArrayList<>();
        tacoService.listarTacos().stream()
                .map(Taco::getNombre)
                .distinct()
                .forEach(nombre -> tacos.add(
                        tacoService.listarTacos().stream()
                                .filter(t -> t.getNombre().equals(nombre))
                                .findFirst().orElse(null)
                ));
        model.addAttribute("tacos", tacos);
        return "menu";
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam String nombre,
                                   @ModelAttribute("carrito") List<Taco> carrito) {

        // Por defecto: tamaño "Normal"
        Optional<Taco> base = tacoService.listarTacos().stream()
                .filter(t -> t.getNombre().equals(nombre) && t.getTamaño().equals("Normal"))
                .findFirst();

        base.ifPresent(carrito::add);
        return "redirect:/menu/carrito";
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model, @ModelAttribute("carrito") List<Taco> carrito) {
        double total = carrito.stream().mapToDouble(Taco::getPrecio).sum();
        model.addAttribute("total", total);
        model.addAttribute("tacosDisponibles", tacoService.listarTacos());
        return "carrito";
    }

    @PostMapping("/actualizar")
    public String actualizarTamaño(@RequestParam int index,
                                   @RequestParam String tamaño,
                                   @ModelAttribute("carrito") List<Taco> carrito) {

        Taco actual = carrito.get(index);
        String nombre = actual.getNombre();

        tacoService.listarTacos().stream()
                .filter(t -> t.getNombre().equals(nombre) && t.getTamaño().equals(tamaño))
                .findFirst()
                .ifPresent(t -> {
                    actual.setTamaño(t.getTamaño());
                    actual.setPrecio(t.getPrecio());
                });

        return "redirect:/menu/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito(@ModelAttribute("carrito") List<Taco> carrito) {
        carrito.clear();
        return "redirect:/menu";
    }
}
