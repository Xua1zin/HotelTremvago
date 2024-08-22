package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.HotelEntity;
import com.HotelTremvago.HotelTremvago.entities.QuartoEntity;
import com.HotelTremvago.HotelTremvago.entities.ReservaEntity;
import com.HotelTremvago.HotelTremvago.entities.TipoQuartoEntity;
import com.HotelTremvago.HotelTremvago.repositories.HotelRepository;
import com.HotelTremvago.HotelTremvago.repositories.QuartoRepository;
import com.HotelTremvago.HotelTremvago.repositories.ReservaRepository;
import com.HotelTremvago.HotelTremvago.repositories.TipoQuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class QuartoService {
    @Autowired
    private QuartoRepository quartoRepository;
    @Autowired
    private TipoQuartoRepository tipoQuartoRepository;
    @Autowired
    ReservaService reservaService;
    @Autowired
    ReservaRepository reservaRepository;

    public QuartoEntity criarQuarto(QuartoEntity quartoEntity){
        try {
            TipoQuartoEntity tipoQuartoEntity = quartoEntity.getTipoQuarto();
            Optional<TipoQuartoEntity> tipoQuartoExistente = tipoQuartoRepository.findByNome(tipoQuartoEntity.getNome());
            if(!tipoQuartoExistente.isPresent()){
                this.tipoQuartoRepository.save(tipoQuartoEntity);
            } else {
                tipoQuartoEntity = tipoQuartoExistente.get();
                quartoEntity.setTipoQuarto(tipoQuartoEntity);
            }
            return quartoRepository.save(quartoEntity);
        } catch(Exception e){
            System.out.println("Não foi possível criar o quarto: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<QuartoEntity> quartosDisponiveis(Long tipoQuartoId, int capacidade, Date dataInicio, Date dataFinal) {
        LocalDate localDataInicio = dataInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDataFinal = dataFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<QuartoEntity> quartos = quartoRepository.findByTipoQuartoECapacidade(tipoQuartoId, capacidade);
        List<QuartoEntity> quartosDisponiveis = new ArrayList<>();

        for (QuartoEntity quarto : quartos) {
            boolean disponivel = true;

            List<ReservaEntity> reservas = reservaRepository.findByTipoQuartoCapacidadeStatusData(
                    tipoQuartoId, capacidade, dataInicio, dataFinal);

            for (ReservaEntity reserva : reservas) {
                LocalDate reservaInicio = reserva.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate reservaFinal = reserva.getDataFinal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (quarto.getId().equals(reserva.getQuarto().getId()) &&
                        !(localDataFinal.isBefore(reservaInicio) || localDataInicio.isAfter(reservaFinal))) {
                    disponivel = false;
                    break;
                }
            }
            if (disponivel) {
                quartosDisponiveis.add(quarto);
            }
        }
        return quartosDisponiveis;
    }

    public String delete(Long id){
        try {
            quartoRepository.deleteById(id);
            return "Quarto deletado";
        } catch (Exception e){
            return "Nao foi possivel deletar quarto";
        }
    }

    public QuartoEntity update(QuartoEntity updatedQuarto, Long id) {
        QuartoEntity quarto = quartoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quarto not found with id " + id));

        quarto.setNome(updatedQuarto.getNome());
        quarto.setCapacidade(updatedQuarto.getCapacidade());

         if (updatedQuarto.getTipoQuarto() != null) {
            TipoQuartoEntity tipoQuarto = tipoQuartoRepository.findById(updatedQuarto.getTipoQuarto().getId())
                    .orElseThrow(() -> new RuntimeException("TipoQuarto not found with id " + updatedQuarto.getTipoQuarto().getId()));
             quarto.setTipoQuarto(tipoQuarto);
        }

        return quartoRepository.save(quarto);
    }

    public QuartoEntity findById(Long id){
        try{
            return quartoRepository.findById(id).orElseThrow();
        }catch(Exception e){
            System.out.println("Nao foi possivel encontrar um quarto com este ID: " + e.getMessage());
            return new QuartoEntity();
        }
    }

    public List<QuartoEntity> findAll() {
        try{
            return quartoRepository.findAll();
        } catch(Exception e) {
            System.out.println("Erro ao encontrar lista de quarto: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}