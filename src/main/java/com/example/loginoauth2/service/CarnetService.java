package com.example.loginoauth2.service;

import com.example.loginoauth2.model.Concoction;
import com.example.loginoauth2.repository.ConcoctionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarnetService {

    private final ConcoctionRepository repository;

    // Spring injecte automatiquement le repository ici
    public CarnetService(ConcoctionRepository repository) {
        this.repository = repository;
    }

    public List<Concoction> toutesLesConcoctions() {
        return repository.findAll();
    }

    public Optional<Concoction> trouverParId(Long id) {
        return repository.findById(id);
    }
}
