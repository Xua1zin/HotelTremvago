package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.TipoQuartoEntity;
import com.HotelTremvago.HotelTremvago.repositories.TipoQuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TipoQuartoService {
    @Autowired
    private TipoQuartoRepository tipoQuartoRepository;

    public TipoQuartoEntity save(TipoQuartoEntity tipoQuartoEntity){
        try{
            return tipoQuartoRepository.save(tipoQuartoEntity);
        } catch(Exception e){
            System.out.println("Nao foi possivel salvar tipo de quarto: " + e.getMessage());
            return new TipoQuartoEntity();
        }
    }

    public String delete(Long id){
        try {
            tipoQuartoRepository.deleteById(id);
            return "Tipo de quarto deletado";
        } catch (Exception e){
            System.out.println("Não foi possível deletar tipo de quarto: " + e.getMessage());
            return "Não foi possível deletar tipo de quarto";
        }
    }

    public TipoQuartoEntity update(TipoQuartoEntity updatedTipoQuarto, Long id) {
        TipoQuartoEntity tipoQuarto = tipoQuartoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoQuarto not found with id " + id));

        tipoQuarto.setNome(updatedTipoQuarto.getNome());
        tipoQuarto.setValor(updatedTipoQuarto.getValor());

        return tipoQuartoRepository.save(tipoQuarto);
    }

    public TipoQuartoEntity findById(Long id){
        try{
            return tipoQuartoRepository.findById(id).orElseThrow();
        }catch(Exception e){
            System.out.println("Nao foi possivel encontrar um tipo de quarto com este ID: " + e.getMessage());
            return new TipoQuartoEntity();
        }
    }

    public List<TipoQuartoEntity> findAll() {
        try{
            return tipoQuartoRepository.findAll();
        } catch(Exception e) {
            System.out.println("Erro ao encontrar lista de tipo de quarto: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
