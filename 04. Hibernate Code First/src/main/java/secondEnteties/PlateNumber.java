package secondEnteties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "plate_number")
public class PlateNumber {

    @Id
    private long id;

    private String number;
    @OneToOne(targetEntity = SecondCar.class, mappedBy = "plateNumber")
    private SecondCar secondCar;

    public PlateNumber() {}

    public PlateNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
