package com.example.cardealerxml.models.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesWithDiscountRootDto implements Serializable {
    @XmlElement(name = "sale")
    private List<SalesWithDiscountDto> salesWithDiscountDtoList;

    public List<SalesWithDiscountDto> getSalesWithDiscountDtoList() {
        return salesWithDiscountDtoList;
    }

    public void setSalesWithDiscountDtoList(List<SalesWithDiscountDto> salesWithDiscountDtoList) {
        this.salesWithDiscountDtoList = salesWithDiscountDtoList;
    }
}
