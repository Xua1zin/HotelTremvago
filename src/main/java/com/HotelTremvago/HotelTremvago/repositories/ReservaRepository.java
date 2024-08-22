package com.HotelTremvago.HotelTremvago.repositories;

import com.HotelTremvago.HotelTremvago.entities.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
    @Query(value = "SELECT r.* " +
            "FROM reserva r " +
            "JOIN quarto q ON r.quarto_id = q.id " +
            "WHERE q.tipo_quarto_id = ?1 " +
            "AND q.capacidade >= ?2 " +
            "AND r.status IN ('OCUPADO', 'RESERVADO') " +
            "AND (r.data_inicio <= ?4 AND r.data_final >= ?3)",
            nativeQuery = true)
    List<ReservaEntity> findByTipoQuartoCapacidadeStatusData(Long tipoQuartoId, int capacidade, Date dataInicio, Date dataFinal);
}