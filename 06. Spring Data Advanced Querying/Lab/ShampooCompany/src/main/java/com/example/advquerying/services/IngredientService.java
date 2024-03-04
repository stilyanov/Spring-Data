package com.example.advquerying.services;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {
    List<String> getAllIngredientsNameLike(String word);

    List<String> getAllIngredientsNameContainedIn(List<String> ingredientsName);

    int deleteIngredientsByGivenName(String name);

    int updateAllIngredientsByGivenPrice(BigDecimal price);

    int updateAllIngredientsPriceByGivenNames(BigDecimal price, List<String> names);
}
