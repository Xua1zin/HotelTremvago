package com.HotelTremvago.HotelTremvago.repositories;

import com.HotelTremvago.HotelTremvago.entities.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
    @Query(value = "SELECT * FROM reserva WHERE quarto_id = ? AND status = 'OCUPADO', 'RESERVADO'", nativeQuery = true)
    List<ReservaEntity> findByQuartoEStatus(@Param("quartoId")Long quartoId);
}
