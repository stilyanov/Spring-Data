package bg.softuni.bookshopsystem.service;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<String> findAllBookAfterYear2000();

    List<String> findAllBooksByGeorgePowellOrdered();
}
