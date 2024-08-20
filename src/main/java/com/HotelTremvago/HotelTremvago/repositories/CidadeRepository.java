package com.HotelTremvago.HotelTremvago.repositories;

import com.HotelTremvago.HotelTremvago.entities.CidadeEntity;
import com.HotelTremvago.HotelTremvago.entities.HospedeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<CidadeEntity, Long> {
    List<CidadeEntity> findByCidade(String cidade);

    List<CidadeEntity> findByEstado(String estado);

    List<CidadeEntity> findByCidadeAndEstado(String cidade, String estado);
}
