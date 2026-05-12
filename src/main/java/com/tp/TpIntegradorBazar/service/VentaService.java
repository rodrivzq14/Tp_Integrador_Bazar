package com.tp.TpIntegradorBazar.service;

import com.tp.TpIntegradorBazar.dto.MayorVentaDTO;
import com.tp.TpIntegradorBazar.dto.ProductoDTO;
import com.tp.TpIntegradorBazar.dto.ResumenVentasDTO;
import com.tp.TpIntegradorBazar.dto.VentaRequestDTO;
import com.tp.TpIntegradorBazar.dto.VentaResponseDTO;
import com.tp.TpIntegradorBazar.model.Producto;
import java.time.LocalDate;
import java.util.List;

public interface VentaService {
    
    public List<Producto> buscarProductos(List<Long> ids);
    public void comprobarStock(List<Producto> listaProductos);
    public void descontarStock(List<Producto> listaProductos);
    public void aumentarStock(List<Producto> listaProductos);
    public List<VentaResponseDTO> traerVentas();
    public VentaResponseDTO crearVenta(VentaRequestDTO v);
    public VentaResponseDTO actualizarVenta(Long id, VentaRequestDTO v);
    public void eliminarVenta(Long id);
    public VentaResponseDTO traerVenta(Long id);
    public List<ProductoDTO> traerVentaProductos(Long id);
    public ResumenVentasDTO traerEstadisticasVentasDia(LocalDate fecha);
    public MayorVentaDTO traerMayorVenta();
    
}
