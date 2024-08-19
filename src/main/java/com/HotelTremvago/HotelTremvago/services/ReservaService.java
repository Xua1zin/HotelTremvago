package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.ReservaEntity;
import com.HotelTremvago.HotelTremvago.entities.TipoQuartoEntity;
import com.HotelTremvago.HotelTremvago.repositories.ReservaRepository;
import com.HotelTremvago.HotelTremvago.repositories.TipoQuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private TipoQuartoRepository tipoQuartoRepository;

    public Double calcularDiaria(ReservaEntity reservaEntity) {
        TipoQuartoEntity tipoQuartoEntity = reservaEntity.getQuarto().getTipoQuarto();

        LocalDate dataInicio = reservaEntity.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dataFinal = reservaEntity.getDataFinal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long dias = ChronoUnit.DAYS.between(dataInicio, dataFinal);
        Double diaria = tipoQuartoEntity.getValor();

        return dias * diaria;
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
}
