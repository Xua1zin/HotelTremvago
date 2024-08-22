package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.QuartoEntity;
import com.HotelTremvago.HotelTremvago.entities.UsuarioEntity;
import com.HotelTremvago.HotelTremvago.repositories.QuartoRepository;
import com.HotelTremvago.HotelTremvago.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioEntity save(UsuarioEntity usuarioEntity){
        try{
            return usuarioRepository.save(usuarioEntity);
        } catch(Exception e){
            System.out.println("Nao foi possivel salvar usuario: " + e.getMessage());
            return new UsuarioEntity();
        }
    }

    public String delete(Long id){
        try {
            usuarioRepository.deleteById(id);
            return "Usuario deletado";
        } catch (Exception e){
            return "Nao foi possivel deletar usuario";
        }
    }

    public UsuarioEntity update(UsuarioEntity usuarioEntity, Long id) {
        try {
            UsuarioEntity usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID " + id));

            usuario.setNome(usuarioEntity.getNome());
            usuario.setDataNascimento(usuarioEntity.getDataNascimento());
            usuario.setCpf(usuarioEntity.getCpf());
            usuario.setRg(usuarioEntity.getRg());
            usuario.setTelefone(usuarioEntity.getTelefone());
            usuario.setEmail(usuarioEntity.getEmail());

            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.out.println("Não foi possível atualizar o usuário: " + e.getMessage());
            return null;
        }
    }

    public UsuarioEntity findById(Long id){
        try{
            return usuarioRepository.findById(id).orElseThrow();
        }catch(Exception e){
            System.out.println("Nao foi possivel encontrar um usuario com este ID: " + e.getMessage());
            return new UsuarioEntity();
        }
    }

    public List<UsuarioEntity> findAll() {
        try{
            return usuarioRepository.findAll();
        } catch(Exception e) {
            System.out.println("Erro ao encontrar lista de usuario: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
