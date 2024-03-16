package com.example.cardealerxml.repositories;

import com.example.cardealerxml.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Set<Customer> findAllByOrderByBirthDateAscIsYoungDriverDesc();

    @Query(value = "FROM Customer WHERE SIZE(sales) > 0 ")
    Set<Customer> findAllWithBoughtCars();
}
