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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String nomeFantasia;
    @NotNull
    @NotEmpty
    @NotBlank
    private String nomeJuridico;
    @NotNull
    @NotEmpty
    @NotBlank
    private String cnpj;
    @NotNull
    @NotEmpty
    @NotBlank
    private String cep;
    private String cidade;
    @NotNull
    @NotEmpty
    @NotBlank
    private String email;
    @NotNull
    @NotEmpty
    @NotBlank
    private String telefone;
    private int quarto;


    @OneToMany(mappedBy = "hotel")
    private List<QuartoEntity> quartos;

//    @ManyToMany(mappedBy = "hotel_hospedes")
//    @JsonIgnoreProperties("hotel_hospedes")
//    private List<HospedeEntity> hotel_hospedes;








}
