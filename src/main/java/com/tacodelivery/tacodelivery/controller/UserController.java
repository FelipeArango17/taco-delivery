package com.tacodelivery.tacodelivery.controller;

import com.tacodelivery.tacodelivery.model.User;
import com.tacodelivery.tacodelivery.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🔹 Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("user", new User("", "", "", "", ""));
        return "registro";
    }

    // 🔹 Procesar registro
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute User user, Model model) {
        boolean registrado = userService.registrarUsuario(user);
        if (registrado) {
            model.addAttribute("mensaje", "Usuario registrado con éxito");
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Ya existe un usuario con ese email");
            model.addAttribute("user", new User("", "", "", "", "")); // ✅ evita error en el form
            return "registro";
        }
    }

    // 🔹 Mostrar login
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("user", new User("", "", "", "", ""));
        return "login";
    }

    // 🔹 Procesar login
    @PostMapping("/login")
    public String validarLogin(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {

        User user = userService.validarUsuario(email, password);

        if (user != null) {
            session.setAttribute("usuario", user);
            return "redirect:/menu";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            model.addAttribute("user", new User("", "", "", "", "")); // ✅ necesario para evitar error de Thymeleaf
            return "login";
        }
    }

    // 🔹 Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
