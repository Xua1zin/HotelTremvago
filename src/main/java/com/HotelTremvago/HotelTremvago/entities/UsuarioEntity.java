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
@Table(name = "hotel")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String nome;
    private Date dataNascimento;
    @NotNull
    @NotEmpty
    @NotBlank
    private String cpf;
    @NotNull
    @NotEmpty
    @NotBlank
    private String rg;
    @NotNull
    @NotEmpty
    @NotBlank
    private String telefone;
    @NotNull
    @NotEmpty
    @NotBlank
    private String email;
}
