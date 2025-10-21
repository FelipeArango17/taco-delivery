package com.tacodelivery.tacodelivery.controller;

import com.tacodelivery.tacodelivery.model.Taco;
import com.tacodelivery.tacodelivery.model.User;
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

        Optional<Taco> base = tacoService.listarTacos().stream()
                .filter(t -> t.getNombre().equals(nombre) && t.getTamaño().equals("Normal"))
                .findFirst();

        base.ifPresent(taco -> carrito.add(new Taco(taco.getNombre(), taco.getTamaño(), taco.getPrecio())));
        return "redirect:/menu/carrito";
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model, @ModelAttribute("carrito") List<Taco> carrito) {
        List<Taco> tacosDisponibles = tacoService.listarTacos();

        for (Taco taco : carrito) {
            tacosDisponibles.stream()
                    .filter(t -> t.getNombre().equals(taco.getNombre()) && t.getTamaño().equals(taco.getTamaño()))
                    .findFirst()
                    .ifPresent(t -> taco.setPrecio(t.getPrecio()));
        }

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", carrito.stream().mapToDouble(Taco::getTotal).sum());
        model.addAttribute("tacosDisponibles", tacosDisponibles);
        return "carrito";
    }

    @PostMapping("/actualizar")
    public String actualizarCarrito(@RequestParam int index,
                                    @RequestParam String tamaño,
                                    @RequestParam(required = false, defaultValue = "1") int cantidad,
                                    @ModelAttribute("carrito") List<Taco> carrito) {

        Taco taco = carrito.get(index);
        taco.setTamaño(tamaño);
        taco.setCantidad(cantidad);

        tacoService.listarTacos().stream()
                .filter(t -> t.getNombre().equals(taco.getNombre()) && t.getTamaño().equals(tamaño))
                .findFirst()
                .ifPresent(t -> taco.setPrecio(t.getPrecio()));

        return "redirect:/menu/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito(@ModelAttribute("carrito") List<Taco> carrito) {
        carrito.clear();
        return "redirect:/menu";
    }

    @PostMapping("/eliminar")
    public String eliminarTaco(@RequestParam int index,
                               @ModelAttribute("carrito") List<Taco> carrito) {
        if (index >= 0 && index < carrito.size()) {
            carrito.remove(index);
        }
        return "redirect:/menu/carrito";
    }

    // Mostrar la confirmación del pedido con dirección editable
    @GetMapping("/confirmar")
    public String mostrarConfirmacion(@ModelAttribute("carrito") List<Taco> carrito,
                                      @SessionAttribute(name = "usuario", required = false) User usuario,
                                      Model model) {
        if (carrito.isEmpty()) {
            return "redirect:/menu"; // carrito vacío, volver al menú
        }

        if (usuario == null) {
            // Si no hay usuario en sesión, mostrar aviso
            model.addAttribute("aviso", "Debes iniciar sesión para confirmar el pedido.");
            return "menu"; // redirige al menú mostrando el aviso
        }

        double total = carrito.stream().mapToDouble(Taco::getTotal).sum();
        model.addAttribute("total", total);
        model.addAttribute("direccion", usuario.getAddress());
        model.addAttribute("tacosDisponibles", tacoService.listarTacos());
        return "confirmar";
    }

    // Finalizar el pedido y mostrar mensaje de éxito
    @PostMapping("/finalizar")
    public String finalizarPedido(@RequestParam String direccion,
                                  @SessionAttribute("usuario") User usuario,
                                  @ModelAttribute("carrito") List<Taco> carrito,
                                  Model model) {

        // Aquí podrías guardar el pedido en BD
        carrito.clear();

        model.addAttribute("mensaje", "¡Pedido confirmado! Se entregará en: " + direccion + ". Pago contra entrega.");
        return "pedido_exitoso";
    }
}
