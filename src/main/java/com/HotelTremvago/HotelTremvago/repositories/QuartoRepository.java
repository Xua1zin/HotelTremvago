package com.HotelTremvago.HotelTremvago.repositories;

import com.HotelTremvago.HotelTremvago.entities.QuartoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuartoRepository extends JpaRepository<QuartoEntity, Long> {
    @Query(value = "SELECT * " +
            "FROM quarto " +
            "WHERE tipo_quarto_id = ?1 " +
            "AND capacidade >= ?2", nativeQuery = true)
    List<QuartoEntity> findByTipoQuartoECapacidade(long tipoQuartoId, int capacidade);
}
