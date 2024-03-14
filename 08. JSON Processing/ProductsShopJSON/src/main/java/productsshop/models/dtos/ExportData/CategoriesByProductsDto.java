package productsshop.models.dtos.ExportData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CategoriesByProductsDto {

    private String category;
    private Long productsCount;
    private Double averagePrice;
    private BigDecimal totalRevenue;
}
