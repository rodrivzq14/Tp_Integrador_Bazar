package com.tp.TpIntegradorBazar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    
    private Long codigoProducto;
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message = "La marca no puede estar vacia")
    private String marca;
    @NotNull(message = "El costo no puede estar vacio")
    @Positive(message = "El costo debe ser mayor a 0")
    private Double costo;
    @NotNull(message = "El stock no puede estar vacio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Double cantidadDisponible;
    
}
