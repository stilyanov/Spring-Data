package bg.softuni.bookshopsystem;

import bg.softuni.bookshopsystem.service.AuthorService;
import bg.softuni.bookshopsystem.service.BookService;
import bg.softuni.bookshopsystem.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
//        getAllBooksAfter2000Year();
//        getAllAuthorsFirstAndLastNameBefore1990Year();
//        getAllAuthorsByBooksDesc();
        printBooksByGeorgePowell();
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
