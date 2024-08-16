package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.QuartoEntity;
import com.HotelTremvago.HotelTremvago.entities.TipoQuartoEntity;
import com.HotelTremvago.HotelTremvago.services.QuartoService;
import com.HotelTremvago.HotelTremvago.services.TipoQuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoQuarto")
public class TipoQuartoController {
    @Autowired
    private TipoQuartoService tipoQuartoService;

    @PostMapping("/save")
    public ResponseEntity<TipoQuartoEntity> save(@RequestBody TipoQuartoEntity tipoQuartoEntity){
        try{
            TipoQuartoEntity tipoQuarto = tipoQuartoService.save(tipoQuartoEntity);
            return new ResponseEntity<>(tipoQuarto, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String tipoQuarto = tipoQuartoService.delete(id);
            return new ResponseEntity<>(tipoQuarto, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TipoQuartoEntity> update(@RequestBody TipoQuartoEntity tipoQuartoEntity, Long id){
        try{
            TipoQuartoEntity tipoQuarto = tipoQuartoService.update(tipoQuartoEntity, id);
            return new ResponseEntity<>(tipoQuarto, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<TipoQuartoEntity> findById(@PathVariable Long id){
        try{
            TipoQuartoEntity tipoQuarto = tipoQuartoService.findById(id);
            return new ResponseEntity<>(tipoQuarto, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TipoQuartoEntity>> findAll(){
        try{
            List<TipoQuartoEntity> tipoQuarto = tipoQuartoService.findAll();
            return new ResponseEntity<>(tipoQuarto, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
