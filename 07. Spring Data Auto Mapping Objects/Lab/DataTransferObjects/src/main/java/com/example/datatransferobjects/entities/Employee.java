package com.example.datatransferobjects.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")

    private String lastName;
    private BigDecimal salary;
    private LocalDate birthday;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Address address;
    @Column(name = "work_status")
    private WorkStatus workStatus;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    @OneToMany(mappedBy = "manager")
    private List<Employee> employees;

    public Employee() {
        employees = new ArrayList<>();
    }

    public Employee(String firstName, String lastName, BigDecimal salary, LocalDate birthday, Address address, WorkStatus workStatus, Employee manager, List<Employee> employees) {
        this();

        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
        this.workStatus = workStatus;
        this.manager = manager;
        this.employees = employees;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public WorkStatus getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(WorkStatus workStatus) {
        this.workStatus = workStatus;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                '}';
    }
}
