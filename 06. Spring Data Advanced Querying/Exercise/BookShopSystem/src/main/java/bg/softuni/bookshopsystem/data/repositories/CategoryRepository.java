package bg.softuni.bookshopsystem.data.repositories;

import bg.softuni.bookshopsystem.data.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
