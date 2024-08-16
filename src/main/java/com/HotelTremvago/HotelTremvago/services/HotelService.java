package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.HotelEntity;
import com.HotelTremvago.HotelTremvago.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public HotelEntity save(HotelEntity hotelEntity){
        try{
            return hotelRepository.save(hotelEntity);
        } catch(Exception e){
            System.out.println("Nao foi possivel salvar hotel: " + e.getMessage());
            return new HotelEntity();
        }
    }

    public String delete(Long id){
        try {
            hotelRepository.deleteById(id);
            return "Hotel deletado";
        } catch (Exception e){
            return "Nao foi possivel deletar hotel";
        }
    }

    public HotelEntity update(HotelEntity hotelEntity, Long id){
        try{
            hotelEntity.setId(id);
            return hotelRepository.save(hotelEntity);
        } catch(Exception e){
            System.out.println("Nao foi possivel atualizar hotel: " + e.getMessage());
            return new HotelEntity();
        }
    }

    public HotelEntity findById(Long id){
        try{
            return hotelRepository.findById(id).orElseThrow();
        }catch(Exception e){
            System.out.println("Nao foi possivel encontrar um hotel com este ID: " + e.getMessage());
            return new HotelEntity();
        }
    }

    public List<HotelEntity> findAll() {
        try{
            return hotelRepository.findAll();
        } catch(Exception e) {
            System.out.println("Erro ao encontrar lista de hotel: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
