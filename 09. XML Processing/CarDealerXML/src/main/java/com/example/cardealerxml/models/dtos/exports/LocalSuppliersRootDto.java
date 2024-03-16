package com.example.cardealerxml.models.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocalSuppliersRootDto implements Serializable {
    @XmlElement(name = "supplier")
    private List<LocalSuppliersDto> localSuppliersDtos;

    public List<LocalSuppliersDto> getLocalSuppliersDtos() {
        return localSuppliersDtos;
    }

    public void setLocalSuppliersDtos(List<LocalSuppliersDto> localSuppliersDtos) {
        this.localSuppliersDtos = localSuppliersDtos;
    }
}
