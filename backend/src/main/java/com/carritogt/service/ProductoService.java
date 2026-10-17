package com.carritogt.service;

import com.carritogt.entity.Producto;
import com.carritogt.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @Override
    public String toString() {
        return "ProductoService";
    }
}
