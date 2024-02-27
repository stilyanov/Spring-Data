package entities;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Plane extends Vehicle {
    private static final String PLANE_TYPE = "Boeing";
    private Integer passengerCapacity;

    public Plane(String model, BigDecimal price, String fuelType, Integer passengerCapacity) {
        super(PLANE_TYPE, model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
    }

    public Plane() {

    }
}
