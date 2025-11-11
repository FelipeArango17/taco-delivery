package com.tacodelivery.tacodelivery.model;

import jakarta.persistence.*; // Importante para las anotaciones JPA

@Entity                     // Marca la clase como entidad (tabla en la base)
@Table(name = "users")      // Puedes cambiar el nombre de la tabla si prefieres
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    private Long user_id;

    private String nombre;
    private String email;
    private String password;
    private String address;
    private String telefono;

    public User() {} // Constructor vacío requerido por JPA

    public User(String nombre, String email, String password, String address, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.address = address;
        this.telefono = telefono;
    }

    // Getters y setters
    public Long getId() { return user_id; }
    public void setId(Long id) { this.user_id = user_id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
