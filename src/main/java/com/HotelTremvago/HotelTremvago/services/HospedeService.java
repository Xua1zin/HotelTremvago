package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.HospedeEntity;
import com.HotelTremvago.HotelTremvago.entities.UsuarioEntity;
import com.HotelTremvago.HotelTremvago.repositories.HospedeRepository;
import com.HotelTremvago.HotelTremvago.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HospedeService {
    @Autowired
    private HospedeRepository hospedeRepository;

    public HospedeEntity save(HospedeEntity hospedeEntity){
        try{
            return hospedeRepository.save(hospedeEntity);
        } catch(Exception e){
            System.out.println("Nao foi possivel salvar hospede: " + e.getMessage());
            return new HospedeEntity();
        }
    }

    public String delete(Long id){
        try {
            hospedeRepository.deleteById(id);
            return "Hospede deletado";
        } catch (Exception e){
            return "Nao foi possivel deletar hospede";
        }
    }

    public HospedeEntity update(HospedeEntity hospedeEntity, Long id){
        try{
            hospedeEntity.setId(id);
            return hospedeRepository.save(hospedeEntity);
        } catch(Exception e){
            System.out.println("Nao foi possivel atualizar hospede: " + e.getMessage());
            return new HospedeEntity();
        }
    }

    public HospedeEntity findById(Long id){
        try{
            return hospedeRepository.findById(id).orElseThrow();
        }catch(Exception e){
            System.out.println("Nao foi possivel encontrar um hospede com este ID: " + e.getMessage());
            return new HospedeEntity();
        }
    }

    public List<HospedeEntity> findAll() {
        try{
            return hospedeRepository.findAll();
        } catch(Exception e) {
            System.out.println("Erro ao encontrar lista de hospede: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
