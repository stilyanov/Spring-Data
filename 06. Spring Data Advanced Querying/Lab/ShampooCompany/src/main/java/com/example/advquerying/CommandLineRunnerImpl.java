package com.example.advquerying;

import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
@Service
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    public CommandLineRunnerImpl(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
//        P01_selectShampoosBySize(scanner);
//        P02_selectShampoosBySizeOrLabel(scanner);
//        P03_selectShampoosByPrice(scanner);
//        P04_selectIngredientsByName(scanner);
//        P05_selectIngredientsByNames(scanner);
//        P06_countShampoosByPrice(scanner);
//        P07_selectShampoosByIngredients(scanner);
//        P08_selectShampoosByIngredientsCount(scanner);
//        P09_deleteIngredientsByGivenName(scanner);
//        P10_updateIngredientsByGivenPrice(scanner);
        P11_updateIngredientsByNames(scanner);
    }

    private void P11_updateIngredientsByNames(Scanner scanner) {
        double percent = Double.parseDouble(scanner.nextLine());
        String[] names = scanner.nextLine().split("\\s+");
        int rowsAffected = this.ingredientService.updateAllIngredientsPriceByGivenNames(BigDecimal.valueOf(percent), List.of(names));
        System.out.println(rowsAffected);
    }

    private void P10_updateIngredientsByGivenPrice(Scanner scanner) {
        BigDecimal price = scanner.nextBigDecimal();
        int rowsAffected = this.ingredientService.updateAllIngredientsByGivenPrice(price);
        System.out.println(rowsAffected);
    }

    private void P09_deleteIngredientsByGivenName(Scanner scanner) {
        String name = scanner.nextLine();
        int rowsAffected = this.ingredientService.deleteIngredientsByGivenName(name);
        System.out.println(rowsAffected);
    }

    private void P08_selectShampoosByIngredientsCount(Scanner scanner) {
        int number = Integer.parseInt(scanner.nextLine());
        this.shampooService.getAllShampoosByIngredientsCount(number)
                .forEach(System.out::println);
    }

    private void P07_selectShampoosByIngredients(Scanner scanner) {
        String[] input = scanner.nextLine().split("\\s+");
        this.shampooService.getAllShampoosWithIngredients(List.of(input))
                .forEach(System.out::println);
    }

    private void P06_countShampoosByPrice(Scanner scanner) {
        BigDecimal price = scanner.nextBigDecimal();
        int count = this.shampooService.countAllShampoosByGivenPrice(price);
        System.out.println(count);
    }

    private void P05_selectIngredientsByNames(Scanner scanner) {
        String[] input = scanner.nextLine().split("\\s+");
        this.ingredientService.getAllIngredientsNameContainedIn(Arrays.stream(input).toList())
                .forEach(System.out::println);
    }

    private void P04_selectIngredientsByName(Scanner scanner) {
        String word = scanner.nextLine();
        this.ingredientService.getAllIngredientsNameLike(word)
                .forEach(System.out::println);
    }

    private void P03_selectShampoosByPrice(Scanner scanner) {
        BigDecimal price = scanner.nextBigDecimal();
        this.shampooService.getAllShampoosByPrice(price)
                .forEach(System.out::println);
    }

    private void P02_selectShampoosBySizeOrLabel(Scanner scanner) {
        String inputEnum = scanner.nextLine();
        Long inputId = Long.parseLong(scanner.nextLine());
        this.shampooService.getAllShampooWithGivenSizeOrLabelId(inputEnum, inputId)
                .forEach(System.out::println);
    }

    private void P01_selectShampoosBySize(Scanner scanner) {
        String input = scanner.nextLine();
        this.shampooService.getAllShampooWithGivenSize(input)
                .forEach(System.out::println);
    }
}
