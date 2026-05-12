package com.tp.TpIntegradorBazar.service;

import com.tp.TpIntegradorBazar.dto.ProductoDTO;
import com.tp.TpIntegradorBazar.exception.NotFoundException;
import com.tp.TpIntegradorBazar.mapper.Mapper;
import com.tp.TpIntegradorBazar.model.Producto;
import com.tp.TpIntegradorBazar.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService{
    
    @Autowired
    private ProductoRepository productoRepo;

    @Override
    public List<ProductoDTO> traerProductos() {
        List<Producto> productos = productoRepo.findAll();
        if (productos.isEmpty()){
            throw new NotFoundException("No se encontraron productos");
        }
        return productos.stream().map(Mapper::toDto).toList();
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO p) {
        return Mapper.toDto(productoRepo.save(Mapper.toEntity(p)));       
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO p) {
        Producto prod = productoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
    
        prod.setNombre(p.getNombre());
        prod.setMarca(p.getMarca());
        prod.setCosto(p.getCosto());
        prod.setCantidadDisponible(p.getCantidadDisponible());
        
        return Mapper.toDto(productoRepo.save(prod));
    }

    @Override
    public void eliminarProducto(Long id) {
        Producto prod = productoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        productoRepo.delete(prod);
    }   

    @Override
    public List<ProductoDTO> productoBajoStock() {
        List<Producto> listaBajoStock = productoRepo.findByCantidadDisponibleLessThan(5.0);

        if (listaBajoStock.isEmpty()){
            throw new NotFoundException("No hay productos con bajo stock");
        }
        return listaBajoStock.stream().map(Mapper::toDto).toList();
    }

    @Override
    public ProductoDTO traerProducto(Long id) {
        Producto prod = productoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        
        return Mapper.toDto(prod);
    }
}
