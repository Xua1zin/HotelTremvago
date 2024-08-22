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
@Table(name = "hotel")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String nomeFantasia;

    @NotNull
    @NotBlank
    private String nomeJuridico;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String cnpj;

    @NotNull
    @NotBlank
    private String cep;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotNull
    @NotBlank
    private String telefone;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("hotel")
    private List<QuartoEntity> quartos;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    @JsonIgnoreProperties("hotelCidades")
    private CidadeEntity cidade;

    @ManyToMany(mappedBy = "hoteis", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("{hoteis, reservas}")
    @JsonIgnore
    private List<ReservaEntity> reservas;
}

