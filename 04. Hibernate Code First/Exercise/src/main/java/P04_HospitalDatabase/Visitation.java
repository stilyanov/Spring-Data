package P04_HospitalDatabase;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "visitations")
public class Visitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate date;
    @Column(name = "comments", columnDefinition = "TEXT")
    private String comment;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Diagnose diagnose;
    @ManyToOne
    private Medicament medicament;
}
