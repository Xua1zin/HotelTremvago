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

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reserva")
public class ReservaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private Date dataInicio;

    @NotNull
    @NotBlank
    private Date dataFinal;

    @NotNull
    @NotBlank
    private String status;

    @NotNull
    @NotBlank
    private Double total;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties("reservas")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    @JsonIgnoreProperties("reservas")
    private QuartoEntity quarto;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "reserva_hospede",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "hospede_id")
    )
    @JsonIgnoreProperties("reservas")
    private List<HospedeEntity> hospedes;

    @ManyToMany
    @JoinTable(
            name = "reserva_hotel",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    @JsonIgnoreProperties("reservas")
    private List<HotelEntity> hoteis;
}

