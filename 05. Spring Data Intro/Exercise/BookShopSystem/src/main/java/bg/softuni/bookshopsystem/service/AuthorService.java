package bg.softuni.bookshopsystem.service;

import bg.softuni.bookshopsystem.data.models.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsFirstAndLastNameBeforeYear1990();

    List<String> getAllAuthorsDescBooks();
}
