package com.tp.TpIntegradorBazar.dto;

import com.tp.TpIntegradorBazar.model.Cliente;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponseDTO {
    
    private Long codigoVenta;
    private LocalDate fechaVenta;
    private Double total;
    private List<ProductoDTO> listaProductos;
    private Cliente unCliente;
    
}
