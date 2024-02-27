package entities;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity

public class Truck extends TransportationVehicle {
    private static final String TRUCK_TYPE = "Scania";
    private int numOfContainers;

    public Truck() {}

    public Truck(String type, int loadCapacity, int numOfContainers) {
        super(TRUCK_TYPE, loadCapacity);
        this.numOfContainers = numOfContainers;
    }
}
