package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Set<Ingredient> findAllByNameStartingWith(String word);

    Set<Ingredient> findAllByNameInOrderByPrice(List<String> ingredientNames);

    @Transactional
    @Modifying
    @Query("DELETE Ingredient WHERE name = :ingredientName")
    int deleteIngredientByName(String ingredientName);

    @Transactional
    @Modifying
    @Query("UPDATE Ingredient SET price = price * :price")
    int updateAllByPrice(BigDecimal price);

    @Transactional
    @Modifying
    @Query("UPDATE Ingredient SET price = price * :percent WHERE name IN :names")
    int updateAllByPriceForGivenNames(BigDecimal percent, List<String> names);
}
