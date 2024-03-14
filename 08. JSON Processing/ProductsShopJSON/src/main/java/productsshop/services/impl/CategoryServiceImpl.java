package productsshop.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import productsshop.models.dtos.SeedData.CategoryDto;
import productsshop.models.entities.Category;
import productsshop.repositories.CategoryRepository;
import productsshop.services.CategoryService;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String URL_PATH = "src/main/resources/imports/categories.json";
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public void seedCategory() throws FileNotFoundException {
        if (this.categoryRepository.count() == 0) {
            FileReader fileReader = new FileReader(URL_PATH);

            CategoryDto[] categoryDtos = this.gson.fromJson(fileReader, CategoryDto[].class);
            for (CategoryDto categoryDto : categoryDtos) {
                Category category = this.mapper.map(categoryDto, Category.class);

                this.categoryRepository.saveAndFlush(category);
            }
        }
    }
}
