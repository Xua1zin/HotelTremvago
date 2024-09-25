package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.QuartoEntity;
import com.HotelTremvago.HotelTremvago.services.QuartoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
public class QuartoControllerTest {

    @Autowired
    private QuartoController quartoController;

    @MockBean
    private QuartoService quartoService;

    private QuartoEntity quarto;

    @BeforeEach
    public void setUp() {
        quarto = new QuartoEntity();
        quarto.setId(1L);
        quarto.setNome("Quarto Luxo");
    }

    @Test
    public void testCriarQuarto_Success() {
        Mockito.when(quartoService.criarQuarto(any(QuartoEntity.class))).thenReturn(quarto);

        ResponseEntity<QuartoEntity> response = quartoController.criarQuarto(quarto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quarto, response.getBody());
    }

    @Test
    public void testCriarQuarto_Failure() {
        Mockito.when(quartoService.criarQuarto(any(QuartoEntity.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<QuartoEntity> response = quartoController.criarQuarto(quarto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDelete_Success() {
        Mockito.when(quartoService.delete(anyLong())).thenReturn("Quarto deletado com sucesso");

        ResponseEntity<String> response = quartoController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Quarto deletado com sucesso", response.getBody());
    }

    @Test
    public void testDelete_Failure() {
        Mockito.when(quartoService.delete(anyLong())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<String> response = quartoController.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdate_Success() {
        QuartoEntity updatedQuarto = new QuartoEntity();
        updatedQuarto.setId(1L);
        updatedQuarto.setNome("Quarto Atualizado");

        Mockito.when(quartoService.update(any(QuartoEntity.class), anyLong())).thenReturn(updatedQuarto);

        ResponseEntity<QuartoEntity> response = quartoController.update(quarto, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedQuarto, response.getBody());
    }

    @Test
    public void testUpdate_Failure() {
        Mockito.when(quartoService.update(any(QuartoEntity.class), anyLong())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<QuartoEntity> response = quartoController.update(quarto, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindById_Success() {
        Mockito.when(quartoService.findById(1L)).thenReturn(quarto);

        ResponseEntity<QuartoEntity> response = quartoController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quarto, response.getBody());
    }

    @Test
    public void testFindById_Failure() {
        Mockito.when(quartoService.findById(1L)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<QuartoEntity> response = quartoController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindAll_Success() {
        QuartoEntity quarto1 = new QuartoEntity();
        quarto1.setId(1L);
        quarto1.setNome("Quarto Luxo");

        QuartoEntity quarto2 = new QuartoEntity();
        quarto2.setId(2L);
        quarto2.setNome("Quarto Simples");

        List<QuartoEntity> quartos = Arrays.asList(quarto1, quarto2);
        Mockito.when(quartoService.findAll()).thenReturn(quartos);

        ResponseEntity<List<QuartoEntity>> response = quartoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quartos, response.getBody());
    }

    @Test
    public void testFindAll_Failure() {
        Mockito.when(quartoService.findAll()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<List<QuartoEntity>> response = quartoController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
