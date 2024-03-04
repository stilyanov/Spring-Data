package com.example.advquerying.services.impl;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import com.example.advquerying.services.IngredientService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<String> getAllIngredientsNameLike(String word) {
        Set<Ingredient> allByNameIsLike = this.ingredientRepository.findAllByNameStartingWith(word);
        return allByNameIsLike.stream()
                .map(i -> String.format("%s", i.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllIngredientsNameContainedIn(List<String> ingredientsName) {
        return this.ingredientRepository.findAllByNameInOrderByPrice(ingredientsName)
                .stream()
                .map(i -> String.format("%s", i.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public int deleteIngredientsByGivenName(String name) {
        return this.ingredientRepository.deleteIngredientByName(name);
    }

    @Override
    public int updateAllIngredientsByGivenPrice(BigDecimal price) {
        return this.ingredientRepository.updateAllByPrice(price);
    }

    @Override
    public int updateAllIngredientsPriceByGivenNames(BigDecimal percent, List<String> names) {
        return this.ingredientRepository.updateAllByPriceForGivenNames(percent, names);
    }


}
