package exam.model.dto.shop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "shops")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopSeedRootDto implements Serializable {

    @XmlElement(name = "shop")
    private List<ShopSeedDto> shopSeedDtos;

    public List<ShopSeedDto> getShopSeedDtos() {
        return shopSeedDtos;
    }

    public void setShopSeedDtos(List<ShopSeedDto> shopSeedDtos) {
        this.shopSeedDtos = shopSeedDtos;
    }
}
