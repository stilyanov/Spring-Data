package productsshop.models.dtos.SeedData;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {
    private String firstName;
    private String lastName;
    private Integer age;
}
