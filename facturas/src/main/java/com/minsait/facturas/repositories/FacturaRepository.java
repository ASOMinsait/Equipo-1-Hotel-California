package com.minsait.facturas.repositories;

import com.minsait.facturas.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface FacturaRepository extends JpaRepository<Factura,Long> {


    @Query("SELECT f FROM facturas f WHERE f.idReservacion = :id")
    Optional<Factura> findByReservacion(@Param("id") Long id);
}
