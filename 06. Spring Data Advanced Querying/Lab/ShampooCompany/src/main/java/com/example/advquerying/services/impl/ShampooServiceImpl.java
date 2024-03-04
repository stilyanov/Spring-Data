package com.example.advquerying.services.impl;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import com.example.advquerying.services.ShampooService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<String> getAllShampooWithGivenSize(String size) {
        Size sizeEnum = Size.valueOf(size.toUpperCase());
        return this.shampooRepository.findAllBySizeOrderById(sizeEnum)
                .stream()
                .map(s -> String.format("%s %s - %.2flv.",
                        s.getBrand(),
                        s.getSize().name(),
                        s.getPrice().doubleValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllShampooWithGivenSizeOrLabelId(String size, Long id) {
        Size sizeEnum = Size.valueOf(size.toUpperCase());
        Set<Shampoo> allBySizeAndLabelIdOrderByPrice = this.shampooRepository.findAllBySizeOrLabelIdOrderByPrice(sizeEnum, id);
        return allBySizeAndLabelIdOrderByPrice.stream()
                .map(s -> String.format("%s %s %.2flv.",
                        s.getBrand(),
                        s.getSize().name(),
                        s.getPrice().doubleValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllShampoosByPrice(BigDecimal price) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price)
                .stream()
                .map(s -> String.format("%s %s %.2flv.",
                        s.getBrand(),
                        s.getSize().name(),
                        s.getPrice().doubleValue()))
                .collect(Collectors.toList());
    }

    @Override
    public int countAllShampoosByGivenPrice(BigDecimal price) {
        return this.shampooRepository.countShampoosByPriceLessThan(price);
    }

    @Override
    public List<String> getAllShampoosWithIngredients(List<String> strings) {
        Set<Shampoo> allByIngredientsIn = this.shampooRepository.findAllByIngredientsNameIn(strings);
        return allByIngredientsIn
                .stream()
                .map(s -> String.format("%s", s.getBrand()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllShampoosByIngredientsCount(int number) {
        Set<Shampoo> allByCountIngredientsBy = this.shampooRepository.findAllByCountIngredientsBy(number);
        return allByCountIngredientsBy.stream()
                .map(Shampoo::getBrand)
                .collect(Collectors.toList());
    }
}
