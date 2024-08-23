package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.HospedeEntity;
import com.HotelTremvago.HotelTremvago.services.HospedeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HospedeControllerTest {

    @Mock
    private HospedeService hospedeService;

    @InjectMocks
    private HospedeController hospedeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        HospedeEntity hospede = new HospedeEntity();
        hospede.setId(1L);
        when(hospedeService.save(any(HospedeEntity.class))).thenReturn(hospede);

        ResponseEntity<HospedeEntity> response = hospedeController.save(hospede);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hospede, response.getBody());
    }

    @Test
    public void testDelete() {
        when(hospedeService.delete(1L)).thenReturn("Hospede deleted");

        ResponseEntity<String> response = hospedeController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hospede deleted", response.getBody());
    }

    @Test
    public void testUpdate() {
        HospedeEntity hospede = new HospedeEntity();
        hospede.setId(1L);
        when(hospedeService.update(any(HospedeEntity.class), anyLong())).thenReturn(hospede);

        ResponseEntity<HospedeEntity> response = hospedeController.update(hospede, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hospede, response.getBody());
    }

    @Test
    public void testFindById() {
        HospedeEntity hospede = new HospedeEntity();
        hospede.setId(1L);
        when(hospedeService.findById(1L)).thenReturn(hospede);

        ResponseEntity<HospedeEntity> response = hospedeController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hospede, response.getBody());
    }

    @Test
    public void testFindById_NotFound() {
        when(hospedeService.findById(anyLong())).thenReturn(null);

        ResponseEntity<HospedeEntity> response = hospedeController.findById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testFindAll() {
        HospedeEntity hospede1 = new HospedeEntity();
        HospedeEntity hospede2 = new HospedeEntity();
        List<HospedeEntity> hospedes = Arrays.asList(hospede1, hospede2);
        when(hospedeService.findAll()).thenReturn(hospedes);

        ResponseEntity<List<HospedeEntity>> response = hospedeController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hospedes, response.getBody());
    }

    @Test
    public void testFindAll_EmptyList() {
        when(hospedeService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<HospedeEntity>> response = hospedeController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    public void testSaveException() {
        HospedeEntity hospede = new HospedeEntity();
        when(hospedeService.save(any(HospedeEntity.class))).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<HospedeEntity> response = hospedeController.save(hospede);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteException() {
        when(hospedeService.delete(anyLong())).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<String> response = hospedeController.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateException() {
        HospedeEntity hospede = new HospedeEntity();
        when(hospedeService.update(any(HospedeEntity.class), anyLong())).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<HospedeEntity> response = hospedeController.update(hospede, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testFindByIdException() {
        when(hospedeService.findById(anyLong())).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<HospedeEntity> response = hospedeController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testFindAllException() {
        when(hospedeService.findAll()).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<List<HospedeEntity>> response = hospedeController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateWithInvalidData() {
        HospedeEntity hospede = new HospedeEntity();
        hospede.setId(1L);
        when(hospedeService.update(any(HospedeEntity.class), anyLong())).thenThrow(new IllegalArgumentException("Dados inválidos"));

        ResponseEntity<HospedeEntity> response = hospedeController.update(hospede, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testSaveWithInvalidData() {
        HospedeEntity hospede = new HospedeEntity();
        when(hospedeService.save(any(HospedeEntity.class))).thenThrow(new IllegalArgumentException("Dados inválidos"));

        ResponseEntity<HospedeEntity> response = hospedeController.save(hospede);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteNotFound() {
        when(hospedeService.delete(anyLong())).thenThrow(new IllegalArgumentException("ID não encontrado"));

        ResponseEntity<String> response = hospedeController.delete(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ID não encontrado", response.getBody());
    }
}
