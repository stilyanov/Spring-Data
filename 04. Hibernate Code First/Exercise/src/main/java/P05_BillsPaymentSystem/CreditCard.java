package P05_BillsPaymentSystem;

import jakarta.persistence.*;

@Entity
@Table(name = "credit_cards")
public class CreditCard extends BillingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CreditCardType creditCardType;
    @Column(name = "expiration_month", nullable = false)
    private int expirationMonth;
    @Column(name = "expirationYear", nullable = false)
    private int expirationYear;
}
