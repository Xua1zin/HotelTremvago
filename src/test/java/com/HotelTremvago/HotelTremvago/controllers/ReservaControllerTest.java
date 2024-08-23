package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.QuartoEntity;
import com.HotelTremvago.HotelTremvago.entities.ReservaEntity;
import com.HotelTremvago.HotelTremvago.entities.ReservaStatus;
import com.HotelTremvago.HotelTremvago.services.QuartoService;
import com.HotelTremvago.HotelTremvago.services.ReservaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReservaControllerTest {

    @Autowired
    private ReservaController reservaController;

    @MockBean
    private ReservaService reservaService;

    @MockBean
    private QuartoService quartoService;

    @Test
    public void testSaveReserva_QuartosDisponiveis() {
        QuartoEntity quarto = new QuartoEntity();
        List<QuartoEntity> quartos = new ArrayList<>();
        quartos.add(quarto);

        ReservaEntity reservaEntity = new ReservaEntity();
        reservaEntity.setDataInicio(null);
        reservaEntity.setDataFinal(null);

        when(quartoService.quartosDisponiveis(anyLong(), anyInt(), any(), any())).thenReturn(quartos);
        when(reservaService.save(any(ReservaEntity.class))).thenReturn(reservaEntity);

        ResponseEntity<ReservaEntity> response = reservaController.save(1L, 2, reservaEntity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservaEntity, response.getBody());
        verify(quartoService).quartosDisponiveis(anyLong(), anyInt(), any(), any());
        verify(reservaService).save(any(ReservaEntity.class));
    }

    @Test
    public void testSaveReserva_NenhumQuartoDisponivel() {
        ReservaEntity reservaEntity = new ReservaEntity();
        reservaEntity.setDataInicio(null);
        reservaEntity.setDataFinal(null);

        when(quartoService.quartosDisponiveis(anyLong(), anyInt(), any(), any())).thenReturn(new ArrayList<>());

        ResponseEntity<ReservaEntity> response = reservaController.save(1L, 2, reservaEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(quartoService).quartosDisponiveis(anyLong(), anyInt(), any(), any());
        verify(reservaService, never()).save(any(ReservaEntity.class));
    }

    @Test
    public void testCancelaReserva_Sucesso() {
        ReservaEntity reservaEntity = new ReservaEntity();
        reservaEntity.setStatus(ReservaStatus.OCUPADO);

        when(reservaService.findById(anyLong())).thenReturn(reservaEntity);
        when(reservaService.update(any(ReservaEntity.class), anyLong())).thenReturn(reservaEntity);

        ResponseEntity<ReservaEntity> response = reservaController.cancela(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ReservaStatus.CANCELADO, response.getBody().getStatus());
        verify(reservaService).findById(anyLong());
        verify(reservaService).update(any(ReservaEntity.class), anyLong());
    }

    @Test
    public void testCancelaReserva_ReservaNaoEncontrada() {
        when(reservaService.findById(anyLong())).thenReturn(null);

        ResponseEntity<ReservaEntity> response = reservaController.cancela(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(reservaService).findById(anyLong());
        verify(reservaService, never()).update(any(ReservaEntity.class), anyLong());
    }

    @Test
    public void testListaDiasDisponiveisPorMes() {
        List<Integer> diasDisponiveis = new ArrayList<>();
        diasDisponiveis.add(1);
        diasDisponiveis.add(2);

        when(reservaService.datasLivres(anyLong(), anyInt(), anyInt(), anyInt())).thenReturn(diasDisponiveis);

        ResponseEntity<List<Integer>> response = reservaController.listaDiasDisponiveisPorMes(1L, 2, 9, 2024);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(diasDisponiveis, response.getBody());
        verify(reservaService).datasLivres(anyLong(), anyInt(), anyInt(), anyInt());
    }
}
