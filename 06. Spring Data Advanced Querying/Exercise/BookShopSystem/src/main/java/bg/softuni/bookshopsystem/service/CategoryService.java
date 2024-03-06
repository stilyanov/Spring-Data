package bg.softuni.bookshopsystem.service;

import bg.softuni.bookshopsystem.data.models.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {

    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
