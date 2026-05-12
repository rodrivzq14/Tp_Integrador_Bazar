package com.tp.TpIntegradorBazar.repository;

import com.tp.TpIntegradorBazar.model.Venta;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long>{
    
    List<Venta> findByFechaVenta(LocalDate fecha);
    Optional<Venta> findTopByOrderByTotalDesc();
    
}
