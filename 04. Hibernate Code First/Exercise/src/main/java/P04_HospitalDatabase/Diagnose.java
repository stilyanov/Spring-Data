package P04_HospitalDatabase;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "diagnoses")
public class Diagnose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "comments", columnDefinition = "TEXT")
    private String comment;
    @OneToMany(mappedBy = "diagnose")
    private Set<Visitation> visitations;
}
