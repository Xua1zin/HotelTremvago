package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.HotelEntity;
import com.HotelTremvago.HotelTremvago.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping("/save")
    public ResponseEntity<HotelEntity> save(@RequestBody HotelEntity hotelEntity){
        try{
            HotelEntity hotel = hotelService.save(hotelEntity);
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String hotel = hotelService.delete(id);
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HotelEntity> update(@RequestBody HotelEntity hotelEntity, Long id){
        try{
            HotelEntity hotel = hotelService.update(hotelEntity, id);
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<HotelEntity> findById(@PathVariable Long id){
        try{
            HotelEntity hotel = hotelService.findById(id);
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<HotelEntity>> findAll(){
        try{
            List<HotelEntity> hotel = hotelService.findAll();
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
