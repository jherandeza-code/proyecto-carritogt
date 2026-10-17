package com.carritogt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idShoppingCart;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    private String codUsuario;
    private Integer cantidad;

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getIdShoppingCart() {
        return idShoppingCart;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "idShoppingCart=" + idShoppingCart +
                ", producto=" + producto +
                ", codUsuario='" + codUsuario + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }

    public void setIdShoppingCart(Integer idShoppingCart) {
        this.idShoppingCart = idShoppingCart;

    }
}
