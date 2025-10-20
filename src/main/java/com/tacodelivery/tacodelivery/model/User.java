package com.tacodelivery.tacodelivery.model;

public class User {
    private String nombre;
    private String email;
    private String password;
    private String address;
    private String telefono;

    public User(String nombre, String email, String password, String address, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.address = address;
        this.telefono = telefono;
    }

    // Getters y setters
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
