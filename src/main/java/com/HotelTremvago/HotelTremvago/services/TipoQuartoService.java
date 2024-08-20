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
            return "Nao foi possivel deletar tipo de quarto";
        }
    }

    public TipoQuartoEntity update(TipoQuartoEntity tipoQuartoEntity, Long id){
        try{
            tipoQuartoEntity.setId(id);
            return tipoQuartoRepository.save(tipoQuartoEntity);
        } catch(Exception e){
            System.out.println("Nao foi possivel atualizar tipo de quarto: " + e.getMessage());
            return new TipoQuartoEntity();
        }
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
