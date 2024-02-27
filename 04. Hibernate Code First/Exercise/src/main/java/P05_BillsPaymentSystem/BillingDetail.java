package P05_BillsPaymentSystem;

import jakarta.persistence.*;

@Entity
@Table(name = "billing_details")
public abstract class BillingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String number;
    @ManyToOne
    private User owner;
}
