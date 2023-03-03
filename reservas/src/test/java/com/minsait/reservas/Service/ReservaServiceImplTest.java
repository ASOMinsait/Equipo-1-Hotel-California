package com.minsait.reservas.Service;

import com.minsait.reservas.Models.Reserva;
import com.minsait.reservas.Repositories.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {
    @Mock
    ReservaRepository reservaRepository;
    @InjectMocks
    ReservaServiceImpl reservaService;
    @Test
    void testMostrarTodos() {
        when(reservaService
                .mostrarTodos())
                .thenReturn(List
                        .of(Datos.crearReserva().get(),Datos.crearReserva2().get()));
        List<Reserva> reservas=reservaService
                .mostrarTodos();
        assertTrue( !reservas.isEmpty() );
    }

    @Test
    void testBuscarPorId() {
        when(reservaService
                .buscarPorId(anyLong()))
                .thenReturn(Datos.crearReserva());
        Reserva reserva=reservaService
                .buscarPorId(1L).get();
        assertTrue(reserva.getIdReserva()!=null);
    }

    @Test
    void testCrearReserva() {
        Reserva reserva=new Reserva(null, 5L, new Date(),new Date(),"Disponible");
        when(reservaService.crearReserva(any(Reserva.class))).then(invocation ->
        {
            Reserva reservaTemporal=invocation.getArgument(0);
            reservaTemporal.setIdReserva(3L);
            return reservaTemporal;
        });
        Reserva reserva1=reservaService.crearReserva(reserva);
        assertTrue(reserva1.getIdReserva()==3L);

    }

    @Test
    void testEliminar() {
        when(reservaService
                .buscarPorId(anyLong()))
                .thenReturn(Datos.crearReserva());
        boolean bool=reservaService
                .eliminar(1L);
        assertTrue(bool);
    }
}