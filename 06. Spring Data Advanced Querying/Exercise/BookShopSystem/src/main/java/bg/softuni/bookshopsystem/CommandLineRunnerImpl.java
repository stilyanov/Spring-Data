package bg.softuni.bookshopsystem;

import bg.softuni.bookshopsystem.data.models.enums.AgeRestriction;
import bg.softuni.bookshopsystem.data.models.enums.EditionType;
import bg.softuni.bookshopsystem.data.repositories.BookInfo;
import bg.softuni.bookshopsystem.service.AuthorService;
import bg.softuni.bookshopsystem.service.BookService;
import bg.softuni.bookshopsystem.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;

    public CommandLineRunnerImpl(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }


    @Override
    public void run(String... args) throws Exception {
        seedData();
        Scanner scanner = new Scanner(System.in);
//        P01_booksTitlesByAgeRestriction(scanner);
//        P02_booksTitlesByEditionTypeGold();
//        P03_booksByPrice();
//        P04_booksTitlesNotReleasedInGivenYear(scanner);
//        P05_booksReleasedBeforeDate(scanner);
//        P06_authorsSearch(scanner);
//        P07_booksSearch(scanner);
//        P08_bookTitlesSearch(scanner);
//        P09_countBooks(scanner);
//        P10_totalBookCopies(scanner);
//        P11_reducedBook(scanner);
//        P12_increaseBookCopies(scanner);
//        P13_removeBooks(scanner);
        P14_storedProcedure(scanner);
    }

    private void P14_storedProcedure(Scanner scanner) {
        String[] input = scanner.nextLine().split("\\s+");
        String firstName = input[0];
        String lastName = input[1];

        int numberOfBooks = this.bookService.callStoredProcedureWithAuthorFirstAndLastName(firstName, lastName);
        System.out.printf("%s %s has written %d books%n", firstName, lastName, numberOfBooks);
    }

    private void P13_removeBooks(Scanner scanner) {
        int number = Integer.parseInt(scanner.nextLine());

        int removedBooks = this.bookService.removeBooksCopiesLessThanGivenNumber(number);
        System.out.println(removedBooks);
    }

    private void P12_increaseBookCopies(Scanner scanner) {
        String[] date = scanner.nextLine().split(" ");
        int day = Integer.parseInt(date[0]);
        String monthInput = date[1];
        int month = 0;
        switch (monthInput) {
            case "Oct":
                month = 10;
                break;
            case "Jun":
                month = 6;
                break;
        }
        int year = Integer.parseInt(date[2]);
        int copies = Integer.parseInt(scanner.nextLine());

        int updatedCopies = this.bookService.updateCopiesOfAllBooksByGivenCopiesAfterGivenDate(LocalDate.of(year, month, day), copies);

        System.out.printf("%d books are released after %d %s %d, so a total of %d book copies were added.%n",
                updatedCopies,
                day, monthInput, year, updatedCopies * copies);
    }

    private void P11_reducedBook(Scanner scanner) {
        String title = scanner.nextLine();
        BookInfo infoByTitle = this.bookService.findInfoByTitle(title);

        System.out.printf("%s %s %s %.2f%n",
                infoByTitle.getTitle(),
                infoByTitle.getEditionType(),
                infoByTitle.getAgeRestriction(),
                infoByTitle.getPrice());
    }

    private void P10_totalBookCopies(Scanner scanner) {
        String[] input = scanner.nextLine().split(" ");
        int count = this.bookService.countAllCopiesInBooksByAuthorsFirstNameAndLastName(input[0], input[1]);
        System.out.printf("%s %s - %d%n", input[0], input[1], count);
    }

    private void P09_countBooks(Scanner scanner) {
        int number = Integer.parseInt(scanner.nextLine());
        int titleLength = this.bookService.countAllBooksTitleLengthGreaterThan(number);
        System.out.printf("There are %d books with longer titles than %d symbols.%n", titleLength, number);
    }

    private void P08_bookTitlesSearch(Scanner scanner) {
        String text = scanner.nextLine();
        this.bookService.findAllBooksByAuthorsFirstNameStartingWith(text)
                .forEach(System.out::println);
    }

    private void P07_booksSearch(Scanner scanner) {
        String text = scanner.nextLine();
        this.bookService.findAllBooksTitlesContainingGivenString(text)
                .forEach(System.out::println);
    }

    private void P06_authorsSearch(Scanner scanner) {
        String word = scanner.nextLine();
        this.authorService.getAllAuthorsNamesEndingWith(word)
                .forEach(System.out::println);
    }

    private void P05_booksReleasedBeforeDate(Scanner scanner) {
        String[] input = scanner.nextLine().split("-");

//        String beforeDate = scanner.nextLine();
//        LocalDate parsedDate = LocalDate.parse(beforeDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        int day = Integer.parseInt(input[0]);
        int month = Integer.parseInt(input[1]);
        int year = Integer.parseInt(input[2]);
        this.bookService.findAllBooksByGivenReleasedDate(year, month, day)
                .forEach(System.out::println);
    }

    private void P04_booksTitlesNotReleasedInGivenYear(Scanner scanner) {
        int year = Integer.parseInt(scanner.nextLine());
        this.bookService.findAllBooksNotReleasedInGivenYear(year)
                .forEach(System.out::println);
    }

    private void P03_booksByPrice() {
        this.bookService.findAllBooksWithPriceBetween5And40(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .forEach(System.out::println);
    }

    private void P02_booksTitlesByEditionTypeGold() {
        this.bookService.findAllBooksTitlesByEditionTypeGold(EditionType.GOLD, 5000)
                .forEach(System.out::println);
    }

    private void P01_booksTitlesByAgeRestriction(Scanner scanner) {
        String ageRestriction = scanner.nextLine().toUpperCase();
        this.bookService.findAllBooksByAgeRestriction(AgeRestriction.valueOf(ageRestriction))
                .forEach(System.out::println);
    }

    private void printBooksByGeorgePowell() {
        this.bookService.findAllBooksByGeorgePowellOrdered()
                .forEach(System.out::println);
    }

    private void getAllAuthorsByBooksDesc() {
        this.authorService.getAllAuthorsDescBooks()
                .forEach(System.out::println);
    }

    private void getAllAuthorsFirstAndLastNameBefore1990Year() {
        this.authorService.getAllAuthorsFirstAndLastNameBeforeYear1990()
                .forEach(System.out::println);
    }

    private void getAllBooksAfter2000Year() {
        this.bookService.findAllBookAfterYear2000()
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();
    }
}
