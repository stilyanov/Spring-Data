import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class P09_FindTheLatest10Projects {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("softuni_jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Project> projects = entityManager.createQuery("FROM Project ORDER BY startDate DESC, name", Project.class)
                .setMaxResults(10)
                .getResultList();

        for (Project project : projects) {
            System.out.printf("Project name: %s%n" +
                    "   Project Description: %s%n" +
                    "   Project Start Date:%s%n" +
                    "   Project End Date: %s%n",
                    project.getName(),
                    project.getDescription(),
                    project.getStartDate(),
                    project.getEndDate());
        }
    }
}
