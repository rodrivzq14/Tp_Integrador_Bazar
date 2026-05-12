package com.tp.TpIntegradorBazar.repository;

import com.tp.TpIntegradorBazar.model.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
   public List<Producto> findByCantidadDisponibleLessThan(Double cantidad);
    
}
