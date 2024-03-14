package productsshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import productsshop.services.CategoryService;
import productsshop.services.ProductService;
import productsshop.services.UserService;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    public CommandLineRunnerImpl(UserService userService, ProductService productService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.categoryService.seedCategory();
        this.userService.seedUsers();
        this.productService.seedProducts();
//        P01_productsInRange();
//        P02_successfullySoldProducts();

        this.productService.printCategoryStatistics();
    }

    private void P01_productsInRange() {
        this.productService.printAllProductsInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
    }

    private void P02_successfullySoldProducts() {
        this.userService.printAllUsersWithSoldProducts();
    }
}
