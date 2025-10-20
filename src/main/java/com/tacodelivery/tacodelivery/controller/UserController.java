package com.tacodelivery.tacodelivery.controller;

import com.tacodelivery.tacodelivery.model.User;
import com.tacodelivery.tacodelivery.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("user", new User("", "", ""));
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute User user, Model model) {
        userService.registrarUsuario(user);
        model.addAttribute("mensaje", "Usuario registrado con éxito");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("user", new User("", "", ""));
        return "login";
    }

    @PostMapping("/login")
    public String validarLogin(@ModelAttribute User user, Model model) {
        if (userService.validarLogin(user.getEmail(), user.getPassword())) {
            return "redirect:/menu";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }
}
