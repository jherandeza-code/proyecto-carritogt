package com.carritogt.controller;

import com.carritogt.entity.ShoppingCart;
import com.carritogt.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public List<ShoppingCart> listar(@RequestParam String usuario) {
        return shoppingCartService.listar(usuario);
    }

    @PostMapping
    public ResponseEntity<ShoppingCart> agregar(@RequestParam String usuario,
                                                @RequestParam Integer idProducto) {
        ShoppingCart item = shoppingCartService.agregar(usuario, idProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        shoppingCartService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public String toString() {
        return "ShoppingCartController";
    }
}
