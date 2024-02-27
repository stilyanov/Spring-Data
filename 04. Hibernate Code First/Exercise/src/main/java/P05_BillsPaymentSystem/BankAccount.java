package P05_BillsPaymentSystem;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "bank_name", nullable = false)
    private String name;
    @Column(name = "swift_code", nullable = false)
    private String swiftCode;
}
