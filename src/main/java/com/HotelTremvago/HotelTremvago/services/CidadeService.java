package com.HotelTremvago.HotelTremvago.services;

import com.HotelTremvago.HotelTremvago.entities.CidadeEntity;
import com.HotelTremvago.HotelTremvago.entities.HospedeEntity;
import com.HotelTremvago.HotelTremvago.repositories.CidadeRepository;
import com.HotelTremvago.HotelTremvago.repositories.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public CidadeEntity save(CidadeEntity cidadeEntity) {
        try {
            return cidadeRepository.save(cidadeEntity);
        } catch (Exception e) {
            System.out.println("Não foi possível salvar a cidade: " + e.getMessage());
            return new CidadeEntity();
        }
    }

    public List<CidadeEntity> saveAll(List<CidadeEntity> cidades) {
        try {
            return cidadeRepository.saveAll(cidades);
        } catch (Exception e) {
            System.out.println("Não foi possível salvar a lista de cidades: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public String delete(Long id) {
        try {
            cidadeRepository.deleteById(id);
            return "Cidade deletada com sucesso";
        } catch (Exception e) {
            System.out.println("Não foi possível deletar a cidade: " + e.getMessage());
            return "Não foi possível deletar a cidade";
        }
    }

    public CidadeEntity update(CidadeEntity cidadeEntity, Long id) {
        try {
            if (cidadeRepository.existsById(id)) {
                cidadeEntity.setId(id);
                return cidadeRepository.save(cidadeEntity);
            } else {
                throw new Exception("Cidade não encontrada");
            }
        } catch (Exception e) {
            System.out.println("Não foi possível atualizar a cidade: " + e.getMessage());
            return new CidadeEntity();
        }
    }

    public CidadeEntity findById(Long id) {
        try {
            Optional<CidadeEntity> cidadeOpt = cidadeRepository.findById(id);
            if (cidadeOpt.isPresent()) {
                return cidadeOpt.get();
            } else {
                throw new Exception("Cidade não encontrada");
            }
        } catch (Exception e) {
            System.out.println("Não foi possível encontrar a cidade com o ID: " + e.getMessage());
            return new CidadeEntity();
        }
    }

    public List<CidadeEntity> findAll() {
        try {
            return cidadeRepository.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao encontrar a lista de cidades: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<CidadeEntity> findByNome(String nome) {
        try {
            return cidadeRepository.findByCidade(nome);
        } catch (Exception e) {
            System.out.println("Não foi possível encontrar cidades por nome: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<CidadeEntity> findByEstado(String estado) {
        try {
            return cidadeRepository.findByEstado(estado);
        } catch (Exception e) {
            System.out.println("Não foi possível encontrar cidades por estado: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<CidadeEntity> findByNomeAndEstado(String nome, String estado) {
        try {
            return cidadeRepository.findByCidadeAndEstado(nome, estado);
        } catch (Exception e) {
            System.out.println("Não foi possível encontrar cidades por nome e estado: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}