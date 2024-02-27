package P04_HospitalDatabase;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "medicaments")
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "medicament")
    private Set<Visitation> visitations;
}
