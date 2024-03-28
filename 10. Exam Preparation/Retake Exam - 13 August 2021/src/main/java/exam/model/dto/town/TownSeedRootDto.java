package exam.model.dto.town;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownSeedRootDto implements Serializable {

    @XmlElement(name = "town")
    private List<TownSeedDto> townSeedDtoList;

    public List<TownSeedDto> getTownSeedDtoList() {
        return townSeedDtoList;
    }

    public void setTownSeedDtoList(List<TownSeedDto> townSeedDtoList) {
        this.townSeedDtoList = townSeedDtoList;
    }
}
