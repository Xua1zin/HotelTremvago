package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.QuartoEntity;
import com.HotelTremvago.HotelTremvago.entities.ReservaEntity;
import com.HotelTremvago.HotelTremvago.entities.TipoQuartoEntity;
import com.HotelTremvago.HotelTremvago.repositories.QuartoRepository;
import com.HotelTremvago.HotelTremvago.repositories.ReservaRepository;
import com.HotelTremvago.HotelTremvago.repositories.TipoQuartoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class QuartoServiceTest {
    @Autowired
    QuartoService quartoService;
    @MockBean
    QuartoRepository quartoRepository;
    @MockBean
    TipoQuartoRepository tipoQuartoRepository;
    @MockBean
    ReservaRepository reservaRepository;

    @Test
    void testCriarQuartoSuccess(){
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);

        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        tipoQuarto.setNome("Standard");
        quarto.setTipoQuarto(tipoQuarto);

        when(tipoQuartoRepository.findByNome(tipoQuarto.getNome())).thenReturn(Optional.of(tipoQuarto));
        when(quartoRepository.save(quarto)).thenReturn(quarto);

        QuartoEntity result = quartoService.criarQuarto(quarto);

        assertNotNull(result);
        assertEquals(result, quarto);
        verify(tipoQuartoRepository).findByNome(tipoQuarto.getNome());
        verify(quartoRepository).save(quarto);
    }

    @Test
    void testCriarQuartoETipoQuartoSuccess(){
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);

        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setNome("Standard");
        quarto.setTipoQuarto(tipoQuarto);

        when(tipoQuartoRepository.findByNome(tipoQuarto.getNome())).thenReturn(Optional.empty());
        when(tipoQuartoRepository.save(tipoQuarto)).thenReturn(tipoQuarto);
        when(quartoRepository.save(quarto)).thenReturn(quarto);

        QuartoEntity result = quartoService.criarQuarto(quarto);

        assertNotNull(result);
        assertEquals(result, quarto);
        verify(tipoQuartoRepository).findByNome(tipoQuarto.getNome());
        verify(tipoQuartoRepository).save(tipoQuarto);
        verify(quartoRepository).save(quarto);
    }
    
    @Test
    void testCriarQuartoFailure(){
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);

        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        tipoQuarto.setNome("Standard");
        quarto.setTipoQuarto(tipoQuarto);

        when(tipoQuartoRepository.findByNome(tipoQuarto.getNome())).thenReturn(Optional.of(tipoQuarto));
        when(quartoRepository.save(quarto)).thenThrow(new IllegalArgumentException("Erro ao salvar quarto"));

        QuartoEntity result = quartoService.criarQuarto(quarto);

        assertNull(result);
        assertNotEquals(result, quarto);
        verify(tipoQuartoRepository).findByNome(tipoQuarto.getNome());
        verify(quartoRepository).save(quarto);
    }

    @Test
    public void testQuartosDisponiveisComVagas() {
        Long tipoQuartoId = 1L;
        int capacidade = 2;
        LocalDate localDataInicio = LocalDate.of(2024, 9, 21);
        LocalDate localDataFinal = localDataInicio.plusDays(3);

        Date dataInicio = Date.from(localDataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataFinal = Date.from(localDataFinal.atStartOfDay(ZoneId.systemDefault()).toInstant());

        QuartoEntity quarto1 = new QuartoEntity();
        quarto1.setId(1L);
        QuartoEntity quarto2 = new QuartoEntity();
        quarto2.setId(2L);

        List<QuartoEntity> quartos = List.of(quarto1, quarto2);
        when(quartoRepository.findByTipoQuartoECapacidade(tipoQuartoId, capacidade)).thenReturn(quartos);

        List<ReservaEntity> reservas = new ArrayList<>();
        ReservaEntity reserva = new ReservaEntity();
        reserva.setQuarto(quarto1);
        reserva.setDataInicio(dataInicio);
        reserva.setDataFinal(dataFinal);
        reservas.add(reserva);

        when(reservaRepository.findByTipoQuartoCapacidadeStatusData(tipoQuartoId, capacidade, dataInicio, dataFinal))
                .thenReturn(reservas);

        List<QuartoEntity> result = quartoService.quartosDisponiveis(tipoQuartoId, capacidade, dataInicio, dataFinal);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(quarto2, result.get(0));
        verify(quartoRepository).findByTipoQuartoECapacidade(tipoQuartoId, capacidade);
        verify(reservaRepository, times(2)).findByTipoQuartoCapacidadeStatusData(tipoQuartoId, capacidade, dataInicio, dataFinal);
    }

    @Test
    public void testQuartosDisponiveisSemVagas() {
        Long tipoQuartoId = 1L;
        int capacidade = 2;
        LocalDate localDataInicio = LocalDate.of(2024, 9, 21);
        LocalDate localDataFinal = localDataInicio.plusDays(3);

        Date dataInicio = Date.from(localDataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataFinal = Date.from(localDataFinal.atStartOfDay(ZoneId.systemDefault()).toInstant());

        QuartoEntity quarto1 = new QuartoEntity();
        quarto1.setId(1L);
        QuartoEntity quarto2 = new QuartoEntity();
        quarto2.setId(2L);

        List<QuartoEntity> quartos = List.of(quarto1, quarto2);
        when(quartoRepository.findByTipoQuartoECapacidade(tipoQuartoId, capacidade)).thenReturn(quartos);

        List<ReservaEntity> reservas = new ArrayList<>();
        ReservaEntity reserva1 = new ReservaEntity();
        reserva1.setQuarto(quarto1);
        reserva1.setDataInicio(dataInicio);
        reserva1.setDataFinal(dataFinal);
        reservas.add(reserva1);

        ReservaEntity reserva2 = new ReservaEntity();
        reserva2.setQuarto(quarto2);
        reserva2.setDataInicio(dataInicio);
        reserva2.setDataFinal(dataFinal);
        reservas.add(reserva2);

        when(reservaRepository.findByTipoQuartoCapacidadeStatusData(tipoQuartoId, capacidade, dataInicio, dataFinal))
                .thenReturn(reservas);

        List<QuartoEntity> result = quartoService.quartosDisponiveis(tipoQuartoId, capacidade, dataInicio, dataFinal);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(quartoRepository).findByTipoQuartoECapacidade(tipoQuartoId, capacidade);
        verify(reservaRepository, times(2)).findByTipoQuartoCapacidadeStatusData(tipoQuartoId, capacidade, dataInicio, dataFinal);
    }

    @Test
    void testDeleteSuccess(){
        Long id = 1L;
        doNothing().when(quartoRepository).deleteById(id);

        String result = quartoService.delete(id);

        assertNotNull(result);
        assertEquals("Quarto deletado",result);
        verify(quartoRepository).deleteById(id);
    }

    @Test
    void testDeleteFailure(){
        Long id = 1L;
        doThrow(new IllegalArgumentException("Erro ao deletar")).when(quartoRepository).deleteById(id);

        String result = quartoService.delete(id);

        assertNotNull(result);
        assertEquals("Nao foi possivel deletar quarto",result);
        verify(quartoRepository).deleteById(id);
    }

    @Test
    void testUpdateSuccess(){
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        quarto.setNome("Standard");

        QuartoEntity updatedQuarto = new QuartoEntity();
        updatedQuarto.setNome("Deluxe");

        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        updatedQuarto.setTipoQuarto(tipoQuarto);

        when(quartoRepository.findById(quarto.getId())).thenReturn(Optional.of(quarto));
        when(tipoQuartoRepository.findById(tipoQuarto.getId())).thenReturn(Optional.of(tipoQuarto));
        when(quartoRepository.save(quarto)).thenReturn(quarto);

        QuartoEntity result = quartoService.update(updatedQuarto, quarto.getId());

        assertNotNull(result);
        assertEquals(result, quarto);
        verify(quartoRepository).findById(quarto.getId());
        verify(tipoQuartoRepository).findById(tipoQuarto.getId());
        verify(quartoRepository).save(quarto);
    }

    @Test
    void testUpdateQuartoFailure(){
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        quarto.setNome("Standard");

        QuartoEntity updatedQuarto = new QuartoEntity();
        updatedQuarto.setNome("Deluxe");

        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        updatedQuarto.setTipoQuarto(tipoQuarto);

        when(quartoRepository.findById(quarto.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            quartoService.update(updatedQuarto, quarto.getId());
        });

        assertNotNull(exception);
        assertEquals("Quarto not found with id " + quarto.getId(), exception.getMessage());
        verify(quartoRepository).findById(quarto.getId());
        verify(tipoQuartoRepository,times(0)).findById(tipoQuarto.getId());
        verify(quartoRepository, times(0)).save(quarto);
    }

    @Test
    void testUpdateTipoQuartoFailure(){
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        quarto.setNome("Standard");

        QuartoEntity updatedQuarto = new QuartoEntity();
        updatedQuarto.setNome("Deluxe");

        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        updatedQuarto.setTipoQuarto(tipoQuarto);

        when(quartoRepository.findById(quarto.getId())).thenReturn(Optional.of(quarto));
        when(tipoQuartoRepository.findById(tipoQuarto.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            quartoService.update(updatedQuarto, quarto.getId());
        });

        assertNotNull(exception);
        assertEquals("TipoQuarto not found with id " + updatedQuarto.getTipoQuarto().getId(), exception.getMessage());
        verify(quartoRepository).findById(quarto.getId());
        verify(tipoQuartoRepository).findById(tipoQuarto.getId());
        verify(quartoRepository, times(0)).save(quarto);
    }

    @Test
    void testUpdateTipoQuartoNull(){
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        quarto.setNome("Standard");

        QuartoEntity updatedQuarto = new QuartoEntity();
        updatedQuarto.setNome("Deluxe");

        TipoQuartoEntity tipoQuarto = null;
        updatedQuarto.setTipoQuarto(tipoQuarto);

        when(quartoRepository.findById(quarto.getId())).thenReturn(Optional.of(quarto));
        when(quartoRepository.save(quarto)).thenReturn(quarto);

        QuartoEntity result = quartoService.update(updatedQuarto, quarto.getId());

        assertNotNull(result);
        assertEquals(result, quarto);
        verify(quartoRepository).findById(quarto.getId());
        verify(tipoQuartoRepository, times(0)).findById(1L);
        verify(quartoRepository).save(quarto);
    }

    @Test
    void testFindByIdSuccess(){
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        when(quartoRepository.findById(quarto.getId())).thenReturn(Optional.of(quarto));

        QuartoEntity result = quartoService.findById(quarto.getId());

        assertNotNull(result);
        assertEquals(result, quarto);
        verify(quartoRepository).findById(quarto.getId());
    }

    @Test
    void testFindByIdFailure(){
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        when(quartoRepository.findById(quarto.getId())).thenReturn(Optional.empty());

        QuartoEntity result = quartoService.findById(quarto.getId());

        assertNotNull(result);
        assertNotEquals(result, quarto);
        verify(quartoRepository).findById(quarto.getId());
    }

    @Test
    void testFindAllSuccess(){
        List<QuartoEntity> quartos = List.of(new QuartoEntity(), new QuartoEntity());
        when(quartoRepository.findAll()).thenReturn(quartos);

        List<QuartoEntity> result = quartoService.findAll();

        assertNotNull(result);
        assertEquals(result, quartos);
        verify(quartoRepository).findAll();
    }

    @Test
    void testFindAllFailure(){
        List<QuartoEntity> quartos = List.of(new QuartoEntity(), new QuartoEntity());
        when(quartoRepository.findAll()).thenThrow(new IllegalArgumentException("Erro ao encontrar quartos"));

        List<QuartoEntity> result = quartoService.findAll();

        assertNotNull(result);
        assertNotEquals(result, quartos);
        verify(quartoRepository).findAll();
    }
}
