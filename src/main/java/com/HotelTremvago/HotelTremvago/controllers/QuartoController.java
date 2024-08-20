package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.QuartoEntity;
import com.HotelTremvago.HotelTremvago.services.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quarto")
public class QuartoController {
    @Autowired
    private QuartoService quartoService;

    @PostMapping("/criarQuarto")
    public ResponseEntity<String> criarQuarto(@RequestBody QuartoEntity quartoEntity) {
        try {
            QuartoEntity novoQuarto = quartoService.criarQuarto(quartoEntity);

            if (novoQuarto != null && novoQuarto.getId() != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Quarto criado com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao criar o quarto.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String quarto = quartoService.delete(id);
            return new ResponseEntity<>(quarto, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<QuartoEntity> update(@RequestBody QuartoEntity quartoEntity, Long id){
        try{
            QuartoEntity quarto = quartoService.update(quartoEntity, id);
            return new ResponseEntity<>(quarto, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<QuartoEntity> findById(@PathVariable Long id){
        try{
            QuartoEntity quarto = quartoService.findById(id);
            return new ResponseEntity<>(quarto, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<QuartoEntity>> findAll(){
        try{
            List<QuartoEntity> quarto = quartoService.findAll();
            return new ResponseEntity<>(quarto, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
