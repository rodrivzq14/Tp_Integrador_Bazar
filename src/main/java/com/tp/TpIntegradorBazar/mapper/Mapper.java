package com.tp.TpIntegradorBazar.mapper;

import com.tp.TpIntegradorBazar.model.Producto;
import com.tp.TpIntegradorBazar.dto.ProductoDTO;
import com.tp.TpIntegradorBazar.model.Cliente;
import com.tp.TpIntegradorBazar.dto.ClienteDTO;
import com.tp.TpIntegradorBazar.dto.VentaRequestDTO;
import com.tp.TpIntegradorBazar.model.Venta;
import com.tp.TpIntegradorBazar.dto.VentaResponseDTO;
import java.util.List;

public class Mapper {
    
    public static ProductoDTO toDto(Producto p){
        
        return ProductoDTO.builder()
                .codigoProducto(p.getCodigoProducto())
                .nombre(p.getNombre())
                .marca(p.getMarca())
                .costo(p.getCosto())
                .cantidadDisponible(p.getCantidadDisponible())
                .build();
    }
    
    public static ClienteDTO toDto(Cliente c){
        
        return ClienteDTO.builder()
                .idCliente(c.getIdCliente())
                .nombre(c.getNombre())
                .apellido(c.getApellido())
                .dni(c.getDni())
                .build();
    }
    
    public static VentaResponseDTO toDto(Venta v){
        
        List<ProductoDTO> productos = toDTOList(v.getListaProductos());
        
        return VentaResponseDTO.builder()
                .codigoVenta(v.getCodigoVenta())
                .fechaVenta(v.getFechaVenta())
                .total(v.getTotal())
                .listaProductos(productos)
                .unCliente(v.getUnCliente())
                .build();        
    }
    
    public static Producto toEntity(ProductoDTO p){
        Producto prod = Producto.builder()
                .nombre(p.getNombre())
                .marca(p.getMarca())
                .costo(p.getCosto())
                .cantidadDisponible(p.getCantidadDisponible())
                .build();
        
        return prod;
    }
    
    public static Cliente toEntity(ClienteDTO c){
        Cliente cli = Cliente.builder()
                .nombre(c.getNombre())
                .apellido(c.getApellido())
                .dni(c.getDni())
                .build();
        
        return cli;
    }
    
    public static Venta toEntity(VentaRequestDTO v, Cliente c, List<Producto> l){
        
        Venta vent = Venta.builder()
                .fechaVenta(v.getFechaVenta())
                .total(v.getTotal())
                .listaProductos(l)
                .unCliente(c)
                .build();
        
        return vent;
        
    }

    public static List<ProductoDTO> toDTOList(List<Producto> listaProductos) {
        List<ProductoDTO> listaProductosDTO = listaProductos.stream().map(p -> Mapper.toDto(p))
                .toList();
        
        return listaProductosDTO;
    }
    
}
