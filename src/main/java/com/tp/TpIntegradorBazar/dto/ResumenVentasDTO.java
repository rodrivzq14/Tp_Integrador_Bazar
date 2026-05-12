package com.tp.TpIntegradorBazar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumenVentasDTO {
    
    private Integer cantidadVentas;
    private Double montoTotal;
    
}
