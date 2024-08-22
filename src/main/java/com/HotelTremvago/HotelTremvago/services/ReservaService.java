package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.*;
import com.HotelTremvago.HotelTremvago.repositories.HospedeRepository;
import com.HotelTremvago.HotelTremvago.repositories.QuartoRepository;
import com.HotelTremvago.HotelTremvago.repositories.ReservaRepository;
import com.HotelTremvago.HotelTremvago.repositories.TipoQuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private HospedeRepository hospedeRepository;
    @Autowired
    private TipoQuartoRepository tipoQuartoRepository;
    @Autowired
    private QuartoRepository quartoRepository;

    public Double calcularDiaria(ReservaEntity reservaEntity) {
        long idQuarto = reservaEntity.getQuarto().getId();
        Optional<QuartoEntity> quartoEntity = quartoRepository.findById(idQuarto);

        TipoQuartoEntity tipoQuartoEntity = quartoEntity.get().getTipoQuarto();

        LocalDate dataInicio = reservaEntity.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dataFinal = reservaEntity.getDataFinal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long dias = ChronoUnit.DAYS.between(dataInicio, dataFinal);
        Double diaria = tipoQuartoEntity.getValor();

        return dias * diaria;
    }


    public List<Integer> datasLivres(Long tipoQuartoId, int capacidade, int mes, int ano) {
        LocalDate inicioMes = LocalDate.of(ano, mes, 1);
        LocalDate fimMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

        List<Integer> diasMes = inicioMes.datesUntil(fimMes.plusDays(1))
                .map(LocalDate::getDayOfMonth)
                .collect(Collectors.toList());

        List<ReservaEntity> reservasFiltradas = reservaRepository.findByTipoQuartoCapacidadeStatusData(tipoQuartoId, capacidade, java.sql.Date.valueOf(inicioMes), java.sql.Date.valueOf(fimMes));

        List<Integer> diasReservados = new ArrayList<>();
        for (ReservaEntity reserva : reservasFiltradas) {
            LocalDate inicioData = reserva.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fimData = reserva.getDataFinal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            LocalDate inicioIntervalo = inicioData.isBefore(inicioMes) ? inicioMes : inicioData;
            LocalDate fimIntervalo = fimData.isAfter(fimMes) ? fimMes : fimData;

            LocalDate tempDate = inicioIntervalo;
            while (!tempDate.isAfter(fimIntervalo)) {
                if (tempDate.getMonthValue() == mes && tempDate.getYear() == ano) {
                    diasReservados.add(tempDate.getDayOfMonth());
                }
                tempDate = tempDate.plusDays(1);
            }
        } LocalDate hoje = LocalDate.now();


        List<QuartoEntity> quartosDisponiveis = quartoRepository.findByTipoQuartoECapacidade(tipoQuartoId, capacidade);

        List<Integer> diasLivres = new ArrayList<>(diasMes);

        for (Integer dia : diasMes) {
            LocalDate dataAtual = LocalDate.of(ano, mes, dia);
            if (diasReservados.contains(dia)) {
                boolean quartoDisponivel = quartosDisponiveis.stream().anyMatch(quarto -> {

                    boolean reservado = reservasFiltradas.stream().anyMatch(reserva -> {
                        LocalDate inicioData = reserva.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate fimData = reserva.getDataFinal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        return reserva.getQuarto().getId().equals(quarto.getId()) &&
                                (dataAtual.isAfter(inicioData.minusDays(1)) && dataAtual.isBefore(fimData.plusDays(1)));
                    });
                    return !reservado;
                });
                if (!quartoDisponivel) {
                    diasLivres.remove(dia);
                }
            }
        }

        List<Integer> datasLivres = diasLivres.stream()
                .filter(dia -> LocalDate.of(ano, mes, dia).isAfter(hoje))
                .collect(Collectors.toList());

        return datasLivres;
    }


    public ReservaEntity save(ReservaEntity reservaEntity) {
        try {
            Double preco = calcularDiaria(reservaEntity);
            reservaEntity.setTotal(preco);
            return reservaRepository.save(reservaEntity);
        } catch (Exception e) {
            System.out.println("Nao foi possivel salvar reserva: " + e.getMessage());
            return new ReservaEntity();
        }
    }

    public String delete(Long id){
        try {
            reservaRepository.deleteById(id);
            return "Reserva deletado";
        } catch (Exception e){
            return "Nao foi possivel deletar reserva";
        }
    }

    public ReservaEntity update(ReservaEntity reservaEntity, Long id){
        try{
            reservaEntity.setId(id);
            return reservaRepository.save(reservaEntity);
        } catch(Exception e){
            System.out.println("Nao foi possivel atualizar reserva: " + e.getMessage());
            return new ReservaEntity();
        }
    }

    public ReservaEntity findById(Long id){
        try{
            return reservaRepository.findById(id).orElseThrow();
        }catch(Exception e){
            System.out.println("Nao foi possivel encontrar uma reserva com este ID: " + e.getMessage());
            return new ReservaEntity();
        }
    }

    public List<ReservaEntity> findAll() {
        try{
            return reservaRepository.findAll();
        } catch(Exception e) {
            System.out.println("Erro ao encontrar lista de reserva: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public ReservaEntity addHospedeToReserva(Long reservaId, Long hospedeId) {
        try {
            ReservaEntity reserva = reservaRepository.findById(reservaId).orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
            HospedeEntity hospede = hospedeRepository.findById(hospedeId).orElseThrow(() -> new RuntimeException("Hospede não encontrado"));

            reserva.getHospedes().add(hospede);
            hospede.getReservas().add(reserva);

            reservaRepository.save(reserva);
            hospedeRepository.save(hospede);

            return reserva;
        } catch (Exception e) {
            System.out.println("Não foi possível adicionar o hóspede à reserva: " + e.getMessage());
            return null;
        }
    }
    public ReservaEntity removeHospedeFromReserva(Long reservaId, Long hospedeId) {
        try {
            ReservaEntity reserva = reservaRepository.findById(reservaId)
                    .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
            HospedeEntity hospede = hospedeRepository.findById(hospedeId)
                    .orElseThrow(() -> new RuntimeException("Hospede não encontrado"));

            reserva.getHospedes().remove(hospede);
            hospede.getReservas().remove(reserva);

            reservaRepository.save(reserva);
            hospedeRepository.save(hospede);

            return reserva;
        } catch (Exception e) {
            System.out.println("Não foi possível remover o hóspede da reserva: " + e.getMessage());
            return null;
        }
    }

    public ReservaEntity realizarCheckIn(Long id) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));

        if (reserva.getStatus() == ReservaStatus.CANCELADO) {
            throw new IllegalStateException("Não é possível fazer check-in em uma reserva cancelada");
        }

        if (reserva.getStatus() != ReservaStatus.RESERVADO) {
            throw new IllegalStateException("Apenas reservas com status RESERVADO podem ser check-in");
        }

        reserva.setStatus(ReservaStatus.OCUPADO);
        reserva.setDataCheckIn(new Date());

        return reservaRepository.save(reserva);
    }
    public ReservaEntity realizarCheckOut(Long id) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));

        if (reserva.getStatus() == ReservaStatus.CANCELADO) {
            throw new IllegalStateException("Não é possível fazer check-out em uma reserva cancelada");
        }

        if (reserva.getStatus() != ReservaStatus.OCUPADO) {
            throw new IllegalStateException("Apenas reservas com status OCUPADO podem ser check-out");
        }

        reserva.setStatus(ReservaStatus.RESERVADO);
        reserva.setDataCheckOut(new Date());

        return reservaRepository.save(reserva);
    }
}



