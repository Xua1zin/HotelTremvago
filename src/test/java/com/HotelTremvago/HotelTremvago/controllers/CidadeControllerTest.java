package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.CidadeEntity;
import com.HotelTremvago.HotelTremvago.services.CidadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CidadeControllerTest {

    @MockBean
    private CidadeService cidadeService;

    @InjectMocks
    private CidadeController cidadeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        CidadeEntity cidade = new CidadeEntity();
        cidade.setId(1L);
        when(cidadeService.save(any(CidadeEntity.class))).thenReturn(cidade);
        ResponseEntity<CidadeEntity> response = cidadeController.save(cidade);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cidade, response.getBody());
    }

    @Test
    public void testSaveAll() {
        CidadeEntity cidade1 = new CidadeEntity();
        CidadeEntity cidade2 = new CidadeEntity();
        List<CidadeEntity> cidades = Arrays.asList(cidade1, cidade2);
        when(cidadeService.saveAll(anyList())).thenReturn(cidades);

        ResponseEntity<List<CidadeEntity>> response = cidadeController.saveAll(cidades);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cidades, response.getBody());
    }

    @Test
    public void testFindById() {
        CidadeEntity cidade = new CidadeEntity();
        cidade.setId(1L);
        when(cidadeService.findById(1L)).thenReturn(cidade);

        ResponseEntity<CidadeEntity> response = cidadeController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cidade, response.getBody());
    }


    @Test
    public void testFindAll() {
        CidadeEntity cidade1 = new CidadeEntity();
        CidadeEntity cidade2 = new CidadeEntity();
        List<CidadeEntity> cidades = Arrays.asList(cidade1, cidade2);
        when(cidadeService.findAll()).thenReturn(cidades);

        ResponseEntity<List<CidadeEntity>> response = cidadeController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cidades, response.getBody());
    }

    @Test
    public void testFindAll_EmptyList() {
        when(cidadeService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<CidadeEntity>> response = cidadeController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    public void testFindByNome() {
        CidadeEntity cidade = new CidadeEntity();
        List<CidadeEntity> cidades = Arrays.asList(cidade);
        when(cidadeService.findByNome(anyString())).thenReturn(cidades);

        ResponseEntity<List<CidadeEntity>> response = cidadeController.findByNome("TestNome");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cidades, response.getBody());
    }

    @Test
    public void testFindByEstado() {
        CidadeEntity cidade = new CidadeEntity();
        List<CidadeEntity> cidades = Arrays.asList(cidade);
        when(cidadeService.findByEstado(anyString())).thenReturn(cidades);

        ResponseEntity<List<CidadeEntity>> response = cidadeController.findByEstado("TestEstado");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cidades, response.getBody());
    }


//    @Test
//    public void testFindByNomeandCidade() {
//        CidadeEntity cidade = new CidadeEntity();
//        List<CidadeEntity> cidades = List.of(cidade);
//        when(cidadeService.findByNomeAndEstado(anyString(), anyString())).thenReturn(cidades);
//
//        ResponseEntity<List<CidadeEntity>> response = cidadeController.findByNomeAndCidade("TestNome", "TestCidade");
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(cidades, response.getBody());
//    }

//    @Test
//    public void testFindByNomeAndEstado_NotFound() {
//        when(cidadeService.findByNomeAndEstado(anyString(), anyString())).thenReturn(Collections.emptyList());
//
//        ResponseEntity<List<CidadeEntity>> response = cidadeController.findByNomeAndEstado("TestNome", "TestEstado");
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals(Collections.emptyList(), response.getBody());
//    }

    @Test
    public void testSaveException() {
        CidadeEntity cidade = new CidadeEntity();
        when(cidadeService.save(any(CidadeEntity.class))).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<CidadeEntity> response = cidadeController.save(cidade);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
