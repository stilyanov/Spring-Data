import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class P07_AddressesWithEmployeeCount {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Address> resultList = entityManager.createQuery("FROM Address ORDER BY employees.size DESC", Address.class).setMaxResults(10).getResultList();

        for (Address address : resultList) {
            System.out.printf("%s, %s - %d employees%n",
                    address.getText(),
                    address.getTown().getName(),
                    address.getEmployees().size());
        }
    }
}
