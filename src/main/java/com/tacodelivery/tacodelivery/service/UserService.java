package com.tacodelivery.tacodelivery.service;

import com.tacodelivery.tacodelivery.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> usuarios = new ArrayList<>();

    // Registrar un usuario, devuelve false si ya existe el email
    public boolean registrarUsuario(User user) {
        if (buscarPorEmail(user.getEmail()) != null) {
            return false; // Ya existe
        }
        usuarios.add(user);
        return true;
    }

    // Buscar usuario por email
    public User buscarPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    // Validar login: devuelve User si credenciales correctas
    public User validarUsuario(String email, String password) {
        User user = buscarPorEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // Opcional: listar todos los usuarios
    public List<User> listarUsuarios() {
        return usuarios;
    }
}
