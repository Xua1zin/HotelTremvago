package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.HotelEntity;
import com.HotelTremvago.HotelTremvago.entities.UsuarioEntity;
import com.HotelTremvago.HotelTremvago.services.HotelService;
import com.HotelTremvago.HotelTremvago.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/save")
    public ResponseEntity<UsuarioEntity> save(@RequestBody UsuarioEntity usuarioEntity){
        try{
            UsuarioEntity usuario = usuarioService.save(usuarioEntity);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String usuario = usuarioService.delete(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioEntity> update(@RequestBody UsuarioEntity usuarioEntity,   Long id){
        try{
            UsuarioEntity usuario = usuarioService.update(usuarioEntity, id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<UsuarioEntity> findById(@PathVariable Long id){
        try{
            UsuarioEntity usuario = usuarioService.findById(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UsuarioEntity>> findAll(){
        try{
            List<UsuarioEntity> usuario = usuarioService.findAll();
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
