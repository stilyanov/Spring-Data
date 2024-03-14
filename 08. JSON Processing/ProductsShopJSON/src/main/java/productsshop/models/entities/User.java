package productsshop.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 3)
    private String lastName;

    @Column
    private Integer age;

    @ManyToMany
    private Set<User> friends;

    @OneToMany(targetEntity = Product.class, mappedBy = "seller")
    private Set<Product> sold;

    @OneToMany(targetEntity = Product.class, mappedBy = "buyer")
    private Set<Product> bought;

}
