package productsshop.models.dtos.ExportData;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SoldProductsDto {
    private String name;
    private BigDecimal price;
    private String buyerFirstName;
    private String buyerLastName;
}
