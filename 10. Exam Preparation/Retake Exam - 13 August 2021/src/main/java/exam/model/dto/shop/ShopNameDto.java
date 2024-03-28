package exam.model.dto.shop;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ShopNameDto implements Serializable {

    @NotNull
    @Size(min = 4)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
