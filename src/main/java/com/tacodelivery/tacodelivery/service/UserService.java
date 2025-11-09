package com.tacodelivery.tacodelivery.service;

import com.tacodelivery.tacodelivery.model.User;
import com.tacodelivery.tacodelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Registrar un usuario, devuelve false si ya existe el email
    public boolean registrarUsuario(User user) {
        if (buscarPorEmail(user.getEmail()) != null) {
            return false; // Ya existe
        }
        userRepository.save(user);
        return true;
    }

    // Buscar usuario por email
    public User buscarPorEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Validar login: devuelve User si credenciales correctas
    public User validarUsuario(String email, String password) {
        User user = buscarPorEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // Listar todos los usuarios
    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }
}
