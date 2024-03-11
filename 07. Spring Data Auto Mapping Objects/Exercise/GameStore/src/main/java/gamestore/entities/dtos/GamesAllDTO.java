package gamestore.entities.dtos;

import java.math.BigDecimal;

public class GamesAllDTO {
    private String title;
    private BigDecimal price;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return title + " " + price;
    }
}
