package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "cars")
public class Car extends PassengerVehicle {
    private static final String CAR_TYPE = "Audi";


    public Car() {}
    public Car(String type, int numOfPassengers) {
        super(CAR_TYPE, numOfPassengers);
    }
}
