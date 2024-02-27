package P05_BillsPaymentSystem;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Column(length = 50)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "owner")
    private Set<BillingDetail> billingDetails;
}
