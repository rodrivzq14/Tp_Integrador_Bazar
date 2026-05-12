package com.tp.TpIntegradorBazar.service;

import com.tp.TpIntegradorBazar.dto.MayorVentaDTO;
import com.tp.TpIntegradorBazar.dto.ProductoDTO;
import com.tp.TpIntegradorBazar.dto.ResumenVentasDTO;
import com.tp.TpIntegradorBazar.dto.VentaRequestDTO;
import com.tp.TpIntegradorBazar.dto.VentaResponseDTO;
import com.tp.TpIntegradorBazar.exception.InsufficientStockException;
import com.tp.TpIntegradorBazar.exception.NotFoundException;
import com.tp.TpIntegradorBazar.mapper.Mapper;
import com.tp.TpIntegradorBazar.model.Cliente;
import com.tp.TpIntegradorBazar.model.Producto;
import com.tp.TpIntegradorBazar.model.Venta;
import com.tp.TpIntegradorBazar.repository.ClienteRepository;
import com.tp.TpIntegradorBazar.repository.ProductoRepository;
import com.tp.TpIntegradorBazar.repository.VentaRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaServiceImpl implements VentaService{
    
    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private VentaRepository ventaRepo;
    
    @Autowired
    private ClienteRepository clienteRepo;
    
    @Override
    public List<Producto> buscarProductos(List<Long> ids) {
        List<Producto> listaProductos = ids.stream()
                .map(idProd -> productoRepo.findById(idProd)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado")))
                .collect(Collectors.toCollection(ArrayList::new));
        
        return listaProductos;
    }
    
    @Override
    public void comprobarStock(List<Producto> listaProductos) {
        for (Producto p : listaProductos) {
            int cantidadNecesaria = Collections.frequency(listaProductos, p);
            if (p.getCantidadDisponible() < cantidadNecesaria) {
                throw new InsufficientStockException("No hay suficiente stock del producto codigo: " +
                    p.getCodigoProducto());
            }
        }   
    }
    
    @Override
    public void descontarStock(List<Producto> listaProductos){    
        Map<Producto, Long> cantidadPorProducto= listaProductos.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        for (Map.Entry<Producto, Long> productoStock : cantidadPorProducto.entrySet()) {
            Producto p = productoStock.getKey();
            Long cantidadProducto = productoStock.getValue();
            p.setCantidadDisponible(p.getCantidadDisponible() - cantidadProducto);
            productoRepo.save(p);
        }      
    }
    
    @Override
    public void aumentarStock(List<Producto> listaProductos){
        Map<Producto, Long> cantidadPorProducto= listaProductos.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        for (Map.Entry<Producto, Long> productoStock : cantidadPorProducto.entrySet()) {
            Producto p = productoStock.getKey();
            Long cantidadProducto = productoStock.getValue();
            p.setCantidadDisponible(p.getCantidadDisponible() + cantidadProducto);
            productoRepo.save(p);
        }
    }
    
    @Override
    public List<VentaResponseDTO> traerVentas() {
        List<Venta> ventas = ventaRepo.findAll();
        if (ventas.isEmpty()){
            throw new NotFoundException("No se encontraron ventas");
        }
        return ventas.stream().map(Mapper::toDto).toList();
    }

    @Override
    public VentaResponseDTO crearVenta(VentaRequestDTO v) {
        Cliente cli = clienteRepo.findById(v.getIdCliente())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        
        List<Producto> listaProductos = buscarProductos(v.getListaProductos());
        
        comprobarStock(listaProductos);
        descontarStock(listaProductos);
        
        return Mapper.toDto(ventaRepo.save(Mapper.toEntity(v, cli, listaProductos)));
    }

    @Override
    public VentaResponseDTO actualizarVenta(Long id, VentaRequestDTO v) {
        Venta vent = ventaRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Venta no encontrada"));
        
        Cliente cli = clienteRepo.findById(v.getIdCliente())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        
        List<Producto> listaProductos = buscarProductos(v.getListaProductos());
    
        // Devolvemos el stock
        aumentarStock(vent.getListaProductos());
        
        // Comprobamos si hay stock necesario para la venta modificada
        comprobarStock(listaProductos);
        // Descontamos el stock de los productos con la venta modificada
        descontarStock(listaProductos);
        
        vent.setFechaVenta(v.getFechaVenta());
        vent.setListaProductos(listaProductos);
        vent.setTotal(v.getTotal());
        vent.setUnCliente(cli);
        
        return Mapper.toDto(ventaRepo.save(vent));
    }

    @Override
    public void eliminarVenta(Long id) {
        Venta vent = ventaRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Venta no encontrada"));
        
        aumentarStock(vent.getListaProductos());
        
        ventaRepo.delete(vent);
    }

    @Override
    public VentaResponseDTO traerVenta(Long id) {
        Venta vent = ventaRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Venta no encontrada"));
        
        return Mapper.toDto(vent);
    }
    
    @Override
    public List<ProductoDTO> traerVentaProductos(Long id){
        Venta vent = ventaRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Venta no encontrada"));
        
        return Mapper.toDTOList(vent.getListaProductos());
    }

    @Override
    public ResumenVentasDTO traerEstadisticasVentasDia(LocalDate fecha){      
        List<Venta> ventas = ventaRepo.findByFechaVenta(fecha);
        if (ventas.isEmpty()){
            throw new NotFoundException("No se encontraron ventas");
        }
        
        ResumenVentasDTO resumen = new ResumenVentasDTO();
        resumen.setCantidadVentas(ventas.size());
        resumen.setMontoTotal(ventas.stream().mapToDouble(Venta::getTotal).sum());
        
        return resumen;
    }

    @Override
    public MayorVentaDTO traerMayorVenta() {
        Venta venta = ventaRepo.findTopByOrderByTotalDesc()
                .orElseThrow(() -> new NotFoundException("No se encontro ninguna venta"));
        
        MayorVentaDTO ventaMayor = new MayorVentaDTO();
        ventaMayor.setCodigoVenta(venta.getCodigoVenta());
        ventaMayor.setTotal(venta.getTotal());
        ventaMayor.setCantidadProductos(venta.getListaProductos().size());
        ventaMayor.setNombreCliente(venta.getUnCliente().getNombre());
        ventaMayor.setApellidoCliente(venta.getUnCliente().getApellido());
        
        return ventaMayor;
    }
    
}
