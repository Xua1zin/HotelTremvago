package com.HotelTremvago.HotelTremvago.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private Date dataInicio;
    private Date dataFinal;
    @NotNull
    @NotEmpty
    @NotBlank
    private String status;
    @NotNull
    @NotEmpty
    @NotBlank
    private Double total;

    private int usuario;
    private int hospede;
    private int quarto;
}
