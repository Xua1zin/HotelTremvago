package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.controllers.CidadeController;
import com.HotelTremvago.HotelTremvago.entities.CidadeEntity;
import com.HotelTremvago.HotelTremvago.entities.HotelEntity;
import com.HotelTremvago.HotelTremvago.repositories.CidadeRepository;
import com.HotelTremvago.HotelTremvago.repositories.HotelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class HotelServiceTest {
    @Autowired
    HotelService hotelService;
    @MockBean
    HotelRepository hotelRepository;
    @MockBean
    CidadeRepository cidadeRepository;

    @Test
    public void testSaveSuccess() {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1L);
        CidadeEntity cidade = new CidadeEntity();
        cidade.setId(1L);
        hotel.setCidade(cidade);
        when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        HotelEntity result = hotelService.save(hotel);

        assertNotNull(result);
        assertEquals(result, hotel);
        verify(hotelRepository).save(hotel);
        verify(cidadeRepository).findById(1L);
    }

    @Test
    public void testSaveFailure() {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1L);
        CidadeEntity cidade = new CidadeEntity();
        cidade.setId(1L);
        hotel.setCidade(cidade);
        when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));
        when(hotelRepository.save(hotel)).thenThrow(new IllegalArgumentException("Erro ao salvar hotel"));

        HotelEntity result = hotelService.save(hotel);

        assertNotNull(result);
        assertNotEquals(hotel, result);
        verify(cidadeRepository).findById(1L);
        verify(hotelRepository).save(hotel);
    }

    @Test
    public void testSaveCidadeNotFound() {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1L);
        CidadeEntity cidade = new CidadeEntity();
        cidade.setId(2L);
        hotel.setCidade(cidade);
        when(cidadeRepository.findById(cidade.getId())).thenReturn(Optional.empty());

        HotelEntity result = hotelService.save(hotel);

        assertNotNull(result);
        assertNotEquals(hotel, result);
        verify(cidadeRepository).findById(cidade.getId());
        verify(hotelRepository, times(0)).save(any(HotelEntity.class));
    }



    @Test
    public void testDeleteSuccess() {
        Long id = 1L;
        doNothing().when(hotelRepository).deleteById(id);

        String result = hotelService.delete(id);

        assertEquals("Hotel deletado", result);
        verify(hotelRepository).deleteById(id);
    }

    @Test
    public void testDeleteFailure() {
        Long id = 1L;
        doThrow(new IllegalArgumentException("Erro ao deletar hotel")).when(hotelRepository).deleteById(id);

        String result = hotelService.delete(id);

        assertEquals("Nao foi possivel deletar hotel", result);
        verify(hotelRepository).deleteById(id);
    }


    @Test
    public void testSaveAll() {
        List<HotelEntity> hotels = List.of(new HotelEntity(), new HotelEntity());
        when(hotelRepository.saveAll(any(List.class))).thenReturn(hotels);

        List<HotelEntity> result = hotelService.saveAll(hotels);

        assertEquals(hotels.size(), result.size());
        verify(hotelRepository).saveAll(hotels);
    }

    @Test
    public void testUpdateSuccess() {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1L);
        hotel.setNomeFantasia("Hotel");

        CidadeEntity cidade = new CidadeEntity();
        cidade.setId(1L);

        HotelEntity updatedHotel = new HotelEntity();
        updatedHotel.setCidade(cidade);
        updatedHotel.setNomeFantasia("Hotel1");

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        HotelEntity result = hotelService.update(updatedHotel, 1L);

        assertNotNull(result);
        assertEquals(hotel, result);
        verify(hotelRepository).findById(1L);
        verify(cidadeRepository).findById(1L);
        verify(hotelRepository).save(hotel);
    }

    @Test
    public void testUpdateCidadeNull() {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1L);
        CidadeEntity cidade = null;
        hotel.setCidade(cidade);

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        HotelEntity result = hotelService.update(hotel, 1L);

        assertNotNull(result);
        assertEquals(hotel, result);
        verify(hotelRepository).findById(1L);
        verify(cidadeRepository, times(0)).findById(anyLong());
        verify(hotelRepository).save(hotel);
    }

    @Test
    public void testUpdateCidadeNotFound() {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1L);
        hotel.setNomeFantasia("Hotel");

        CidadeEntity cidade = new CidadeEntity();
        cidade.setId(2L);

        HotelEntity updatedHotel = new HotelEntity();
        updatedHotel.setCidade(cidade);
        updatedHotel.setNomeFantasia("Hotel1");

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(cidadeRepository.findById(cidade.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            hotelService.update(updatedHotel, 1L);
        });

        assertEquals("Cidade not found with id " + cidade.getId(), exception.getMessage());
        verify(hotelRepository).findById(1L);
        verify(cidadeRepository).findById(cidade.getId());
        verify(hotelRepository, times(0)).save(any(HotelEntity.class));
    }


    @Test
    public void testUpdateHotelNotFound() {
        HotelEntity updatedHotel = new HotelEntity();
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            hotelService.update(updatedHotel, 1L);
        });

        assertEquals("Hotel not found with id 1", exception.getMessage());
        verify(hotelRepository).findById(1L);
        verify(cidadeRepository, times(0)).findById(anyLong());
    }

    @Test
    public void testFindByIdSuccess() {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1L);
        when(hotelRepository.findById(hotel.getId())).thenReturn(Optional.of(hotel));

        HotelEntity result = hotelService.findById(hotel.getId());

        assertNotNull(result);
        assertEquals(result, hotel);
        verify(hotelRepository).findById(hotel.getId());
    }

    @Test
    public void testFindByIdFailure() {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1L);
        when(hotelRepository.findById(hotel.getId()))
                .thenThrow(new IllegalArgumentException("Erro ao encontrar hotel"));

        HotelEntity result = hotelService.findById(hotel.getId());

        assertNotNull(result);
        assertNotEquals(result, hotel);
        verify(hotelRepository).findById(hotel.getId());
    }

    @Test
    public void testFindAllSuccess() {
        List<HotelEntity> hoteis = List.of(new HotelEntity(), new HotelEntity());
        when(hotelRepository.findAll()).thenReturn(hoteis);

        List<HotelEntity> result = hotelService.findAll();

        assertEquals(hoteis.size(), result.size());
        verify(hotelRepository).findAll();
    }

    @Test
    public void testFindAllFailure() {
        List<HotelEntity> hoteis = List.of(new HotelEntity(), new HotelEntity());
        when(hotelRepository.findAll()).thenThrow(new IllegalArgumentException("Erro ao encontrar hoteis"));

        List<HotelEntity> result = hotelService.findAll();

        assertNotEquals(hoteis.size(), result.size());
        verify(hotelRepository).findAll();
    }
}
