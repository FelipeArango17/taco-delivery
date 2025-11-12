package com.tacodelivery.tacodelivery.controller;

import com.tacodelivery.tacodelivery.model.*;
import com.tacodelivery.tacodelivery.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/menu")
public class PedidoController {

    private final TacoService tacoService;
    private final ItemCarritoService itemCarritoService;

    public PedidoController(TacoService tacoService, ItemCarritoService itemCarritoService) {
        this.tacoService = tacoService;
        this.itemCarritoService = itemCarritoService;
    }

    @GetMapping
    public String mostrarMenu(Model model, @SessionAttribute(name = "usuario", required = false) User usuario) {
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

        if (usuario == null) {
            model.addAttribute("aviso", "Debes iniciar sesión para agregar al carrito.");
        }

        return "menu";
    }

    // Agregar un taco al carrito
    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam String nombre,
                                   @SessionAttribute(name = "usuario", required = false) User usuario,
                                   Model model) {

        if (usuario == null) {
            model.addAttribute("aviso", "Debes iniciar sesión para agregar al carrito.");
            model.addAttribute("tacos", tacoService.listarTacos());
            return "menu";
        }

        Optional<Taco> base = tacoService.listarTacos().stream()
                .filter(t -> t.getNombre().equals(nombre) && t.getTamaño().equals("Normal"))
                .findFirst();

        base.ifPresent(taco -> itemCarritoService.agregarItem(usuario, taco, 1));

        return "redirect:/menu/carrito";
    }

    // Mostrar carrito
    @GetMapping("/carrito")
    public String verCarrito(Model model, @SessionAttribute(name = "usuario", required = false) User usuario) {
        if (usuario == null) {
            model.addAttribute("aviso", "Debes iniciar sesión para ver el carrito.");
            model.addAttribute("tacos", tacoService.listarTacos());
            return "menu";
        }

        List<ItemCarrito> carrito = itemCarritoService.obtenerItemsPorUsuario(usuario);
        int total = itemCarritoService.calcularTotal(usuario);

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        model.addAttribute("tacosDisponibles", tacoService.listarTacos());
        return "carrito";
    }

    // Actualizar cantidad
    @PostMapping("/actualizar")
    public String actualizarCarrito(@RequestParam Long itemId,
                                    @RequestParam(required = false, defaultValue = "1") int cantidad) {
        itemCarritoService.actualizarCantidad(itemId, cantidad);
        return "redirect:/menu/carrito";
    }

    // 🔧 Nuevo: actualizar tamaño del taco desde el carrito
    @PostMapping("/actualizarTamaño")
    public String actualizarTamaño(@RequestParam Long itemId,
                                   @RequestParam String nuevoTamaño) {
        itemCarritoService.actualizarTamaño(itemId, nuevoTamaño);
        return "redirect:/menu/carrito";
    }

    @PostMapping("/eliminar")
    public String eliminarTaco(@RequestParam Long itemId) {
        itemCarritoService.eliminarItem(itemId);
        return "redirect:/menu/carrito";
    }

    // ✅ Corregido con transacción activa en el servicio
    @PostMapping("/vaciar")
    public String vaciarCarrito(@SessionAttribute(name = "usuario", required = false) User usuario) {
        if (usuario != null) {
            itemCarritoService.vaciarCarrito(usuario);
        }
        return "redirect:/menu";
    }

    @GetMapping("/confirmar")
    public String mostrarConfirmacion(@SessionAttribute(name = "usuario", required = false) User usuario,
                                      Model model) {
        if (usuario == null) {
            model.addAttribute("aviso", "Debes iniciar sesión para confirmar el pedido.");
            model.addAttribute("tacos", tacoService.listarTacos());
            return "menu";
        }

        List<ItemCarrito> carrito = itemCarritoService.obtenerItemsPorUsuario(usuario);
        if (carrito.isEmpty()) {
            return "redirect:/menu";
        }

        int total = itemCarritoService.calcularTotal(usuario);
        model.addAttribute("total", total);
        model.addAttribute("direccion", usuario.getAddress());
        model.addAttribute("carrito", carrito);
        return "confirmar";
    }

    @PostMapping("/finalizar")
    public String finalizarPedido(@RequestParam String direccion,
                                  @SessionAttribute("usuario") User usuario,
                                  Model model) {

        List<ItemCarrito> carrito = itemCarritoService.obtenerItemsPorUsuario(usuario);
        if (carrito.isEmpty()) {
            return "redirect:/menu";
        }

        int total = itemCarritoService.calcularTotal(usuario);
        model.addAttribute("mensaje", "¡Pedido confirmado! Se entregará en: " + direccion + ". Pago contra entrega.");
        model.addAttribute("productos", carrito);
        model.addAttribute("total", total);

        itemCarritoService.vaciarCarrito(usuario);

        return "pedido_exitoso";
    }
}
