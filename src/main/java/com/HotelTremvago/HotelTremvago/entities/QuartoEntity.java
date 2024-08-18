package com.HotelTremvago.HotelTremvago.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    private ReservaStatus reservado;

    @OneToMany(mappedBy = "quarto")
    @JsonIgnoreProperties("quarto")
    private List<ReservaEntity> reservas;

    @ManyToOne
    @JoinColumn(name = "tipo_quarto_id")
    private TipoQuartoEntity tipoQuarto;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotel;

    @OneToMany(mappedBy = "quarto")
    @JsonIgnoreProperties("quarto")
    private List<HospedeEntity> hospedes;
}