package productsshop.models.dtos.ExportData;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserWithSoldProductsDto {
    private String firstName;
    private String lastName;
    private List<SoldProductsDto> bought;
}
