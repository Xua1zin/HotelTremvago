package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.*;
import com.HotelTremvago.HotelTremvago.repositories.HospedeRepository;
import com.HotelTremvago.HotelTremvago.repositories.QuartoRepository;
import com.HotelTremvago.HotelTremvago.repositories.ReservaRepository;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
    void testSave() {
        TipoQuartoEntity tipoQuarto = new TipoQuartoEntity();
        tipoQuarto.setId(1L);
        tipoQuarto.setValor(200.0);

        QuartoEntity quarto = new QuartoEntity();
        quarto.setId(1L);
        quarto.setTipoQuarto(tipoQuarto);

        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);
        reserva.setQuarto(quarto);
        reserva.setDataInicio(Date.from(LocalDate.of(2024, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        reserva.setDataFinal(Date.from(LocalDate.of(2024, 9, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reserva);

        when(quartoRepository.findById(1L)).thenReturn(Optional.of(quarto));

        ReservaEntity savedReserva = reservaService.save(reserva);
        System.out.println(savedReserva.getTotal());

        assertNotNull(savedReserva, "Reserva salva não deve ser nula.");
        assertEquals(1L, savedReserva.getId(), "ID da reserva não corresponde ao esperado.");
    }


    @Test
    void testDelete() {
        Long reservaId = 1L;

        doNothing().when(reservaRepository).deleteById(reservaId);

        String result = reservaService.delete(reservaId);

        assertEquals("Reserva deletado", result);
    }

    @Test
    void testUpdate() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);

        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reserva);

        ReservaEntity updatedReserva = reservaService.update(reserva, 1L);

        assertNotNull(updatedReserva);
        assertEquals(1L, updatedReserva.getId());
    }

    @Test
    void testFindById() {
        Long reservaId = 1L;
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(reservaId);

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

        ReservaEntity foundReserva = reservaService.findById(reservaId);

        assertNotNull(foundReserva);
        assertEquals(reservaId, foundReserva.getId());
    }

    @Test
    void testFindAll() {
        List<ReservaEntity> reservas = Arrays.asList(new ReservaEntity(), new ReservaEntity());

        when(reservaRepository.findAll()).thenReturn(reservas);

        List<ReservaEntity> allReservas = reservaService.findAll();

        assertNotNull(allReservas);
        assertEquals(2, allReservas.size());
    }

    @Test
    void testAddHospedeToReserva() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);
        reserva.setHospedes(new ArrayList<>());

        HospedeEntity hospede = new HospedeEntity();
        hospede.setId(1L);
        hospede.setReservas(new ArrayList<>());

        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(hospedeRepository.findById(hospede.getId())).thenReturn(Optional.of(hospede));
        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reserva);
        when(hospedeRepository.save(any(HospedeEntity.class))).thenReturn(hospede);

        ReservaEntity updatedReserva = reservaService.addHospedeToReserva(reserva.getId(), hospede.getId());

        assertNotNull(updatedReserva, "Reserva atualizada não deve ser nula.");
        assertTrue(updatedReserva.getHospedes().contains(hospede), "O hóspede não foi adicionado à reserva.");
        assertTrue(hospede.getReservas().contains(updatedReserva), "A reserva não foi adicionada ao hóspede.");
    }


    @Test
    void testRemoveHospedeFromReserva() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);
        List<ReservaEntity> reservas = new ArrayList<>();

        HospedeEntity hospede = new HospedeEntity();
        hospede.setId(1L);
        List<HospedeEntity> hospedes = new ArrayList<>();

        hospede.setReservas(reservas);
        reserva.setHospedes(hospedes);

        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(hospedeRepository.findById(hospede.getId())).thenReturn(Optional.of(hospede));
        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reserva);
        when(hospedeRepository.save(any(HospedeEntity.class))).thenReturn(hospede);

        ReservaEntity updatedReserva = reservaService.removeHospedeFromReserva(reserva.getId(), hospede.getId());

        assertNotNull(updatedReserva);
    }

    @Test
    void testRealizarCheckIn() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);
        reserva.setStatus(ReservaStatus.RESERVADO);

        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reserva);

        ReservaEntity updatedReserva = reservaService.realizarCheckIn(reserva.getId());

        assertNotNull(updatedReserva);
        assertEquals(ReservaStatus.OCUPADO, updatedReserva.getStatus());
    }

    @Test
    void testRealizarCheckOut() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);
        reserva.setStatus(ReservaStatus.OCUPADO);

        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reserva);

        ReservaEntity updatedReserva = reservaService.realizarCheckOut(reserva.getId());

        assertNotNull(updatedReserva);
        assertEquals(ReservaStatus.SAIDA, updatedReserva.getStatus());
    }
}
