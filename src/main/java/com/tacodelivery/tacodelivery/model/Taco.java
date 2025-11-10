package com.tacodelivery.tacodelivery.model;

public class Taco {

    private String nombre;
    private String tamaño;
    private int precio;
    private int cantidad;

    // Constructor
    public Taco(String nombre, String tamaño, int precio) {
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.precio = precio;
        this.cantidad = 1;
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

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getTotal() {
        return precio * cantidad;
    }

    @Override
    public String toString() {
        return "Taco{" +
                "nombre='" + nombre + '\'' +
                ", tamaño='" + tamaño + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}
