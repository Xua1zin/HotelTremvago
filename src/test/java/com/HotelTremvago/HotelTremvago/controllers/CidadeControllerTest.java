package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.CidadeEntity;
import com.HotelTremvago.HotelTremvago.services.CidadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CidadeControllerTest {

    @Autowired
    private CidadeController cidadeController;

    @MockBean
    private CidadeService cidadeService;

    private CidadeEntity cidadeEntity;
    private List<CidadeEntity> cidadeList;

    @BeforeEach
    void setUp() {
        cidadeEntity = new CidadeEntity();
        cidadeEntity.setId(1L);
        cidadeEntity.setCidade("São Paulo");
        cidadeEntity.setEstado("SP");

        cidadeList = Arrays.asList(cidadeEntity);
    }

    @Test
    void testSave_Success() {
        when(cidadeService.save(any(CidadeEntity.class))).thenReturn(cidadeEntity);

        ResponseEntity<CidadeEntity> response = cidadeController.save(cidadeEntity);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cidadeEntity, response.getBody());
    }

    @Test
    void testSave_Failure() {
        when(cidadeService.save(any(CidadeEntity.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<CidadeEntity> response = cidadeController.save(cidadeEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testSaveAll_Success() {
        when(cidadeService.saveAll(anyList())).thenReturn(cidadeList);

        ResponseEntity<List<CidadeEntity>> response = cidadeController.saveAll(cidadeList);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cidadeList, response.getBody());
    }

    @Test
    void testSaveAll_Failure() {
        when(cidadeService.saveAll(anyList())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<List<CidadeEntity>> response = cidadeController.saveAll(cidadeList);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindById_Success() {
        when(cidadeService.findById(anyLong())).thenReturn(cidadeEntity);

        ResponseEntity<CidadeEntity> response = cidadeController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cidadeEntity, response.getBody());
    }

    @Test
    void testFindById_NotFound() {
        when(cidadeService.findById(anyLong())).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<CidadeEntity> response = cidadeController.findById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindAll_Success() {
        when(cidadeService.findAll()).thenReturn(cidadeList);

        ResponseEntity<List<CidadeEntity>> response = cidadeController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cidadeList, response.getBody());
    }

    @Test
    void testFindAll_Failure() {
        when(cidadeService.findAll()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<List<CidadeEntity>> response = cidadeController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindByNome_Success() {
        when(cidadeService.findByNome(anyString())).thenReturn(cidadeList);

        ResponseEntity<List<CidadeEntity>> response = cidadeController.findByNome("São Paulo");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cidadeList, response.getBody());
    }

    @Test
    void testFindByNome_Failure() {
        when(cidadeService.findByNome(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<List<CidadeEntity>> response = cidadeController.findByNome("São Paulo");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindByEstado_Success() {
        when(cidadeService.findByEstado(anyString())).thenReturn(cidadeList);

        ResponseEntity<List<CidadeEntity>> response = cidadeController.findByEstado("SP");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cidadeList, response.getBody());
    }

    @Test
    void testFindByEstado_Failure() {
        when(cidadeService.findByEstado(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<List<CidadeEntity>> response = cidadeController.findByEstado("SP");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}