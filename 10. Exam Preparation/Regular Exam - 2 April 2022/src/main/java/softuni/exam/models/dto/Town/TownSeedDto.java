package softuni.exam.models.dto.Town;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class TownSeedDto implements Serializable {

    @NotNull
    @Size(min = 2)
    private String townName;

    @NotNull
    @Positive
    private Integer population;

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

}
