package exam.model.dto.shop;

import exam.model.dto.town.TownNameDto;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement(name = "shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopSeedDto implements Serializable {

    @XmlElement
    @NotNull
    @Size(min = 4)
    private String address;

    @XmlElement(name = "employee-count")
    @NotNull
    @Min(1)
    @Max(50)
    private Integer employeeCount;

    @XmlElement
    @NotNull
    @DecimalMin(value = "20000")
    private BigDecimal income;

    @XmlElement
    @NotNull
    @Size(min = 4)
    private String name;

    @XmlElement(name = "shop-area")
    @NotNull
    @Min(150)
    private Integer shopArea;

    @XmlElement(name = "town")
    private TownNameDto townNameDto;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShopArea() {
        return shopArea;
    }

    public void setShopArea(Integer shopArea) {
        this.shopArea = shopArea;
    }

    public TownNameDto getTownNameDto() {
        return townNameDto;
    }

    public void setTownNameDto(TownNameDto townNameDto) {
        this.townNameDto = townNameDto;
    }
}
