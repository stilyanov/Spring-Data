package productsshop.models.dtos.ExportData;

import lombok.Getter;
import lombok.Setter;
import productsshop.models.entities.User;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductsInRangeDto {
    private String name;
    private BigDecimal price;
    private String seller;
}
