package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "bikes")
public class Bike extends Vehicle {
    private static final String BIKE_TYPE = "BMX";

    public Bike() {
        super(BIKE_TYPE);
    }

    public Bike(String model, BigDecimal price, String fuelType) {
        super(BIKE_TYPE, model, price, fuelType);
    }


}
