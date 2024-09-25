package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.*;
import com.HotelTremvago.HotelTremvago.repositories.HospedeRepository;
import com.HotelTremvago.HotelTremvago.repositories.QuartoRepository;
import com.HotelTremvago.HotelTremvago.repositories.ReservaRepository;
import com.HotelTremvago.HotelTremvago.repositories.TipoQuartoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ReservaServiceTest {
    @Autowired
    ReservaService reservaService;
    @MockBean
    ReservaRepository reservaRepository;
    @MockBean
    HospedeRepository hospedeRepository;
    @MockBean
    QuartoRepository quartoRepository;
    @MockBean
    TipoQuartoRepository tipoQuartoRepository;

    @Test
    void testCalcularDiaria() {
        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setValor(100.0);

        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        quarto.setTipoQuarto(tipoQuarto);

        ReservaEntity reserva = new ReservaEntity();
        reserva.setQuarto(quarto);
        reserva.setDataInicio(Date.from(LocalDate.of(2024, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        reserva.setDataFinal(Date.from(LocalDate.of(2024, 9, 5).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Mockito.when(quartoRepository.findById(1L)).thenReturn(Optional.of(quarto));

        Double diaria = reservaService.calcularDiaria(reserva);

        assertEquals(400.0, diaria);
    }


    @Test
    void testDatasLivres() {
        Long tipoQuartoId = 1L;
        int capacidade = 2;
        int mes = 10;
        int ano = 2024;

        List<QuartoEntity> quartosDisponiveis = new ArrayList<>();
        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        quarto.setNome("Quarto Deluxe");
        quarto.setCapacidade(2);

        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        tipoQuarto.setNome("Tipo A");
        tipoQuarto.setValor(200.0);
        quarto.setTipoQuarto(tipoQuarto);

        Mockito.when(tipoQuartoRepository.findById(tipoQuartoId))
                .thenReturn(Optional.of(tipoQuarto));

        quartosDisponiveis.add(quarto);

        LocalDate inicioMes = LocalDate.of(ano, mes, 1);
        LocalDate fimMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

        ReservaEntity reserva1 = new ReservaEntity();
        reserva1.setId(1L);
        reserva1.setDataInicio(Date.from(LocalDate.of(ano, mes, 5).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        reserva1.setDataFinal(Date.from(LocalDate.of(ano, mes, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        reserva1.setQuarto(quarto);
        reserva1.setStatus(ReservaStatus.OCUPADO);

        List<ReservaEntity> reservasFiltradas = new ArrayList<>();
        reservasFiltradas.add(reserva1);

        Mockito.when(reservaRepository.findByTipoQuartoCapacidadeStatusData(
                        tipoQuartoId, capacidade,
                        java.sql.Date.valueOf(inicioMes),
                        java.sql.Date.valueOf(fimMes)))
                .thenReturn(reservasFiltradas);

        Mockito.when(quartoRepository.findByTipoQuartoECapacidade(tipoQuartoId, capacidade))
                .thenReturn(quartosDisponiveis);
        System.out.println("Reservas retornadas: " + reservasFiltradas.get(0));
        System.out.println("Quartos disponíveis: " + quartosDisponiveis.get(0));

        List<Integer> expectedDiasLivres = List.of(1, 2, 3, 4, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);

        var dataTest = reservaService.datasLivres(tipoQuartoId, capacidade, mes, ano);
        assertEquals(expectedDiasLivres, dataTest);
    }

    @Test
    void testSaveSuccess() {
        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setValor(100.0);

        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        quarto.setTipoQuarto(tipoQuarto);

        ReservaEntity reserva = new ReservaEntity();
        reserva.setQuarto(quarto);
        reserva.setDataInicio(Date.from(LocalDate.of(2024, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        reserva.setDataFinal(Date.from(LocalDate.of(2024, 9, 5).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        when(quartoRepository.findById(quarto.getId())).thenReturn(Optional.of(quarto));
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        ReservaEntity result = reservaService.save(reserva);

        assertNotNull(result);
        assertEquals(reserva, result);
        verify(reservaRepository).save(reserva);
    }

    @Test
    void testSaveFailure() {
        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setValor(100.0);

        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        quarto.setTipoQuarto(tipoQuarto);

        ReservaEntity reserva = new ReservaEntity();
        reserva.setQuarto(quarto);
        reserva.setDataInicio(Date.from(LocalDate.of(2024, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        reserva.setDataFinal(Date.from(LocalDate.of(2024, 9, 5).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        when(quartoRepository.findById(quarto.getId())).thenReturn(Optional.of(quarto));
        when(reservaRepository.save(reserva)).thenThrow(new IllegalArgumentException("Erro ao salvar"));

        ReservaEntity result = reservaService.save(reserva);

        assertNotNull(result);
        assertNotEquals(reserva, result);
        verify(reservaRepository).save(reserva);
    }

    @Test
    void testDeleteSuccess() {
        Long reservaId = 1L;

        doNothing().when(reservaRepository).deleteById(reservaId);

        String result = reservaService.delete(reservaId);

        assertNotNull(result);
        assertEquals("Reserva deletado", result);
        verify(reservaRepository).deleteById(reservaId);
    }

    @Test
    void testDeleteFailure() {
        Long reservaId = 1L;

        doThrow(new IllegalArgumentException("Erro ao deletar")).when(reservaRepository).deleteById(reservaId);

        String result = reservaService.delete(reservaId);

        assertNotNull(result);
        assertEquals("Nao foi possivel deletar reserva", result);
        verify(reservaRepository).deleteById(reservaId);
    }

    @Test
    void testUpdateSuccess() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);

        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reserva);

        ReservaEntity result = reservaService.update(reserva, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(reservaRepository).save(reserva);
    }

    @Test
    void testUpdateFaiure() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);

        when(reservaRepository.save(any(ReservaEntity.class))).thenThrow(new IllegalArgumentException("Erro ao atualizar"));

        ReservaEntity result = reservaService.update(reserva, 1L);

        assertNotNull(result);
        assertNotEquals(reserva, result);
        verify(reservaRepository).save(reserva);
    }

    @Test
    void testFindByIdSuccess() {
        Long reservaId = 1L;
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(reservaId);

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

        ReservaEntity result = reservaService.findById(reservaId);

        assertNotNull(result);
        assertEquals(reserva, result);
        verify(reservaRepository).findById(reserva.getId());
    }

    @Test
    void testFindByIdFailure() {
        Long reservaId = 1L;
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(reservaId);

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.empty());

        ReservaEntity result = reservaService.findById(reservaId);

        assertNotNull(result);
        assertNotEquals(reserva, result);
        verify(reservaRepository).findById(reserva.getId());
    }

    @Test
    void testFindAllSuccess() {
        List<ReservaEntity> reservas = Arrays.asList(new ReservaEntity(), new ReservaEntity());

        when(reservaRepository.findAll()).thenReturn(reservas);

        List<ReservaEntity> result = reservaService.findAll();

        assertNotNull(result);
        assertEquals(reservas, result);
        verify(reservaRepository).findAll();
    }

    @Test
    void testFindAllFailure() {
        List<ReservaEntity> reservas = Arrays.asList(new ReservaEntity(), new ReservaEntity());

        when(reservaRepository.findAll()).thenThrow(new IllegalArgumentException("Nenhuma reserva encontrada"));

        List<ReservaEntity> result = reservaService.findAll();

        assertNotNull(result);
        assertNotEquals(reservas, result);
        verify(reservaRepository).findAll();
    }

    @Test
    void testAddHospedeToReservaSuccess() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);
        reserva.setHospedes(new ArrayList<>());

        HospedeEntity hospede = new HospedeEntity();
        hospede.setId(1L);
        hospede.setReservas(new ArrayList<>());

        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(hospedeRepository.findById(hospede.getId())).thenReturn(Optional.of(hospede));
        when(reservaRepository.save(reserva)).thenReturn(reserva);
        when(hospedeRepository.save(hospede)).thenReturn(hospede);

        ReservaEntity result = reservaService.addHospedeToReserva(reserva.getId(), hospede.getId());

        assertNotNull(result);
        assertEquals(reserva, result);
        assertTrue(reserva.getHospedes().contains(hospede));
        verify(reservaRepository).findById(reserva.getId());
        verify(hospedeRepository).findById(hospede.getId());
        verify(reservaRepository).save(reserva);
        verify(hospedeRepository).save(hospede);
    }


    @Test
    void testAddHospedeToReservaReservaInexistente() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());

        ReservaEntity result = reservaService.addHospedeToReserva(1L, 1L);

        assertNull(result);
        verify(reservaRepository).findById(1L);
        verify(hospedeRepository, never()).findById(anyLong());
    }

    @Test
    void testAddHospedeToReservaHospedeInexistente() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);
        reserva.setHospedes(new ArrayList<>());

        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(hospedeRepository.findById(1L)).thenReturn(Optional.empty());

        ReservaEntity result = reservaService.addHospedeToReserva(reserva.getId(), 1L);

        assertNull(result);
        verify(reservaRepository).findById(reserva.getId());
        verify(hospedeRepository).findById(1L);
    }

    @Test
    void testRemoveHospedeFromReservaSuccess() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);
        List<ReservaEntity> reservas = new ArrayList<>();
        reservas.add(reserva);

        HospedeEntity hospede = new HospedeEntity();
        hospede.setId(1L);
        List<HospedeEntity> hospedes = new ArrayList<>();
        hospedes.add(hospede);
        hospede.setReservas(reservas);
        reserva.setHospedes(hospedes);

        hospede.setReservas(reservas);
        reserva.setHospedes(hospedes);

        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(hospedeRepository.findById(hospede.getId())).thenReturn(Optional.of(hospede));
        when(reservaRepository.save(reserva)).thenReturn(reserva);
        when(hospedeRepository.save(hospede)).thenReturn(hospede);

        ReservaEntity result = reservaService.removeHospedeFromReserva(reserva.getId(), hospede.getId());

        assertNotNull(result);
        assertEquals(reserva, result);
        assertFalse(reserva.getHospedes().contains(hospede));
        verify(reservaRepository).findById(reserva.getId());
        verify(hospedeRepository).findById(hospede.getId());
        verify(reservaRepository).save(reserva);
        verify(hospedeRepository).save(hospede);
    }

    @Test
    void testRemoveHospedeToReservaReservaInexistente() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());

        ReservaEntity result = reservaService.removeHospedeFromReserva(1L, 1L);

        assertNull(result);
        verify(reservaRepository).findById(1L);
        verify(hospedeRepository, never()).findById(anyLong());
    }

    @Test
    void testRemoveHospedeToReservaHospedeInexistente() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);
        reserva.setHospedes(new ArrayList<>());

        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(hospedeRepository.findById(1L)).thenReturn(Optional.empty());

        ReservaEntity result = reservaService.removeHospedeFromReserva(reserva.getId(), 1L);

        assertNull(result);
        verify(reservaRepository).findById(reserva.getId());
        verify(hospedeRepository).findById(1L);
    }

    @Test
    void testRealizarCheckInSuccess() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);
        reserva.setStatus(ReservaStatus.RESERVADO);

        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        ReservaEntity result = reservaService.realizarCheckIn(reserva.getId());

        assertNotNull(result);
        assertEquals(ReservaStatus.OCUPADO, result.getStatus());
        verify(reservaRepository).findById(reserva.getId());
        verify(reservaRepository).save(reserva);
    }

    @Test
    void testRealizarCheckInReservaInexistente() {
        Long reservaId = 1L;
        when(reservaRepository.findById(reservaId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reservaService.realizarCheckIn(reservaId));
        verify(reservaRepository).findById(reservaId);
    }

    @Test
    void testRealizarCheckInStatusCancelado() {
        Long reservaId = 1L;
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(reservaId);
        reserva.setStatus(ReservaStatus.CANCELADO);

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

        assertThrows(IllegalStateException.class, () -> reservaService.realizarCheckIn(reservaId));
        verify(reservaRepository).findById(reservaId);
    }

    @Test
    void testRealizarCheckInReservaStatusDiferenteDeReservado() {
        Long reservaId = 1L;
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(reservaId);
        reserva.setStatus(ReservaStatus.OCUPADO);

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

        assertThrows(IllegalStateException.class, () -> reservaService.realizarCheckIn(reservaId));
        verify(reservaRepository).findById(reservaId);
    }

    @Test
    void testRealizarCheckOutSuccess() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);
        reserva.setStatus(ReservaStatus.OCUPADO);

        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reserva);

        ReservaEntity updatedReserva = reservaService.realizarCheckOut(reserva.getId());

        assertNotNull(updatedReserva);
        assertEquals(ReservaStatus.SAIDA, updatedReserva.getStatus());
    }

    @Test
    void testRealizarCheckOutReservaInexistente() {
        Long reservaId = 1L;
        when(reservaRepository.findById(reservaId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reservaService.realizarCheckOut(reservaId));
        verify(reservaRepository).findById(reservaId);
    }

    @Test
    void testRealizarCheckOutStatusCancelado() {
        Long reservaId = 1L;
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(reservaId);
        reserva.setStatus(ReservaStatus.CANCELADO);

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

        assertThrows(IllegalStateException.class, () -> reservaService.realizarCheckOut(reservaId));
        verify(reservaRepository).findById(reservaId);
    }

    @Test
    void testRealizarCheckOutReservaStatusDiferenteDeOcupado() {
        Long reservaId = 1L;
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(reservaId);
        reserva.setStatus(ReservaStatus.RESERVADO);

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

        assertThrows(IllegalStateException.class, () -> reservaService.realizarCheckOut(reservaId));
        verify(reservaRepository).findById(reservaId);
    }
}
