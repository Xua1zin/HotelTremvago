package com.HotelTremvago.HotelTremvago.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quarto")
public class QuartoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String nome;

    private int capacidade;

    @OneToMany(mappedBy = "quarto")
    private List<ReservaEntity> reservas;

    @ManyToOne
    @JoinColumn(name = "tipo_quarto_id")
    @JsonIgnoreProperties("quartos")
    private TipoQuartoEntity tipoQuarto;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonIgnoreProperties("quartos")
    private HotelEntity hotel;

    @OneToMany(mappedBy = "quarto")
    @JsonIgnore
    private List<HospedeEntity> hospedes;
}
