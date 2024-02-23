import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class P13_RemoveTowns {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        String townInput = scanner.nextLine();

        List<Town> resultList = entityManager.createQuery("FROM Town WHERE name = :town", Town.class)
                .setParameter("town", townInput)
                .getResultList();

        if (!resultList.isEmpty()) {
            Town town = resultList.get(0);
            List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a JOIN a.town t WHERE t.name = :name", Address.class)
                    .setParameter("name", town.getName())
                    .getResultList();

            for (Address address : addresses) {
                address.getEmployees().forEach(employee -> {
                    employee.setAddress(null);
                    entityManager.persist(employee);
                });
                entityManager.remove(address);
            }

            System.out.printf("%d addresses in %s deleted", addresses.size(), town.getName());
            entityManager.remove(town);
        }

        entityManager.getTransaction().commit();
    }
}
