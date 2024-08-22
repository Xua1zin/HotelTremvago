package com.HotelTremvago.HotelTremvago.repositories;

import com.HotelTremvago.HotelTremvago.entities.TipoQuartoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TipoQuartoRepository extends JpaRepository<TipoQuartoEntity, Long> {
    @Query(value = "SELECT * FROM tipo_quarto WHERE (nome = ?)", nativeQuery = true)
    Optional<TipoQuartoEntity> findByNome(@Param("nome") String nome);
}