import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class P02_ChangeCasing {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Town> resultList = entityManager.createQuery("FROM Town WHERE LENGTH(name) > 5", Town.class)
                .getResultList();

        resultList.forEach(town -> {
            town.setName(town.getName().toUpperCase());
            entityManager.persist(town);
        });
    }
}
