package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.QuartoEntity;
import com.HotelTremvago.HotelTremvago.entities.ReservaEntity;
import com.HotelTremvago.HotelTremvago.entities.ReservaStatus;
import com.HotelTremvago.HotelTremvago.repositories.QuartoRepository;
import com.HotelTremvago.HotelTremvago.repositories.ReservaRepository;
import com.HotelTremvago.HotelTremvago.services.QuartoService;
import com.HotelTremvago.HotelTremvago.services.ReservaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReservaControllerTest {

    @Autowired
    private ReservaController reservaController;

    @MockBean
    private ReservaService reservaService;

    @MockBean
    private ReservaRepository reservaRepository;

    @MockBean
    private QuartoRepository quartoRepository;

    @MockBean
    private QuartoService quartoService;

    @Test
    public void testSave_Success() {
        long tipoQuartoId = 1L;
        int capacidade = 2;
        ReservaEntity reservaEntity = new ReservaEntity();
        reservaEntity.setDataInicio(new Date());
        reservaEntity.setDataFinal(new Date());

        QuartoEntity quartoEntity = new QuartoEntity();
        when(quartoService.quartosDisponiveis(anyLong(), anyInt(), any(), any()))
                .thenReturn(Collections.singletonList(quartoEntity));
        when(reservaService.save(any(ReservaEntity.class))).thenReturn(reservaEntity);

        ResponseEntity<ReservaEntity> response = reservaController.save(tipoQuartoId, capacidade, reservaEntity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservaEntity, response.getBody());
    }

    @Test
    public void testSave_NoQuartosDisponiveis() {
        long tipoQuartoId = 1L;
        int capacidade = 2;
        ReservaEntity reservaEntity = new ReservaEntity();
        reservaEntity.setDataInicio(new Date());
        reservaEntity.setDataFinal(new Date());

        when(quartoService.quartosDisponiveis(anyLong(), anyInt(), any(), any()))
                .thenReturn(Collections.emptyList());

        ResponseEntity<ReservaEntity> response = reservaController.save(tipoQuartoId, capacidade, reservaEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testSave_Exception() {
        long tipoQuartoId = 1L;
        int capacidade = 2;
        ReservaEntity reservaEntity = new ReservaEntity();
        reservaEntity.setDataInicio(new Date());
        reservaEntity.setDataFinal(new Date());

        when(quartoService.quartosDisponiveis(anyLong(), anyInt(), any(), any()))
                .thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<ReservaEntity> response = reservaController.save(tipoQuartoId, capacidade, reservaEntity);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testListaDiasDisponiveisPorMes_Success() {
        Long tipoQuartoId = 1L;
        int capacidade = 2;
        int mes = 6;
        int ano = 2023;
        List<Integer> diasDisponiveis = Arrays.asList(1, 2, 3);

        when(reservaService.datasLivres(tipoQuartoId, capacidade, mes, ano)).thenReturn(diasDisponiveis);

        ResponseEntity<List<Integer>> response = reservaController.listaDiasDisponiveisPorMes(tipoQuartoId, capacidade, mes, ano);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(diasDisponiveis, response.getBody());
    }

    @Test
    public void testListaDiasDisponiveisPorMes_Exception() {
        Long tipoQuartoId = 1L;
        int capacidade = 2;
        int mes = 6;
        int ano = 2023;

        when(reservaService.datasLivres(tipoQuartoId, capacidade, mes, ano)).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<List<Integer>> response = reservaController.listaDiasDisponiveisPorMes(tipoQuartoId, capacidade, mes, ano);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCancela_Success() {
        Long id = 1L;
        ReservaEntity reservaEntity = new ReservaEntity();
        reservaEntity.setId(id);

        when(reservaRepository.findById(id)).thenReturn(Optional.of(reservaEntity));
        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reservaEntity);

        ResponseEntity<ReservaEntity> response = reservaController.cancela(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ReservaStatus.CANCELADO, response.getBody().getStatus());
    }

    @Test
    public void testCancela_NotFound() {
        Long id = 1L;

        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<ReservaEntity> response = reservaController.cancela(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCancela_Exception() {
        Long id = 1L;

        when(reservaRepository.findById(id)).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<ReservaEntity> response = reservaController.cancela(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testFindById_Success() {
        Long id = 1L;
        ReservaEntity reservaEntity = new ReservaEntity();
        reservaEntity.setId(id);

        when(reservaService.findById(id)).thenReturn(reservaEntity);

        ResponseEntity<ReservaEntity> response = reservaController.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservaEntity, response.getBody());
    }

    @Test
    public void testFindById_Exception() {
        Long id = 1L;

        when(reservaService.findById(id)).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<ReservaEntity> response = reservaController.findById(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testFindAll_Success() {
        List<ReservaEntity> reservas = Arrays.asList(new ReservaEntity(), new ReservaEntity());

        when(reservaService.findAll()).thenReturn(reservas);

        ResponseEntity<List<ReservaEntity>> response = reservaController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservas, response.getBody());
    }

    @Test
    public void testFindAll_Exception() {
        when(reservaService.findAll()).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<List<ReservaEntity>> response = reservaController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testAddHospedeToReserva_Success() {
        Long reservaId = 1L;
        Long hospedeId = 2L;
        ReservaEntity reservaEntity = new ReservaEntity();

        when(reservaService.addHospedeToReserva(reservaId, hospedeId)).thenReturn(reservaEntity);

        ResponseEntity<ReservaEntity> response = reservaController.addHospedeToReserva(reservaId, hospedeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservaEntity, response.getBody());
    }

    @Test
    public void testAddHospedeToReserva_Exception() {
        Long reservaId = 1L;
        Long hospedeId = 2L;

        when(reservaService.addHospedeToReserva(reservaId, hospedeId)).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<ReservaEntity> response = reservaController.addHospedeToReserva(reservaId, hospedeId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testRemoveHospedeFromReserva_Success() {
        Long reservaId = 1L;
        Long hospedeId = 2L;
        ReservaEntity reservaEntity = new ReservaEntity();

        when(reservaService.removeHospedeFromReserva(reservaId, hospedeId)).thenReturn(reservaEntity);

        ResponseEntity<ReservaEntity> response = reservaController.removeHospedeFromReserva(reservaId, hospedeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservaEntity, response.getBody());
    }

    @Test
    public void testRemoveHospedeFromReserva_Exception() {
        Long reservaId = 1L;
        Long hospedeId = 2L;

        when(reservaService.removeHospedeFromReserva(reservaId, hospedeId)).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<ReservaEntity> response = reservaController.removeHospedeFromReserva(reservaId, hospedeId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testRealizarCheckIn_Success() {
        Long id = 1L;
        ReservaEntity reservaEntity = new ReservaEntity();

        when(reservaService.realizarCheckIn(id)).thenReturn(reservaEntity);

        ResponseEntity<ReservaEntity> response = reservaController.realizarCheckIn(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservaEntity, response.getBody());
    }

    @Test
    public void testRealizarCheckIn_NotFound() {
        Long id = 1L;

        when(reservaService.realizarCheckIn(id)).thenThrow(new IllegalArgumentException("Test exception"));

        ResponseEntity<ReservaEntity> response = reservaController.realizarCheckIn(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testRealizarCheckIn_BadRequest() {
        Long id = 1L;

        when(reservaService.realizarCheckIn(id)).thenThrow(new IllegalStateException("Test exception"));

        ResponseEntity<ReservaEntity> response = reservaController.realizarCheckIn(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testRealizarCheckIn_InternalServerError() {
        Long id = 1L;

        when(reservaService.realizarCheckIn(id)).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<ReservaEntity> response = reservaController.realizarCheckIn(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testRealizarCheckOut_Success() {
        Long id = 1L;
        ReservaEntity reservaEntity = new ReservaEntity();

        when(reservaService.realizarCheckOut(id)).thenReturn(reservaEntity);

        ResponseEntity<ReservaEntity> response = reservaController.realizarCheckOut(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservaEntity, response.getBody());
    }

    @Test
    public void testRealizarCheckOut_NotFound() {
        Long id = 1L;

        when(reservaService.realizarCheckOut(id)).thenThrow(new IllegalArgumentException("Test exception"));

        ResponseEntity<ReservaEntity> response = reservaController.realizarCheckOut(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testRealizarCheckOut_BadRequest() {
        Long id = 1L;

        when(reservaService.realizarCheckOut(id)).thenThrow(new IllegalStateException("Test exception"));

        ResponseEntity<ReservaEntity> response = reservaController.realizarCheckOut(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testRealizarCheckOut_InternalServerError() {
        Long id = 1L;

        when(reservaService.realizarCheckOut(id)).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<ReservaEntity> response = reservaController.realizarCheckOut(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}