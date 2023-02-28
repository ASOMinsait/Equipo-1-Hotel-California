package com.minsait.facturas.services;

import com.minsait.facturas.models.Factura;
import com.minsait.facturas.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaServiceImp implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Factura> buscarTodos() {
        return facturaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Factura> buscarPorId(Long id) {
        return facturaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Factura> buscarPorIdReservacion(Long id) {
        return facturaRepository.findByReservacion(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Factura guardar(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean eliminar(Long id) {
        boolean estadoEliminado = false;
        Optional<Factura> factura = facturaRepository.findById(id);
        if (factura.isPresent()) {
            facturaRepository.deleteById(id);
            estadoEliminado = true;
        }
        return estadoEliminado;
    }


    @Override
    @Transactional(readOnly = false)
    public Factura procesarPago(Long id, String formaPago) {
        return null;
    }
}
