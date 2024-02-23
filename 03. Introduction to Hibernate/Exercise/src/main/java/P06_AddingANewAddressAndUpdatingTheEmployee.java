import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class P06_AddingANewAddressAndUpdatingTheEmployee {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        String lastName = scanner.nextLine();

        entityManager.getTransaction().begin();

        Town town = entityManager.find(Town.class, 32);

        Address address = new Address();
        address.setText("Vitoshka 15");
        address.setTown(town);
        entityManager.persist(address);

        List<Employee> resultList = entityManager.createQuery("FROM Employee WHERE lastName = ?1", Employee.class)
                .setParameter(1, lastName).getResultList();

        if (!resultList.isEmpty()) {
            Employee employee = resultList.get(0);
            employee.setAddress(address);
            entityManager.persist(employee);
        }


        entityManager.getTransaction().commit();
    }
}
