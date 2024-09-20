package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.HotelEntity;
import com.HotelTremvago.HotelTremvago.services.HotelService;
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
class HotelControllerTest {

    @Autowired
    private HotelController hotelController;

    @MockBean
    private HotelService hotelService;

    private HotelEntity hotelEntity;
    private List<HotelEntity> hotelList;

    @BeforeEach
    void setUp() {
        hotelEntity = new HotelEntity();
        hotelEntity.setId(1L);
        hotelEntity.setNomeFantasia("Hotel Test");

        hotelList = Arrays.asList(hotelEntity);
    }

    @Test
    void testSave_Success() {
        when(hotelService.save(any(HotelEntity.class))).thenReturn(hotelEntity);

        ResponseEntity<HotelEntity> response = hotelController.save(hotelEntity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotelEntity, response.getBody());
    }

    @Test
    void testSave_Failure() {
        when(hotelService.save(any(HotelEntity.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<HotelEntity> response = hotelController.save(hotelEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDelete_Success() {
        when(hotelService.delete(anyLong())).thenReturn("Hotel deletado com sucesso");

        ResponseEntity<String> response = hotelController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hotel deletado com sucesso", response.getBody());
    }

    @Test
    void testDelete_Failure() {
        when(hotelService.delete(anyLong())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<String> response = hotelController.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Não foi possível deletar hotel", response.getBody());
    }

    @Test
    void testUpdate_Success() {
        when(hotelService.update(any(HotelEntity.class), anyLong())).thenReturn(hotelEntity);

        ResponseEntity<HotelEntity> response = hotelController.update(hotelEntity, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotelEntity, response.getBody());
    }

    @Test
    void testUpdate_Failure() {
        when(hotelService.update(any(HotelEntity.class), anyLong())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<HotelEntity> response = hotelController.update(hotelEntity, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindById_Success() {
        when(hotelService.findById(anyLong())).thenReturn(hotelEntity);

        ResponseEntity<HotelEntity> response = hotelController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotelEntity, response.getBody());
    }

    @Test
    void testFindById_Failure() {
        when(hotelService.findById(anyLong())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<HotelEntity> response = hotelController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindAll_Success() {
        when(hotelService.findAll()).thenReturn(hotelList);

        ResponseEntity<List<HotelEntity>> response = hotelController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotelList, response.getBody());
    }

    @Test
    void testFindAll_Failure() {
        when(hotelService.findAll()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<List<HotelEntity>> response = hotelController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testSaveAll_Success() {
        when(hotelService.saveAll(anyList())).thenReturn(hotelList);

        ResponseEntity<List<HotelEntity>> response = hotelController.saveAll(hotelList);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotelList, response.getBody());
    }

    @Test
    void testSaveAll_Failure() {
        when(hotelService.saveAll(anyList())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<List<HotelEntity>> response = hotelController.saveAll(hotelList);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}