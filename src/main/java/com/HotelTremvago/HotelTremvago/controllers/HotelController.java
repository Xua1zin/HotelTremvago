package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.CidadeEntity;
import com.HotelTremvago.HotelTremvago.entities.HotelEntity;
import com.HotelTremvago.HotelTremvago.repositories.CidadeRepository;
import com.HotelTremvago.HotelTremvago.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private CidadeRepository cidadeRepository;

    @PostMapping("/save")
    public ResponseEntity<HotelEntity> save(@RequestBody HotelEntity hotelEntity) {
        try {
            HotelEntity hotel = hotelService.save(hotelEntity);
            Long cidadeId = hotel.getCidade().getId();

            Optional<CidadeEntity> cidadeOptional = cidadeRepository.findById(cidadeId);
            if (cidadeOptional.isPresent()) {
                hotel.setCidade(cidadeOptional.get());
                return new ResponseEntity<>(hotel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String result = hotelService.delete(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>("Não foi possível deletar hotel", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HotelEntity> update(@RequestBody HotelEntity hotelEntity, @PathVariable Long id) {
        try {
            HotelEntity updatedHotel = hotelService.update(hotelEntity, id);
            return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
        } catch (Exception e) {
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
    @PostMapping("/saveAll")
    public ResponseEntity<List<HotelEntity>> saveAll(@RequestBody List<HotelEntity> hotelEntities){
        try {
            List<HotelEntity> hotels = hotelService.saveAll(hotelEntities);
            return new ResponseEntity<>(hotels, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
