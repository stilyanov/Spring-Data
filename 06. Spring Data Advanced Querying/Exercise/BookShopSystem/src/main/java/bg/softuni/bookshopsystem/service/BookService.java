package bg.softuni.bookshopsystem.service;

import bg.softuni.bookshopsystem.data.models.enums.AgeRestriction;
import bg.softuni.bookshopsystem.data.models.enums.EditionType;
import bg.softuni.bookshopsystem.data.repositories.BookInfo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<String> findAllBookAfterYear2000();

    List<String> findAllBooksByGeorgePowellOrdered();

    List<String> findAllBooksByAgeRestriction(AgeRestriction ageRestriction);

    List<String> findAllBooksTitlesByEditionTypeGold(EditionType editionType, int copies);

    List<String> findAllBooksWithPriceBetween5And40(BigDecimal firstNumber, BigDecimal secondNumber);

    List<String> findAllBooksNotReleasedInGivenYear(int year);

    List<String> findAllBooksByGivenReleasedDate(int year, int month, int day);

    List<String> findAllBooksTitlesContainingGivenString(String text);

    List<String> findAllBooksByAuthorsFirstNameStartingWith(String text);

    int countAllBooksTitleLengthGreaterThan(int number);

    int countAllCopiesInBooksByAuthorsFirstNameAndLastName(String firstName, String lastName);

    BookInfo findInfoByTitle(String title);
}
