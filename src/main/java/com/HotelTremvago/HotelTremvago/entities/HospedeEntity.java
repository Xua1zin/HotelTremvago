package com.HotelTremvago.HotelTremvago.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "hospede")
public class HospedeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    private Date dataNascimento;

    @NotNull
    @NotBlank
    private String cpf;

    @NotNull
    @NotBlank
    private String rg;

    @NotNull
    @NotBlank
    private String telefone;

    @NotNull
    @NotBlank
    private String email;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    @JsonIgnoreProperties("hospedes")
    private QuartoEntity quarto;

    @ManyToMany(mappedBy = "hospedes")
    @JsonIgnoreProperties("hospedes")
    private List<ReservaEntity> reservas;
}

