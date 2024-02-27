package P01_GringottsDatabase;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wizard_deposits")
public class WizardDeposits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name", length = 50)
    private String firstName;
    @Column(name = "last_name", length = 60, nullable = false)
    private String lastName;
    @Column(columnDefinition = "TEXT", length = 1000)
    private String notes;
    @Column(nullable = false)
    private int age;
    @Column(name = "magic_wand_creator", length = 100)
    private String magicWandCreator;
    @Column(name = "magic_wand_size")
    private int magicWandSize;
    @Column(name = "deposit_group", length = 20)
    private String depositGroup;
    @Column(name = "deposit_start_date", columnDefinition = "DATETIME")
    private LocalDateTime depositStartDate;
    @Column(name = "deposit_amount", columnDefinition = "DECIMAL(19, 2)")
    private BigDecimal depositAmount;
    @Column(name = "deposit_interest")
    private Double depositInterest;
    @Column(name = "deposit_charge")
    private Double depositCharge;
    @Column(name = "deposit_expiration_date", columnDefinition = "DATETIME")
    private LocalDateTime depositExpirationDate;
    @Column(name = "is_deposit_expired")
    private boolean isDepositExpired;


}
