package com.carritogt.service;

import com.carritogt.entity.Producto;
import com.carritogt.entity.ShoppingCart;
import com.carritogt.repository.ProductoRepository;
import com.carritogt.repository.ShoppingCartRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductoRepository productoRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               ProductoRepository productoRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productoRepository = productoRepository;
    }

    public List<ShoppingCart> listar(String codUsuario) {
        return shoppingCartRepository.findByCodUsuario(codUsuario);
    }

    public ShoppingCart agregar(String codUsuario, Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "El producto " + idProducto + " no existe"));

        ShoppingCart item = new ShoppingCart();
        item.setProducto(producto);
        item.setCodUsuario(codUsuario);
        item.setCantidad(1);
        return shoppingCartRepository.save(item);
    }

    public void eliminar(Integer idShoppingCart) {
        if (!shoppingCartRepository.existsById(idShoppingCart)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "El item " + idShoppingCart + " no esta en el carrito");
        }
        shoppingCartRepository.deleteById(idShoppingCart);
    }

    @Override
    public String toString() {
        return "ShoppingCartService";
    }
}
