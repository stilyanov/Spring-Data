package com.example.cardealerxml.models.dtos.exports;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarAndPartsDto implements Serializable {
    @XmlAttribute
    private String make;
    @XmlAttribute
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private long travelledDistance;

    @XmlElement(name = "parts")
    private PartRootDto partRootDto;

    public PartRootDto getPartRootDto() {
        return partRootDto;
    }

    public void setPartRootDto(PartRootDto partRootDto) {
        this.partRootDto = partRootDto;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

}
