package com.tacodelivery.tacodelivery.service;

import com.tacodelivery.tacodelivery.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> usuarios = new ArrayList<>();

    public void registrarUsuario(User user) {
        usuarios.add(user);
    }

    public List<User> listarUsuarios() {
        return usuarios;
    }

    public boolean validarLogin(String email, String password) {
        return usuarios.stream()
                .anyMatch(u -> u.getEmail().equals(email) && u.getPassword().equals(password));
    }
}
