package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.QuartoEntity;
import com.HotelTremvago.HotelTremvago.entities.TipoQuartoEntity;
import com.HotelTremvago.HotelTremvago.services.QuartoService;
import com.HotelTremvago.HotelTremvago.services.TipoQuartoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class QuartoControllerTest {
    @Autowired
    private QuartoController quartoController;
    @MockBean
    private QuartoService quartoService;
    @MockBean
    private TipoQuartoService tipoQuartoService;

    @Test
    public void testCriarQuarto() {
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        quarto.setTipoQuarto(tipoQuarto);

        when(quartoService.criarQuarto(any(QuartoEntity.class))).thenReturn(quarto);
        when(tipoQuartoService.findById(1L)).thenReturn(tipoQuarto);

        ResponseEntity<QuartoEntity> response = quartoController.criarQuarto(quarto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quarto, response.getBody());
    }


    @Test
    public void testDelete() {
        when(quartoService.delete(1L)).thenReturn("Quarto deleted");

        ResponseEntity<String> response = quartoController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Quarto deleted", response.getBody());
    }

    @Test
    public void testDeleteException() {
        when(quartoService.delete(1L)).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<String> response = quartoController.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdate() {
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        when(quartoService.update(any(QuartoEntity.class), anyLong())).thenReturn(quarto);

        ResponseEntity<QuartoEntity> response = quartoController.update(quarto, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quarto, response.getBody());
    }

    @Test
    public void testUpdateException() {
        QuartoEntity quarto = new QuartoEntity();
        when(quartoService.update(any(QuartoEntity.class), anyLong())).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<QuartoEntity> response = quartoController.update(quarto, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testFindById() {
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        when(quartoService.findById(1L)).thenReturn(quarto);

        ResponseEntity<QuartoEntity> response = quartoController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quarto, response.getBody());
    }

    @Test
    public void testFindByIdException() {
        when(quartoService.findById(1L)).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<QuartoEntity> response = quartoController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testFindAll() {
        QuartoEntity quarto1 = new QuartoEntity();
        QuartoEntity quarto2 = new QuartoEntity();
        List<QuartoEntity> quartos = Arrays.asList(quarto1, quarto2);
        when(quartoService.findAll()).thenReturn(quartos);

        ResponseEntity<List<QuartoEntity>> response = quartoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quartos, response.getBody());
    }

    @Test
    public void testFindAllException() {
        when(quartoService.findAll()).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<List<QuartoEntity>> response = quartoController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
