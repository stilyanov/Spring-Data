package com.example.football.models.dto.stat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatSeedRootDto implements Serializable {

    @XmlElement(name = "stat")
    private List<StatSeedDto> statSeedDtoList;

    public List<StatSeedDto> getStatSeedDtoList() {
        return statSeedDtoList;
    }

    public void setStatSeedDtoList(List<StatSeedDto> statSeedDtoList) {
        this.statSeedDtoList = statSeedDtoList;
    }
}
