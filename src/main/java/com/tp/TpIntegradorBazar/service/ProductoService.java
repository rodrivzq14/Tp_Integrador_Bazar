package com.tp.TpIntegradorBazar.service;

import com.tp.TpIntegradorBazar.dto.ProductoDTO;
import java.util.List;

public interface ProductoService {
    
    public List<ProductoDTO> traerProductos();
    public ProductoDTO crearProducto(ProductoDTO p);
    public ProductoDTO actualizarProducto(Long id, ProductoDTO P);
    public void eliminarProducto(Long id);
    public List<ProductoDTO> productoBajoStock();
    public ProductoDTO traerProducto(Long id);
    
}
