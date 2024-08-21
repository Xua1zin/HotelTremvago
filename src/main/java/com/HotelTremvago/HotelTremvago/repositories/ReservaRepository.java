package com.HotelTremvago.HotelTremvago.repositories;

import com.HotelTremvago.HotelTremvago.entities.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
    @Query(value = "SELECT r.* " +
            "FROM reserva r " +
            "JOIN quarto q ON r.quarto_id = q.id " +
            "WHERE q.tipo_quarto_id = ?1 " +
            "AND r.status IN ('OCUPADO', 'RESERVADO') " +
            "AND q.capacidade = ?2 " +
            "AND (EXTRACT(MONTH FROM r.data_inicio) = ?3 OR EXTRACT(MONTH FROM r.data_final) = ?3) " +
            "AND (EXTRACT(YEAR FROM r.data_inicio) = ?4 OR EXTRACT(YEAR FROM r.data_final) = ?4)",
            nativeQuery = true)
    List<ReservaEntity> findByTipoQuartoCapacidadeStatusData(Long tipoQuartoId, int capacidade, int mes, int ano);
}