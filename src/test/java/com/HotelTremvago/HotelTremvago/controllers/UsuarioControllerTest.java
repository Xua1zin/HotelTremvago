package com.HotelTremvago.HotelTremvago.controllers;

import com.HotelTremvago.HotelTremvago.entities.UsuarioEntity;
import com.HotelTremvago.HotelTremvago.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void testSaveUsuario_Success() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        when(usuarioService.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        ResponseEntity<UsuarioEntity> response = usuarioController.save(usuarioEntity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioEntity, response.getBody());
        verify(usuarioService).save(any(UsuarioEntity.class));
    }

    @Test
    public void testSaveUsuario_Failure() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        when(usuarioService.save(any(UsuarioEntity.class))).thenThrow(new RuntimeException());

        ResponseEntity<UsuarioEntity> response = usuarioController.save(usuarioEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(usuarioService).save(any(UsuarioEntity.class));
    }

    @Test
    public void testDeleteUsuario_Success() {
        when(usuarioService.delete(anyLong())).thenReturn("Deletado com sucesso");

        ResponseEntity<String> response = usuarioController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deletado com sucesso", response.getBody());
        verify(usuarioService).delete(anyLong());
    }

    @Test
    public void testDeleteUsuario_Failure() {
        when(usuarioService.delete(anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<String> response = usuarioController.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(usuarioService).delete(anyLong());
    }

    @Test
    public void testUpdateUsuario_Success() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        UsuarioEntity updatedUsuario = new UsuarioEntity();
        when(usuarioService.update(any(UsuarioEntity.class), anyLong())).thenReturn(updatedUsuario);

        ResponseEntity<UsuarioEntity> response = usuarioController.update(usuarioEntity, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUsuario, response.getBody());
        verify(usuarioService).update(any(UsuarioEntity.class), anyLong());
    }

    @Test
    public void testUpdateUsuario_Failure() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        when(usuarioService.update(any(UsuarioEntity.class), anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<UsuarioEntity> response = usuarioController.update(usuarioEntity, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(usuarioService).update(any(UsuarioEntity.class), anyLong());
    }

    @Test
    public void testUpdateUsuario_NotFound() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        when(usuarioService.update(any(UsuarioEntity.class), anyLong())).thenReturn(null);

        ResponseEntity<UsuarioEntity> response = usuarioController.update(usuarioEntity, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(usuarioService).update(any(UsuarioEntity.class), anyLong());
    }

    @Test
    public void testFindById_Success() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        when(usuarioService.findById(anyLong())).thenReturn(usuarioEntity);

        ResponseEntity<UsuarioEntity> response = usuarioController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioEntity, response.getBody());
        verify(usuarioService).findById(anyLong());
    }

    @Test
    public void testFindById_Failure() {
        when(usuarioService.findById(anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<UsuarioEntity> response = usuarioController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(usuarioService).findById(anyLong());
    }

    @Test
    public void testFindAll_Success() {
        List<UsuarioEntity> usuarioEntities = new ArrayList<>();
        usuarioEntities.add(new UsuarioEntity());
        when(usuarioService.findAll()).thenReturn(usuarioEntities);

        ResponseEntity<List<UsuarioEntity>> response = usuarioController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioEntities, response.getBody());
        verify(usuarioService).findAll();
    }

    @Test
    public void testFindAll_Failure() {
        when(usuarioService.findAll()).thenThrow(new RuntimeException());

        ResponseEntity<List<UsuarioEntity>> response = usuarioController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(usuarioService).findAll();
    }
}
