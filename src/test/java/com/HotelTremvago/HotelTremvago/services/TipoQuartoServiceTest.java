package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.TipoQuartoEntity;
import com.HotelTremvago.HotelTremvago.repositories.TipoQuartoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TipoQuartoServiceTest {
    @Autowired
    TipoQuartoService tipoQuartoService;
    @MockBean
    TipoQuartoRepository tipoQuartoRepository;

    @Test
    void testSaveSuccess() {
        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        when(tipoQuartoRepository.save(tipoQuarto)).thenReturn(tipoQuarto);

        TipoQuartoEntity result = tipoQuartoService.save(tipoQuarto);

        assertNotNull(result);
        assertEquals(result, tipoQuarto);
        verify(tipoQuartoRepository).save(tipoQuarto);
    }

    @Test
    void testSaveFailure() {
        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        when(tipoQuartoRepository.save(tipoQuarto)).thenThrow(new IllegalArgumentException("Erro ao salvar tipoQuarto"));

        TipoQuartoEntity result = tipoQuartoService.save(tipoQuarto);

        assertNotNull(result);
        assertNotEquals(result, tipoQuarto);
        verify(tipoQuartoRepository).save(tipoQuarto);
    }

    @Test
    void testDeleteSuccess() {
        Long id = 1L;
        doNothing().when(tipoQuartoRepository).deleteById(id);

        String result = tipoQuartoService.delete(id);

        assertEquals("Tipo de quarto deletado", result);
        verify(tipoQuartoRepository).deleteById(id);
    }

    @Test
    void testDeleteFailure() {
        Long id = 1L;
        doThrow(new IllegalArgumentException("Erro ao deletar")).when(tipoQuartoRepository).deleteById(id);
        String result = tipoQuartoService.delete(id);

        assertEquals("Não foi possível deletar tipo de quarto", result);
        verify(tipoQuartoRepository).deleteById(id);
    }

    @Test
    void testUpdateSuccess() {
        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        tipoQuarto.setNome("Standard");

        TipoQuartoEntity updatedTipoQuarto = new TipoQuartoEntity();
        updatedTipoQuarto.setNome("Deluxe");

        when(tipoQuartoRepository.findById(1L)).thenReturn(Optional.of(tipoQuarto));
        when(tipoQuartoRepository.save(tipoQuarto)).thenReturn(tipoQuarto);

        TipoQuartoEntity result = tipoQuartoService.update(updatedTipoQuarto, 1L);

        assertNotNull(result);
        assertEquals(result, tipoQuarto);
        verify(tipoQuartoRepository).findById(1L);
        verify(tipoQuartoRepository).save(tipoQuarto);
    }

    @Test
    void testUpdateFailure() {
        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        tipoQuarto.setNome("Standard");

        TipoQuartoEntity updatedTipoQuarto = new TipoQuartoEntity();
        updatedTipoQuarto.setNome("Deluxe");

        when(tipoQuartoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            tipoQuartoService.update(updatedTipoQuarto, 1L);
        });

        assertEquals("TipoQuarto not found with id " + tipoQuarto.getId(), exception.getMessage());
        verify(tipoQuartoRepository).findById(1L);
    }

    @Test
    void testFindByIdSuccess() {
        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        when(tipoQuartoRepository.findById(tipoQuarto.getId())).thenReturn(Optional.of(tipoQuarto));

        TipoQuartoEntity result = tipoQuartoService.findById(tipoQuarto.getId());

        assertNotNull(result);
        assertEquals(result, tipoQuarto);
        verify(tipoQuartoRepository).findById(tipoQuarto.getId());
    }

    @Test
    void testFindByIdFailure() {
        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        when(tipoQuartoRepository.findById(tipoQuarto.getId())).thenReturn(Optional.empty());

        TipoQuartoEntity result = tipoQuartoService.findById(tipoQuarto.getId());

        assertNotEquals(result, tipoQuarto);
        verify(tipoQuartoRepository).findById(tipoQuarto.getId());
    }

    @Test
    void testFindAllSuccess() {
        List<TipoQuartoEntity> tipoQuartos = List.of(new TipoQuartoEntity(), new TipoQuartoEntity());
        when(tipoQuartoRepository.findAll()).thenReturn(tipoQuartos);

        List<TipoQuartoEntity> result = tipoQuartoService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tipoQuartoRepository).findAll();
    }

    @Test
    void testFindAllFailure() {
        List<TipoQuartoEntity> tipoQuartoEntities = List.of(new TipoQuartoEntity(), new TipoQuartoEntity());
        when(tipoQuartoRepository.findAll()).thenThrow(new IllegalArgumentException("Erro ao encontrar tipos de quartos"));

        List<TipoQuartoEntity> result = tipoQuartoService.findAll();

        assertNotNull(result);
        assertNotEquals(result, tipoQuartoEntities);
        verify(tipoQuartoRepository).findAll();
    }
}