package com.example.advquerying.services;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<String> getAllShampooWithGivenSize(String size);

    List<String> getAllShampooWithGivenSizeOrLabelId(String size, Long id);

    List<String> getAllShampoosByPrice(BigDecimal price);

    int countAllShampoosByGivenPrice(BigDecimal price);

    List<String> getAllShampoosWithIngredients(List<String> strings);

    List<String> getAllShampoosByIngredientsCount(int number);
}
