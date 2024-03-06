package bg.softuni.bookshopsystem.data.repositories;

import bg.softuni.bookshopsystem.data.models.Book;
import bg.softuni.bookshopsystem.data.models.enums.AgeRestriction;
import bg.softuni.bookshopsystem.data.models.enums.EditionType;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Set<Book> findAllByReleaseDateAfter(LocalDate date);

    Set<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle(String firstName, String lastName);

    Set<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Set<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    Set<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal firstNumber, BigDecimal secondNumber);

    Set<Book> findAllByReleaseDateLessThanOrReleaseDateGreaterThan(LocalDate start, LocalDate end);

    Set<Book> findAllByReleaseDateBefore(LocalDate date);

    Set<Book> findAllByTitleContaining(String text);

    Set<Book> findAllByAuthorLastNameStartingWith(String text);

    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :number")
    int countByTitleLengthGreaterThan(int number);

    @Query("SELECT SUM(b.copies) " +
            "FROM Author a " +
            "JOIN a.books b " +
            "WHERE a.firstName = :firstName AND a.lastName = :lastName")
    int countBookCopiesByAuthorFirstNameAndAuthorLastName(String firstName, String lastName);

    BookInfo findByTitle(String title);
}
