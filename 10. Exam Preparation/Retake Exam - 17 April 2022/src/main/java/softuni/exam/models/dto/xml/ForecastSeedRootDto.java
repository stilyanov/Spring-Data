package softuni.exam.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastSeedRootDto implements Serializable {

    @XmlElement(name = "forecast")
    private List<ForecastSeedDto> forecastSeedDtoList;

    public List<ForecastSeedDto> getForecastSeedDtoList() {
        return forecastSeedDtoList;
    }

    public void setForecastSeedDtoList(List<ForecastSeedDto> forecastSeedDtoList) {
        this.forecastSeedDtoList = forecastSeedDtoList;
    }
}
