package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.HotelEntity;
import com.HotelTremvago.HotelTremvago.entities.QuartoEntity;
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

            LocalDate data = localDataInicio;
            while (!data.isAfter(localDataFinal)) {
                int mes = data.getMonthValue();
                int ano = data.getYear();
                List<Integer> datasDisponiveis = reservaService.datasLivres(tipoQuartoId, capacidade, mes, ano);

                if (!datasDisponiveis.contains(data.getDayOfMonth())) {
                    disponivel = false;
                    break;
                }
                data = data.plusDays(1);
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

    public QuartoEntity update(QuartoEntity quartoEntity, Long id){
        try{
            quartoEntity.setId(id);
            return quartoRepository.save(quartoEntity);
        } catch(Exception e){
            System.out.println("Nao foi possivel atualizar quarto: " + e.getMessage());
            return new QuartoEntity();
        }
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