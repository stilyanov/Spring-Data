import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class P05_EmployeesFromDepartment {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Employee> resultList = entityManager.createQuery("FROM Employee WHERE department.id = 6 ORDER BY salary, id", Employee.class).getResultList();
        System.out.println(resultList.size());

        resultList.forEach(e -> {
            System.out.printf("%s %s from Research and Development - $%.2f%n",
                    e.getFirstName(),
                    e.getLastName(),
                    e.getSalary());
        });


    }
}
