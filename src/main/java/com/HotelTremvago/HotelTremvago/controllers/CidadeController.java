package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.CidadeEntity;

import com.HotelTremvago.HotelTremvago.services.CidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @PostMapping("/save")
    public ResponseEntity<CidadeEntity> save(@RequestBody CidadeEntity cidadeEntity) {
        try {
            CidadeEntity savedCidade = cidadeService.save(cidadeEntity);
            return new ResponseEntity<>(savedCidade, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Erro ao salvar cidade: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<CidadeEntity>> saveAll(@RequestBody List<CidadeEntity> cidades) {
        try {
            List<CidadeEntity> savedCidades = cidadeService.saveAll(cidades);
            return new ResponseEntity<>(savedCidades, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Erro ao salvar cidades: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CidadeEntity> findById(@PathVariable Long id) {
        try {
            CidadeEntity cidade = cidadeService.findById(id);
            return new ResponseEntity<>(cidade, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<CidadeEntity>> findAll() {
        try {
            List<CidadeEntity> cidades = cidadeService.findAll();
            return new ResponseEntity<>(cidades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByNome")
    public ResponseEntity<List<CidadeEntity>> findByNome(@RequestParam String nome) {
        try {
            List<CidadeEntity> cidades = cidadeService.findByNome(nome);
            return new ResponseEntity<>(cidades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByEstado")
    public ResponseEntity<List<CidadeEntity>> findByEstado(@RequestParam String estado) {
        try {
            List<CidadeEntity> cidades = cidadeService.findByEstado(estado);
            return new ResponseEntity<>(cidades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}