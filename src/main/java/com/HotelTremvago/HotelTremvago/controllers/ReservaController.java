package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.QuartoEntity;
import com.HotelTremvago.HotelTremvago.entities.ReservaEntity;
import com.HotelTremvago.HotelTremvago.entities.ReservaStatus;
import com.HotelTremvago.HotelTremvago.repositories.QuartoRepository;
import com.HotelTremvago.HotelTremvago.repositories.ReservaRepository;
import com.HotelTremvago.HotelTremvago.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reserva")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private QuartoRepository quartoRepository;

    @PostMapping("/criarReserva")
    public ResponseEntity<ReservaEntity> save(@RequestBody ReservaEntity reservaEntity) {
        try {
            long idQuarto = reservaEntity.getQuarto().getId();
            Optional<QuartoEntity> quartoEntity = quartoRepository.findById(idQuarto);
            int capacidade = quartoEntity.get().getCapacidade();

            boolean dataVerificada = reservaService.verificaDisponibilidade(
                    idQuarto,
                    capacidade,
                    reservaEntity.getDataInicio(),
                    reservaEntity.getDataFinal());
            if (dataVerificada) {
                ReservaEntity reserva = reservaService.save(reservaEntity);
                return new ResponseEntity<>(reserva, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/consultarReserva/{tipoQuartoId}/{capacidade}/{mes}/{ano}")
    public ResponseEntity<List<Integer>> listaDiasDisponiveisPorMes(
            @PathVariable Long tipoQuartoId,
            @PathVariable int capacidade,
            @PathVariable int mes,
            @PathVariable int ano) {
        try {
            List<Integer> datasDisponiveis = reservaService.datasLivres(tipoQuartoId, capacidade, mes, ano);
            return new ResponseEntity<>(datasDisponiveis, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // Log de stack trace para diagnóstico
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/cancela/{id}")
    public ResponseEntity<ReservaEntity> cancela(@PathVariable Long id) {
        try {
            Optional<ReservaEntity> optionalReserva = reservaRepository.findById(id);
            if (optionalReserva.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            ReservaEntity reservaEntity = optionalReserva.get();
            reservaEntity.setStatus(ReservaStatus.valueOf("CANCELADO"));

            ReservaEntity cancelado = reservaRepository.save(reservaEntity);

            return new ResponseEntity<>(cancelado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ReservaEntity> update(@RequestBody ReservaEntity reservaEntity, Long id){
        try{
            ReservaEntity reserva = reservaService.update(reservaEntity, id);
            return new ResponseEntity<>(reserva, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<ReservaEntity> findById(@PathVariable Long id){
        try{
            ReservaEntity reserva = reservaService.findById(id);
            return new ResponseEntity<>(reserva, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ReservaEntity>> findAll(){
        try{
            List<ReservaEntity> reserva = reservaService.findAll();
            return new ResponseEntity<>(reserva, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/addHospede/{reservaId}/{hospedeId}")
    public ResponseEntity<ReservaEntity> addHospedeToReserva(@PathVariable Long reservaId, @PathVariable Long hospedeId) {
        try {
            ReservaEntity reserva = reservaService.addHospedeToReserva(reservaId, hospedeId);
            return new ResponseEntity<>(reserva, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/removeHospede/{reservaId}/{hospedeId}")
    public ResponseEntity<ReservaEntity> removeHospedeFromReserva(@PathVariable Long reservaId, @PathVariable Long hospedeId) {
        try {
            ReservaEntity reserva = reservaService.removeHospedeFromReserva(reservaId, hospedeId);
            return new ResponseEntity<>(reserva, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/checkin/{id}")
public ResponseEntity<ReservaEntity> realizarCheckIn(@PathVariable Long id) {
    try {
        ReservaEntity reserva = reservaService.realizarCheckIn(id);
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (IllegalStateException e) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    } catch (RuntimeException e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
}
