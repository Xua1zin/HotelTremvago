package com.HotelTremvago.HotelTremvago.repositories;

import com.HotelTremvago.HotelTremvago.entities.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
    @Query(value = "SELECT * FROM reserva " +
            "WHERE quarto_id = ?1 " +
            "AND status IN ('OCUPADO', 'RESERVADO') " +
            "AND (EXTRACT(MONTH FROM data_inicio) = ?2 OR EXTRACT(MONTH FROM data_final) = ?2) " +
            "AND (EXTRACT(YEAR FROM data_inicio) = ?3 OR EXTRACT(YEAR FROM data_final) = ?3)", nativeQuery = true)
    List<ReservaEntity> findByQuartoStatusData(Long quartoId, int mes, int ano);
}