package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.HospedeEntity;
import com.HotelTremvago.HotelTremvago.entities.UsuarioEntity;
import com.HotelTremvago.HotelTremvago.services.HospedeService;
import com.HotelTremvago.HotelTremvago.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospede")
public class HospedeController {
    @Autowired
    private HospedeService hospedeService;

    @PostMapping("/save")
    public ResponseEntity<HospedeEntity> save(@RequestBody HospedeEntity hospedeEntity){
        try{
            HospedeEntity hospede = hospedeService.save(hospedeEntity);
            return new ResponseEntity<>(hospede, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String hospede = hospedeService.delete(id);
            return new ResponseEntity<>(hospede, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HospedeEntity> update(@RequestBody HospedeEntity hospedeEntity, Long id){
        try{
            HospedeEntity hospede = hospedeService.update(hospedeEntity, id);
            return new ResponseEntity<>(hospede, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<HospedeEntity> findById(@PathVariable Long id){
        try{
            HospedeEntity hospede = hospedeService.findById(id);
            return new ResponseEntity<>(hospede, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<HospedeEntity>> findAll(){
        try{
            List<HospedeEntity> hospede = hospedeService.findAll();
            return new ResponseEntity<>(hospede, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
