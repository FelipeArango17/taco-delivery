package com.tacodelivery.tacodelivery.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccionEntrega;
    private double total;

    @Column(name = "metodo_pago")
    private String metodoPago; // "Tarjeta de crédito", "Tarjeta de débito", "Efectivo"

    private LocalDateTime fechaPedido;

    private String estado; // "PENDIENTE", "PAGADO", "ENTREGADO"

    public Pedido() {}

    public Pedido(String direccionEntrega, double total, String metodoPago) {
        this.direccionEntrega = direccionEntrega;
        this.total = total;
        this.metodoPago = metodoPago;
        this.fechaPedido = LocalDateTime.now();
        this.estado = "PENDIENTE";
    }

    // Getters y setters
    public Long getId() { return id; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}