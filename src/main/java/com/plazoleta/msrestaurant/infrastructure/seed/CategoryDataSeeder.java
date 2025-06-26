package com.plazoleta.msrestaurant.infrastructure.seed;

import com.plazoleta.msrestaurant.infrastructure.output.jpa.entity.CategoryEntity;
import com.plazoleta.msrestaurant.infrastructure.output.jpa.repository.ICategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryDataSeeder {

    private final ICategoryRepository categoryRepository;

    @PostConstruct
    public void seedCategories() {
        if (!categoryRepository.findByName("Bebidas").isPresent()) {
            CategoryEntity bebidas = CategoryEntity.builder()
                    .name("Bebidas")
                    .description("Bebidas frías y calientes")
                    .build();
            categoryRepository.save(bebidas);
        }

        if (!categoryRepository.findByName("Comida rápida").isPresent()) {
            CategoryEntity comidaRapida = CategoryEntity.builder()
                    .name("Comida rápida")
                    .description("Hamburguesas, pizzas, papas, etc.")
                    .build();
            categoryRepository.save(comidaRapida);
        }

        if (!categoryRepository.findByName("Comida gourmet").isPresent()) {
            CategoryEntity comidaRapida = CategoryEntity.builder()
                    .name("Comida gourmet")
                    .description("Comida gourmet elaborada")
                    .build();
            categoryRepository.save(comidaRapida);
        }

        System.out.println("✅ Categorías semilla insertadas");
    }
}
