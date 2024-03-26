package softuni.exam.models.dto.Offer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedRootDto implements Serializable {

    @XmlElement(name = "offer")
    private List<OfferSeedDto> offerSeedDtoList;

    public List<OfferSeedDto> getOfferSeedDtoList() {
        return offerSeedDtoList;
    }

    public void setOfferSeedDtoList(List<OfferSeedDto> offerSeedDtoList) {
        this.offerSeedDtoList = offerSeedDtoList;
    }
}
