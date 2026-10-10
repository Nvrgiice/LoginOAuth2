package com.example.loginoauth2.repository;

import com.example.loginoauth2.model.Concoction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ConcoctionRepository {

    private final List<Concoction> concoctions = List.of(
            new Concoction(1L, "Remède contre la fièvre",
                    "Décoction tiède à boire matin et soir.",
                    "Facile",
                    List.of("Écorce de saule", "Miel", "Gingembre")),

            new Concoction(2L, "Baume apaisant",
                    "Onguent à appliquer sur les irritations de la peau.",
                    "Moyen",
                    List.of("Camomille", "Cire d'abeille", "Huile d'olive")),

            new Concoction(3L, "Poudre de sommeil",
                    "À dissoudre dans une tisane avant le coucher.",
                    "Difficile",
                    List.of("Valériane", "Lavande", "Fleur de pavot"))
    );

    public List<Concoction> findAll() {
        return concoctions;
    }

    public Optional<Concoction> findById(Long id) {
        return concoctions.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }
}