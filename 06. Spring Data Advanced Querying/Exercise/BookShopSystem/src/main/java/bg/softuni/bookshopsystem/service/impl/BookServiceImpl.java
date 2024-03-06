package bg.softuni.bookshopsystem.service.impl;

import bg.softuni.bookshopsystem.data.models.Author;
import bg.softuni.bookshopsystem.data.models.Book;
import bg.softuni.bookshopsystem.data.models.Category;
import bg.softuni.bookshopsystem.data.models.enums.AgeRestriction;
import bg.softuni.bookshopsystem.data.models.enums.EditionType;
import bg.softuni.bookshopsystem.data.repositories.BookInfo;
import bg.softuni.bookshopsystem.data.repositories.BookRepository;
import bg.softuni.bookshopsystem.service.AuthorService;
import bg.softuni.bookshopsystem.service.BookService;
import bg.softuni.bookshopsystem.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;


    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() == 0) {
            Files.readAllLines(Path.of(FILE_PATH))
                    .stream()
                    .filter(row -> !row.isEmpty())
                    .forEach(row -> {
                        String[] data = row.split("\\s+");

                        Author author = this.authorService.getRandomAuthor();
                        EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
                        LocalDate releaseDate = LocalDate.parse(data[1],
                                DateTimeFormatter.ofPattern("d/M/yyyy"));
                        int copies = Integer.parseInt(data[2]);
                        BigDecimal price = new BigDecimal(data[3]);
                        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];
                        String title = Arrays.stream(data)
                                .skip(5)
                                .collect(Collectors.joining(" "));
                        Set<Category> categories = categoryService.getRandomCategories();


                        Book book = new Book(title, editionType, price, copies, releaseDate,
                                ageRestriction, author, categories);

                        bookRepository.save(book);
                    });
        }


    }

    @Override
    public List<String> findAllBookAfterYear2000() {
        return this.bookRepository.findAllByReleaseDateAfter(LocalDate.of(2000, 12, 31))
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByGeorgePowellOrdered() {
        return this.bookRepository
                .findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle("George", "Powell")
                .stream()
                .map(book -> String.format("%s %s %d", book.getTitle(), book.getReleaseDate(), book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAgeRestriction(AgeRestriction ageRestriction) {
        return this.bookRepository.findAllByAgeRestriction(ageRestriction)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksTitlesByEditionTypeGold(EditionType editionType, int copies) {
        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(editionType, copies)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksWithPriceBetween5And40(BigDecimal firstNumber, BigDecimal secondNumber) {
        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(firstNumber, secondNumber)
                .stream()
                .map(b -> String.format("%s - $%.2f", b.getTitle(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksNotReleasedInGivenYear(int year) {
        return this.bookRepository.findAllByReleaseDateLessThanOrReleaseDateGreaterThan
                        (LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31))
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByGivenReleasedDate(int year, int month, int day) {
        return this.bookRepository.findAllByReleaseDateBefore(LocalDate.of(year, month, day))
                .stream()
                .map(b -> String.format("%s %s %.2f",
                        b.getTitle(),
                        b.getEditionType(),
                        b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksTitlesContainingGivenString(String text) {
        return this.bookRepository.findAllByTitleContaining(text)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorsFirstNameStartingWith(String text) {
        return this.bookRepository.findAllByAuthorLastNameStartingWith(text)
                .stream()
                .map(b -> String.format("%s (%s %s)",
                        b.getTitle(),
                        b.getAuthor().getFirstName(),
                        b.getAuthor().getLastName()))
                .toList();
    }

    @Override
    public int countAllBooksTitleLengthGreaterThan(int number) {
        return this.bookRepository.countByTitleLengthGreaterThan(number);
    }

    @Override
    public int countAllCopiesInBooksByAuthorsFirstNameAndLastName(String firstName, String lastName) {
        return this.bookRepository.countBookCopiesByAuthorFirstNameAndAuthorLastName(firstName, lastName);
    }

    @Override
    public BookInfo findInfoByTitle(String title) {
        return this.bookRepository.findByTitle(title);
    }


}
