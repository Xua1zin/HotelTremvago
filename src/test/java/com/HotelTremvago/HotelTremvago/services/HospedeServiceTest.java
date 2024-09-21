package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.HospedeEntity;
import com.HotelTremvago.HotelTremvago.repositories.HospedeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class HospedeServiceTest {
    @Autowired
    private HospedeService hospedeService;
    @MockBean
    private HospedeRepository hospedeRepository;


    @Test
    void testSaveSuccess() {
        HospedeEntity hospede = new HospedeEntity();
        when(hospedeRepository.save(hospede)).thenReturn(hospede);

        HospedeEntity result = hospedeService.save(hospede);

        assertNotNull(result);
        verify(hospedeRepository, times(1)).save(hospede);
    }

    @Test
    void testSaveFailure() {
        HospedeEntity hospede = new HospedeEntity();
        when(hospedeRepository.save(hospede)).thenThrow(new IllegalArgumentException("Erro ao salvar"));

        HospedeEntity result = hospedeService.save(hospede);

        assertNotNull(result);
        verify(hospedeRepository, times(1)).save(hospede);
    }

    @Test
    void testDeleteSuccess() {
        Long id = 1L;

        String result = hospedeService.delete(id);

        assertEquals("Hospede deletado", result);
        verify(hospedeRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteFailure() {
        Long id = 1L;
        doThrow(new RuntimeException("Erro ao deletar")).when(hospedeRepository).deleteById(id);

        String result = hospedeService.delete(id);

        assertEquals("Nao foi possivel deletar hospede", result);
        verify(hospedeRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdateSuccess() {
        Long id = 1L;
        HospedeEntity hospede = new HospedeEntity();
        hospede.setId(id);
        when(hospedeRepository.save(hospede)).thenReturn(hospede);

        HospedeEntity result = hospedeService.update(hospede, id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(hospedeRepository, times(1)).save(hospede);
    }

    @Test
    void testUpdateFailure() {
        Long id = 1L;
        HospedeEntity hospede = new HospedeEntity();
        when(hospedeRepository.save(hospede)).thenThrow(new RuntimeException("Erro ao atualizar"));

        HospedeEntity result = hospedeService.update(hospede, id);

        assertNotNull(result);
        verify(hospedeRepository, times(1)).save(hospede);
    }

    @Test
    void testFindByIdSuccess() {
        Long id = 1L;
        HospedeEntity hospede = new HospedeEntity();
        when(hospedeRepository.findById(id)).thenReturn(Optional.of(hospede));

        HospedeEntity result = hospedeService.findById(id);

        assertNotNull(result);
        verify(hospedeRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdFailure() {
        Long id = 1L;
        HospedeEntity hospede = new HospedeEntity();
        when(hospedeRepository.findById(id)).thenReturn(Optional.of(hospede))
                .thenThrow(new IllegalArgumentException("Hospede não encontrado"));

        HospedeEntity result = hospedeService.findById(2L);

        assertNotNull(result);
        assertNotEquals(result.getId(), id);
        verify(hospedeRepository, times(1)).findById(2L);
    }

    @Test
    void testFindAllSuccess() {
        when(hospedeRepository.findAll()).thenReturn(Collections.singletonList(new HospedeEntity()));

        List<HospedeEntity> result = hospedeService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(hospedeRepository, times(1)).findAll();
    }

    @Test
    void testFindAllFailure() {
        when(hospedeRepository.findAll()).thenThrow(new RuntimeException("Erro ao encontrar lista"));

        List<HospedeEntity> result = hospedeService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(hospedeRepository, times(1)).findAll();
    }
}
