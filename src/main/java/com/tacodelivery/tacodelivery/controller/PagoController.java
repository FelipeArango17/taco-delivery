package com.tacodelivery.tacodelivery.controller;

import com.tacodelivery.tacodelivery.model.ItemCarrito;
import com.tacodelivery.tacodelivery.model.User;
import com.tacodelivery.tacodelivery.service.ItemCarritoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pago")
public class PagoController {

    private final ItemCarritoService itemCarritoService;

    public PagoController(ItemCarritoService itemCarritoService) {
        this.itemCarritoService = itemCarritoService;
    }

    @GetMapping("/seleccionar")
    public String seleccionarMetodo(@RequestParam double total, Model model) {
        model.addAttribute("total", total);
        return "pago_seleccionar";
    }

    @PostMapping("/procesar")
    public String procesarPago(@RequestParam String metodoPago,
                               @RequestParam(required = false) String cuotas,
                               @RequestParam double total,
                               @SessionAttribute("usuario") User usuario,
                               Model model) {

        // Obtener carrito del usuario
        List<ItemCarrito> carrito = itemCarritoService.obtenerItemsPorUsuario(usuario);

        // Armar mensaje según método de pago
        String mensaje;
        switch (metodoPago) {
            case "credito":
                mensaje = "Pago exitoso con tarjeta de crédito a " + cuotas + " cuotas.";
                break;
            case "debito":
                mensaje = "Pago exitoso con tarjeta de débito.";
                break;
            case "efectivo":
                mensaje = "Pago contra entrega — efectivo.";
                break;
            default:
                mensaje = "Método de pago no reconocido.";
        }

        // Pasar datos al modelo
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("total", total);
        model.addAttribute("productos", carrito);

        // Vaciar carrito tras confirmar
        itemCarritoService.vaciarCarrito(usuario);

        return "pedido_exitoso";
    }
}