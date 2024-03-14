package productsshop.services.impl;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import productsshop.models.dtos.ExportData.CategoriesByProductsDto;
import productsshop.models.dtos.ExportData.ProductsInRangeDto;
import productsshop.models.dtos.SeedData.ProductDto;
import productsshop.models.entities.Category;
import productsshop.models.entities.Product;
import productsshop.models.entities.User;
import productsshop.repositories.CategoryRepository;
import productsshop.repositories.ProductRepository;
import productsshop.repositories.UserRepository;
import productsshop.services.ProductService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String URL_PATH = "src/main/resources/imports/products.json";
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, Gson gson, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedProducts() throws FileNotFoundException {
        if (this.productRepository.count() == 0) {
            FileReader fileReader = new FileReader(URL_PATH);
            ProductDto[] productDtos = this.gson.fromJson(fileReader, ProductDto[].class);

            for (ProductDto productDto : productDtos) {
                Product product = this.modelMapper.map(productDto, Product.class);
                product.setBuyer(getRandomUser(true));
                product.setSeller(getRandomUser(false));
                product.setCategories(getRandomCategories());

                this.productRepository.saveAndFlush(product);
            }
        }
    }

    @Transactional
    @Override
    public List<ProductsInRangeDto> getAllProductsInRange(BigDecimal from, BigDecimal to) {
        return this.productRepository.findByPriceBetweenAndBuyerIsNullOrderByPrice(from, to)
                .stream()
                .map(p -> {
                    ProductsInRangeDto dto = this.modelMapper.map(p, ProductsInRangeDto.class);
                    dto.setSeller(p.getSeller().getFirstName() + " " + p.getSeller().getLastName());
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public void printAllProductsInRange(BigDecimal from, BigDecimal to) {
        System.out.println(this.gson.toJson(this.getAllProductsInRange(from, to)));
    }

    @Override
    public List<CategoriesByProductsDto> getCategoryStatistics() {
        return this.productRepository.getCategoryStats();
    }

    public void printCategoryStatistics() {
        String json = this.gson.toJson(getCategoryStatistics());

        System.out.println(json);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();

        int randomNum = ThreadLocalRandom.current().nextInt(1, 4);

        for (int i = 0; i < randomNum; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, this.categoryRepository.count() + 1);

            categories.add(this.categoryRepository.findById(randomId).get());
        }

        return categories;
    }

    private User getRandomUser(boolean isBuyer) {
        long randomId = ThreadLocalRandom.current().nextLong(1, this.userRepository.count() + 1);

        return isBuyer && randomId % 4 == 0 ? null : this.userRepository.findById(randomId).get();
    }
}
