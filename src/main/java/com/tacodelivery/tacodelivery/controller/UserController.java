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
        return "login";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String validarLogin(@RequestParam String email, @RequestParam String password, Model model) {
        if (userService.validarLogin(email, password)) {
            return "redirect:/";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }
}
