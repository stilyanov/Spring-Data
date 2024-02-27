package P02_SalesDatabase;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "store_location")
public class StoreLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "location_name")
    private String locationName;
    @OneToMany(mappedBy = "storeLocation", cascade = CascadeType.ALL)
    private Set<Sale> sales;
}
