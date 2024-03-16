package com.example.cardealerxml.models.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersTotalSalesRootDto implements Serializable {

    @XmlElement(name = "customer")
    private List<CustomersTotalSalesDto> customersTotalSalesDtoList;

    public List<CustomersTotalSalesDto> getCustomersTotalSalesDtoList() {
        return customersTotalSalesDtoList;
    }

    public void setCustomersTotalSalesDtoList(List<CustomersTotalSalesDto> customersTotalSalesDtoList) {
        this.customersTotalSalesDtoList = customersTotalSalesDtoList;
    }
}
