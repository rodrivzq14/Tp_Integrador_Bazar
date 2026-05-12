package com.tp.TpIntegradorBazar.controller;

import com.tp.TpIntegradorBazar.dto.ProductoDTO;
import com.tp.TpIntegradorBazar.service.ProductoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> traerProductos(){
        return ResponseEntity.ok(productoService.traerProductos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> traerProducto(@PathVariable Long id){
        return ResponseEntity.ok(productoService.traerProducto(id));
    }
    
    @GetMapping("/falta_stock")
    public ResponseEntity<List<ProductoDTO>> traerProductoBajoStock(){
        return ResponseEntity.ok(productoService.productoBajoStock());
    }
    
    
    @PostMapping("/crear")
    public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO dto){
        ProductoDTO creado = productoService.crearProducto(dto);
        
        return ResponseEntity.created(URI.create("api/productos" 
                + creado.getCodigoProducto())).body(creado);
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id,
                                                          @Valid @RequestBody ProductoDTO dto){
        return ResponseEntity.ok(productoService.actualizarProducto(id, dto));      
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
    
}
