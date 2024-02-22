import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class P03_ContainsEmployee {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split("\\s+");

        List<Employee> resultList = entityManager.createQuery("FROM Employee WHERE firstName = ?1 AND lastName = ?2", Employee.class)
                .setParameter(1, input[0])
                .setParameter(2, input[1])
                .getResultList();

//        if (!resultList.isEmpty()) {
//            System.out.println("Yes");
//        } else {
//            System.out.println("No");
//        }

        System.out.println(resultList.size() > 0 ? "Yes" : "No");
    }
}
