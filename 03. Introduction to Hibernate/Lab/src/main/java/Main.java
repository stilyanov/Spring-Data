import enteties.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

//        Student student = new Student();
//        student.setName("Pesho");
//        session.persist(student);

//        Student student = session.get(Student.class, 1);
//        System.out.println(student);

        List<Student> studentList = session.createQuery("FROM Student ", Student.class).list();

        for (Student student : studentList) {
            System.out.println(student.getId());
        }

        session.getTransaction().commit();
        session.close();
    }
}
