package com.example.cardealerxml.models.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsFromToyotaRootDto implements Serializable {
    @XmlElement(name = "car")
    private List<CarsFromToyotaDto> carsFromToyotaDtoList;

    public List<CarsFromToyotaDto> getCarsFromToyotaDtoList() {
        return carsFromToyotaDtoList;
    }

    public void setCarsFromToyotaDtoList(List<CarsFromToyotaDto> carsFromToyotaDtoList) {
        this.carsFromToyotaDtoList = carsFromToyotaDtoList;
    }
}
