package com.tacodelivery.tacodelivery.controller;

import com.tacodelivery.tacodelivery.model.ItemCarrito;
import com.tacodelivery.tacodelivery.model.Pedido;
import com.tacodelivery.tacodelivery.model.User;
import com.tacodelivery.tacodelivery.service.ItemCarritoService;
import com.tacodelivery.tacodelivery.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/pago")
public class PagoController {

    private final ItemCarritoService itemCarritoService;
    private final PedidoService pedidoService;

    public PagoController(ItemCarritoService itemCarritoService, PedidoService pedidoService) {
        this.itemCarritoService = itemCarritoService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/seleccionar")
    public String seleccionarMetodo(@RequestParam double total,
                                    @SessionAttribute("usuario") User usuario,
                                    Model model) {

        model.addAttribute("total", total);
        model.addAttribute("direccion", usuario.getAddress());
        return "pago_seleccionar";
    }

    @PostMapping("/procesar")
    public String procesarPago(@RequestParam String metodoPago,
                               @RequestParam(required = false) String cuotas,
                               @RequestParam(required = false) String numeroTarjeta,
                               @RequestParam(required = false) String titularTarjeta,
                               @RequestParam double total,
                               @SessionAttribute("usuario") User usuario,
                               Model model) {

        List<ItemCarrito> carrito = itemCarritoService.obtenerItemsPorUsuario(usuario);

        // Validar número de tarjeta si corresponde
        if ((metodoPago.equals("credito") || metodoPago.equals("debito")) &&
                (numeroTarjeta == null || !numeroTarjeta.matches("\\d{16}"))) {
            model.addAttribute("error", "Número de tarjeta inválido. Debe tener 16 dígitos.");
            model.addAttribute("total", total);
            model.addAttribute("direccion", usuario.getAddress());
            return "pago_seleccionar";
        }

        // Generar mensaje según método de pago
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

        // Crear y guardar el pedido
        Pedido pedido = new Pedido();
        pedido.setDireccionEntrega(usuario.getAddress());
        pedido.setEstado("Confirmado");
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setMetodoPago(metodoPago);
        pedido.setTotal(total);
        pedido.setNumeroTarjeta(numeroTarjeta);
        pedido.setTitularTarjeta(titularTarjeta);
        pedido.setCuotas(cuotas);
        pedido.setEmailUsuario(usuario.getEmail());

        pedidoService.guardar(pedido);

        // Pasar datos al modelo
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("total", total);
        model.addAttribute("productos", carrito);
        model.addAttribute("direccion", usuario.getAddress());
        model.addAttribute("metodoPago", metodoPago);

        // Vaciar carrito después de confirmar
        itemCarritoService.vaciarCarrito(usuario);

        return "pedido_exitoso";
    }
}