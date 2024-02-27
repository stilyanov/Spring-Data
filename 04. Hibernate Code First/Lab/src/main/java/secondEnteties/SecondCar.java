package secondEnteties;

import jakarta.persistence.*;

@Entity
@Table(name = "second_car")
public class SecondCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "plate_number_id", referencedColumnName = "id")
    private PlateNumber plateNumber;

    public SecondCar() {}

    public SecondCar(PlateNumber plateNumber) {
        this.plateNumber = plateNumber;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
