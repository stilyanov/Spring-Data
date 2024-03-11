package gamestore.entities.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DetailsGameDTO {
    private String title;
    private BigDecimal price;
    private String description;
    private String releaseDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Title: %s", title)).append(System.lineSeparator())
                .append(String.format("Price: %.2f", price)).append(System.lineSeparator())
                .append(String.format("Description: %s", description)).append(System.lineSeparator())
                .append(String.format("Release date: %s", releaseDate.toString())).append(System.lineSeparator());

        return sb.toString();
    }
}
