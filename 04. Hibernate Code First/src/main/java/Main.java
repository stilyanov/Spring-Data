import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import secondEnteties.Article;
import secondEnteties.PlateNumber;
import secondEnteties.SecondCar;
import secondEnteties.User;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory hibernateCodeFirst = Persistence.createEntityManagerFactory("hibernate_code_first");
        EntityManager entityManager = hibernateCodeFirst.createEntityManager();

        entityManager.getTransaction().begin();

//        Vehicle bike = new Bike("BMX", BigDecimal.TEN, null);
//        Vehicle car = new Car("A3", 5);
//        Vehicle truck = new Truck("Scania", 20, 2);
//        Vehicle plane = new Plane("747", BigDecimal.ZERO, "PlanePetrol", 100);
//
//        entityManager.persist(bike);
//        entityManager.persist(car);
//        entityManager.persist(truck);
//        entityManager.persist(plane);

//        ------------------------------------------------
//
//        PlateNumber number = new PlateNumber("BT0000BT");
//        SecondCar car = new SecondCar(number);
//
//        entityManager.persist(number);
//        entityManager.persist(car);

//        ------------------------------------------------


        Article article = new Article("text");
        User author = new User("Pesho");

        author.addArticle(article);

        entityManager.persist(article);
        entityManager.persist(author);


        entityManager.getTransaction().commit();
    }
}
