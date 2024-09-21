package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.CidadeEntity;
import com.HotelTremvago.HotelTremvago.repositories.CidadeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CidadeServiceTest {
    @Autowired
    CidadeService cidadeService;
    @MockBean
    CidadeRepository cidadeRepository;

    @Test
    void testSaveSuccess() {
        CidadeEntity cidade = new CidadeEntity();
        when(cidadeRepository.save(cidade)).thenReturn(cidade);

        CidadeEntity result = cidadeService.save(cidade);

        assertNotNull(result);
        verify(cidadeRepository, times(1)).save(cidade);
    }

    @Test
    void testSaveFailure() {
        CidadeEntity cidade = new CidadeEntity();
        when(cidadeRepository.save(cidade)).thenThrow(new RuntimeException("Erro ao salvar lista"));

        CidadeEntity result = cidadeService.save(cidade);

        assertNotNull(result);
        verify(cidadeRepository, times(1)).save(cidade);
    }

    @Test
    void testSaveAllSuccess() {
        List<CidadeEntity> cidades = Collections.singletonList(new CidadeEntity());
        when(cidadeRepository.saveAll(cidades)).thenReturn(cidades);

        List<CidadeEntity> result = cidadeService.saveAll(cidades);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(cidadeRepository, times(1)).saveAll(cidades);
    }

    @Test
    void testSaveAllFailure() {
        List<CidadeEntity> cidades = Collections.singletonList(new CidadeEntity());
        when(cidadeRepository.saveAll(cidades)).thenThrow(new RuntimeException("Erro ao salvar lista"));

        List<CidadeEntity> result = cidadeService.saveAll(cidades);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cidadeRepository, times(1)).saveAll(cidades);
    }

    @Test
    void testDeleteSuccess() {
        Long id = 1L;
        String result = cidadeService.delete(id);

        assertEquals("Cidade deletada com sucesso", result);
        verify(cidadeRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteFailure() {
        Long id = 1L;
        doThrow(new RuntimeException("Erro ao deletar")).when(cidadeRepository).deleteById(id);

        String result = cidadeService.delete(id);

        assertEquals("Não foi possível deletar a cidade", result);
        verify(cidadeRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindByIdSuccess() {
        Long id = 1L;
        CidadeEntity cidade = new CidadeEntity();
        when(cidadeRepository.findById(id)).thenReturn(Optional.of(cidade));

        CidadeEntity result = cidadeService.findById(id);

        assertNotNull(result);
        verify(cidadeRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdFailure() {
        Long id = 1L;
        CidadeEntity cidade = new CidadeEntity();
        when(cidadeRepository.findById(id)).thenReturn(Optional.of(cidade)).thenThrow(new IllegalArgumentException("ID não pode ser null"));

        CidadeEntity result = cidadeService.findById(2L);

        assertNotNull(result);
        assertNotEquals(result.getId(), id);
        verify(cidadeRepository, times(1)).findById(2L);
    }

    @Test
    void testFindAllSuccess() {
        List<CidadeEntity> cidades = Collections.singletonList(new CidadeEntity());
        when(cidadeRepository.findAll()).thenReturn(cidades);

        List<CidadeEntity> result = cidadeService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(cidadeRepository, times(1)).findAll();
    }

    @Test
    void testFindAllFailure() {
        when(cidadeRepository.findAll()).thenThrow(new RuntimeException("Erro ao buscar cidades"));

        List<CidadeEntity> result = cidadeService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cidadeRepository, times(1)).findAll();
    }

    @Test
    void testFindByNomeSuccess() {
        String nome = "CidadeX";
        List<CidadeEntity> cidades = Collections.singletonList(new CidadeEntity());
        when(cidadeRepository.findByCidade(nome)).thenReturn(cidades);

        List<CidadeEntity> result = cidadeService.findByNome(nome);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(cidadeRepository, times(1)).findByCidade(nome);
    }

    @Test
    void testFindByNomeFailure() {
        String nome = "Cidade";
        when(cidadeRepository.findByCidade(nome)).thenThrow(new IllegalArgumentException("Erro ao achar cidade"));

        List<CidadeEntity> result = cidadeService.findByNome(nome);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cidadeRepository, times(1)).findByCidade(nome);
    }

    @Test
    void testFindByEstadoSuccess() {
        String estado = "Estado";
        List<CidadeEntity> cidades = Collections.singletonList(new CidadeEntity());
        when(cidadeRepository.findByEstado(estado)).thenReturn(cidades);

        List<CidadeEntity> result = cidadeService.findByEstado(estado);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(cidadeRepository, times(1)).findByEstado(estado);
    }

    @Test
    void testFindByEstadoFailure() {
        String estado = "Estado";
        when(cidadeRepository.findByEstado(estado)).thenThrow(new IllegalArgumentException("Estado não encontrado"));

        List<CidadeEntity> result = cidadeService.findByEstado(estado);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cidadeRepository, times(1)).findByEstado(estado);
    }

    @Test
    void testUpdateSuccess() {
        CidadeEntity cidade = new CidadeEntity();
        cidade.setId(1L);
        when(cidadeRepository.existsById(cidade.getId())).thenReturn(true);
        when(cidadeRepository.save(cidade)).thenReturn(cidade);

        CidadeEntity result = cidadeService.update(cidade, cidade.getId());

        assertNotNull(result);
        assertEquals(cidade.getId(), result.getId());
        verify(cidadeRepository, times(1)).existsById(cidade.getId());
        verify(cidadeRepository, times(1)).save(cidade);
    }

    @Test
    void testUpdateFailure() {
        CidadeEntity cidade = new CidadeEntity();
        cidade.setId(1L);
        when(cidadeRepository.existsById(cidade.getId())).thenReturn(false);

        CidadeEntity result = cidadeService.update(cidade, cidade.getId());

        assertNotNull(result);
        assertNull(result.getId());
        verify(cidadeRepository, times(1)).existsById(cidade.getId());
        verify(cidadeRepository, times(0)).save(cidade);
    }



//    @Test
//    void testFindByNomeAndEstadoSuccess() {
//        String nome = "CidadeX";
//        String estado = "EstadoY";
//        List<CidadeEntity> cidades = Collections.singletonList(new CidadeEntity());
//        when(cidadeRepository.findByCidadeAndEstado(nome, estado)).thenReturn(cidades);
//
//        List<CidadeEntity> result = cidadeService.findByNomeAndEstado(nome, estado);
//
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//        verify(cidadeRepository, times(1)).findByCidadeAndEstado(nome, estado);
//    }
}
