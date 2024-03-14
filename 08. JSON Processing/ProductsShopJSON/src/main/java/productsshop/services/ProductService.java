package productsshop.services;

import productsshop.models.dtos.ExportData.CategoriesByProductsDto;
import productsshop.models.dtos.ExportData.ProductsInRangeDto;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts() throws FileNotFoundException;

    List<ProductsInRangeDto> getAllProductsInRange(BigDecimal from, BigDecimal to);

    void printAllProductsInRange(BigDecimal from, BigDecimal to);

    List<CategoriesByProductsDto> getCategoryStatistics();

    void printCategoryStatistics();
}
