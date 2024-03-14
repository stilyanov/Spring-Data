package productsshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productsshop.models.dtos.ExportData.CategoriesByProductsDto;
import productsshop.models.entities.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Set<Product> findByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal from, BigDecimal to);


    @Query("SELECT new productsshop.models.dtos.ExportData.CategoriesByProductsDto" +
            "(c.name, COUNT(p), AVG(p.price), SUM(p.price))" +
            " FROM Product p" +
            " JOIN p.categories c " +
            " GROUP BY c")
    List<CategoriesByProductsDto> getCategoryStats();
}
