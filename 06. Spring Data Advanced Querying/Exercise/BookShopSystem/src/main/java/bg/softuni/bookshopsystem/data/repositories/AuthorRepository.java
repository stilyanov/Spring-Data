package bg.softuni.bookshopsystem.data.repositories;

import bg.softuni.bookshopsystem.data.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Set<Author> findAllByBooksReleaseDateBefore(LocalDate date);

    @Query("FROM Author ORDER BY SIZE(books) DESC")
    Set<Author> findAllByOrderByBooksSizeDesc();

    Set<Author> findAllByFirstNameEndsWith(String word);

}
