package com.tp.TpIntegradorBazar.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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
public class VentaRequestDTO {
    
    private Long codigoVenta;
    @NotNull(message = "La fecha no puede estar vacia")
    @PastOrPresent(message = "La fecha debe ser de hoy o anterior")
    private LocalDate fechaVenta;
    @NotNull(message = "El total no puede estar vacio")
    @Positive(message = "El total debe ser mayor a 0")
    private Double total;
    @NotEmpty(message = "La lista de productos no puede estar vacia")
    private List<Long> listaProductos;
    @NotNull(message = "La id no puede estar vacia")
    private Long idCliente;
    
}
