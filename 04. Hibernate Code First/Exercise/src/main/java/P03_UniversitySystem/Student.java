package P03_UniversitySystem;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "average_grade")
    private BigDecimal averageGrade;
    @Column
    private int attendance;
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;
}
