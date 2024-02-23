import entities.Department;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class P12_EmployeesMaximumSalaries {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Department> resultList = entityManager.createQuery("FROM Department ", Department.class).getResultList();

        for (Department department : resultList) {
            double maxSalary = department.getEmployees()
                    .stream()
                    .mapToDouble(value -> value.getSalary().doubleValue())
                    .max().orElse(0);

            if (maxSalary < 30000 || maxSalary > 70000) {
                System.out.printf("%s %.2f%n", department.getName(), maxSalary);
            }
        }

    }
}
