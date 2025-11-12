package com.tacodelivery.tacodelivery.model;

import jakarta.persistence.*;

@Entity
@Table(name = "items_carrito")
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long item_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "taco_id", nullable = false)
    private Taco taco;

    private int cantidad;
    private int subtotal;

    public ItemCarrito() {}

    public ItemCarrito(User user, Taco taco, int cantidad) {
        this.user = user;
        this.taco = taco;
        this.cantidad = cantidad;
        this.subtotal = taco.getPrecio() * cantidad;
    }

    public Long getItem_id() { return item_id; }
    public void setItem_id(Long item_id) { this.item_id = item_id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Taco getTaco() { return taco; }
    public void setTaco(Taco taco) { this.taco = taco; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = taco.getPrecio() * cantidad;
    }

    public int getSubtotal() { return subtotal; }
    public void setSubtotal(int subtotal) { this.subtotal = subtotal; }
}
