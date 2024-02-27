package P04_HospitalDatabase;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column
    private String address;
    @Column
    private String email;
    @Column(name = "date_of_birth", columnDefinition = "DATETIME", nullable = false)
    private LocalDate dateOfBirth;
    @Column(columnDefinition = "BLOB")
    private String picture;
    @Column(name = "has_insurence", nullable = false)
    private boolean isInsured;
    @OneToMany(mappedBy = "patient")
    private Set<Visitation> visitations;
}
