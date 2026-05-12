package com.tp.TpIntegradorBazar.controller;

import com.tp.TpIntegradorBazar.dto.MayorVentaDTO;
import com.tp.TpIntegradorBazar.dto.ProductoDTO;
import com.tp.TpIntegradorBazar.dto.ResumenVentasDTO;
import com.tp.TpIntegradorBazar.dto.VentaRequestDTO;
import com.tp.TpIntegradorBazar.dto.VentaResponseDTO;
import com.tp.TpIntegradorBazar.service.VentaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
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
@RequestMapping("/ventas")
public class VentaController {
    
    @Autowired
    private VentaService ventaService;
    
    @GetMapping()
    public ResponseEntity<List<VentaResponseDTO>> traerVentas(){
        return ResponseEntity.ok(ventaService.traerVentas());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> traerVenta(@PathVariable Long id){
        return ResponseEntity.ok(ventaService.traerVenta(id));
    }
    
    @GetMapping("/productos/{id}")
    public ResponseEntity<List<ProductoDTO>> traerVentaProductos(@PathVariable Long id){
        return ResponseEntity.ok(ventaService.traerVentaProductos(id));
    }
    
    @GetMapping("/estadisticas/{fecha_venta}")
    public ResponseEntity<ResumenVentasDTO> traerEstadisticasVentasDia(@PathVariable LocalDate fecha_venta){
        return ResponseEntity.ok(ventaService.traerEstadisticasVentasDia(fecha_venta));
    }
    
    @GetMapping("/mayor_venta")
    public ResponseEntity<MayorVentaDTO> traerMayorVenta(){
        return ResponseEntity.ok(ventaService.traerMayorVenta());
    }
    
    @PostMapping("/crear")
    public ResponseEntity<VentaResponseDTO> crearVenta(@Valid @RequestBody VentaRequestDTO dto){
        VentaResponseDTO creado = ventaService.crearVenta(dto);
        
        return ResponseEntity.created(URI.create("api/productos" 
                + creado.getCodigoVenta())).body(creado);
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<VentaResponseDTO> actualizarVenta(@PathVariable Long id,
                                                          @Valid @RequestBody VentaRequestDTO dto){
        return ResponseEntity.ok(ventaService.actualizarVenta(id, dto));      
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id){
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
    
}
