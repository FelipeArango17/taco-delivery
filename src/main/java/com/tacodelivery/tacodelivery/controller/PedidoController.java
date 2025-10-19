package com.tacodelivery.tacodelivery.controller;

import com.tacodelivery.tacodelivery.service.TacoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PedidoController {

    private final TacoService tacoService;

    public PedidoController(TacoService tacoService) {
        this.tacoService = tacoService;
    }

    @GetMapping("/")
    public String mostrarMenu(Model model) {
        model.addAttribute("tacos", tacoService.listarTacos());
        return "menu";
    }
    
}
