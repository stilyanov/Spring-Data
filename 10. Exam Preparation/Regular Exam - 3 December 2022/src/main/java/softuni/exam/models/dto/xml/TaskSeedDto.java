package softuni.exam.models.dto.xml;

import softuni.exam.util.LocalDateAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskSeedDto implements Serializable {

    @NotNull
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDateTime date;

    @NotNull
    @XmlElement
    @Positive
    private BigDecimal price;

    @NotNull
    private CarIdDto car;

    @NotNull
    private MechanicNameDto mechanic;

    @NotNull
    private PartIdDto part;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CarIdDto getCar() {
        return car;
    }

    public void setCar(CarIdDto car) {
        this.car = car;
    }

    public MechanicNameDto getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicNameDto mechanic) {
        this.mechanic = mechanic;
    }

    public PartIdDto getPart() {
        return part;
    }

    public void setPart(PartIdDto part) {
        this.part = part;
    }
}
