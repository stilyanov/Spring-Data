import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class P11_FindEmployeesByFirstName {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.nextLine();

        List<Employee> resultList = entityManager.createQuery("FROM Employee WHERE firstName LIKE CONCAT(:pattern, '%') ", Employee.class)
                .setParameter("pattern", pattern)
                .getResultList();

        for (Employee employee : resultList) {
            System.out.printf("%s %s - %s ($%.2f)%n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getJobTitle(),
                    employee.getSalary());
        }
    }
}
