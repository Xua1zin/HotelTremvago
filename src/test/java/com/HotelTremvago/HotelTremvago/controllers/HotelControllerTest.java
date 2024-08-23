package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.CidadeEntity;
import com.HotelTremvago.HotelTremvago.entities.HotelEntity;
import com.HotelTremvago.HotelTremvago.repositories.CidadeRepository;
import com.HotelTremvago.HotelTremvago.services.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
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
public class HotelControllerTest {

    @MockBean
    private HotelService hotelService;

    @MockBean
    private CidadeRepository cidadeRepository;

    @Autowired
    private HotelController hotelController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1L);
        CidadeEntity cidade = new CidadeEntity();
        cidade.setId(1L);
        hotel.setCidade(cidade);

        when(hotelService.save(any(HotelEntity.class))).thenReturn(hotel);
        when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));

        ResponseEntity<HotelEntity> response = hotelController.save(hotel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotel, response.getBody());
    }

    @Test
    public void testSaveCidadeNotFound() {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1L);
        CidadeEntity cidade = new CidadeEntity();
        cidade.setId(1L);
        hotel.setCidade(cidade);

        when(hotelService.save(any(HotelEntity.class))).thenReturn(hotel);
        when(cidadeRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<HotelEntity> response = hotelController.save(hotel);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDelete() {
        when(hotelService.delete(1L)).thenReturn("Hotel deleted");

        ResponseEntity<String> response = hotelController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hotel deleted", response.getBody());
    }

    @Test
    public void testDeleteException() {
        when(hotelService.delete(1L)).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<String> response = hotelController.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Não foi possível deletar hotel", response.getBody());
    }

    @Test
    public void testUpdate() {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1L);
        when(hotelService.update(any(HotelEntity.class), anyLong())).thenReturn(hotel);

        ResponseEntity<HotelEntity> response = hotelController.update(hotel, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotel, response.getBody());
    }

    @Test
    public void testUpdateException() {
        HotelEntity hotel = new HotelEntity();
        when(hotelService.update(any(HotelEntity.class), anyLong())).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<HotelEntity> response = hotelController.update(hotel, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testFindById() {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1L);
        when(hotelService.findById(1L)).thenReturn(hotel);

        ResponseEntity<HotelEntity> response = hotelController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotel, response.getBody());
    }

    @Test
    public void testFindByIdException() {
        when(hotelService.findById(1L)).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<HotelEntity> response = hotelController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testFindAll() {
        HotelEntity hotel1 = new HotelEntity();
        HotelEntity hotel2 = new HotelEntity();
        List<HotelEntity> hotels = Arrays.asList(hotel1, hotel2);
        when(hotelService.findAll()).thenReturn(hotels);

        ResponseEntity<List<HotelEntity>> response = hotelController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotels, response.getBody());
    }

    @Test
    public void testFindAllException() {
        when(hotelService.findAll()).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<List<HotelEntity>> response = hotelController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testSaveAll() {
        HotelEntity hotel1 = new HotelEntity();
        HotelEntity hotel2 = new HotelEntity();
        List<HotelEntity> hotels = Arrays.asList(hotel1, hotel2);
        when(hotelService.saveAll(anyList())).thenReturn(hotels);

        ResponseEntity<List<HotelEntity>> response = hotelController.saveAll(hotels);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotels, response.getBody());
    }

    @Test
    public void testSaveAllException() {
        when(hotelService.saveAll(anyList())).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<List<HotelEntity>> response = hotelController.saveAll(Arrays.asList(new HotelEntity()));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
