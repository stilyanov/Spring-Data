package productsshop.models.dtos.SeedData;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto implements Serializable {
    private String name;
    private BigDecimal price;
}
