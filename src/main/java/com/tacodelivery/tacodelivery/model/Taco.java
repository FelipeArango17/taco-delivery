package com.tacodelivery.tacodelivery.model;

public class Taco {

    private String nombre;
    private String tamaño;
    private double precio;

    // Constructor
    public Taco(String nombre, String tamaño, double precio) {
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.precio = precio;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Método opcional: para mostrar el taco en consola o debug
    @Override
    public String toString() {
        return "Taco{" +
                "nombre='" + nombre + '\'' +
                ", tamaño='" + tamaño + '\'' +
                ", precio=" + precio +
                '}';
    }
}
