import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class P04_EmployeesWithASalaryOver50000 {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Employee> resultList = entityManager.createQuery("FROM Employee WHERE salary > 50000", Employee.class)
                .getResultList();

        for (Employee employee : resultList) {
            System.out.println(employee.getFirstName());
        }
    }
}
