package P03_UniversitySystem;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "start_date", columnDefinition = "DATETIME", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", columnDefinition = "DATETIME", nullable = false)
    private LocalDate endDate;
    @Column
    private int credits;
    @ManyToOne
    private Teacher teacher;
    @ManyToMany
    private Set<Student> students;
}
