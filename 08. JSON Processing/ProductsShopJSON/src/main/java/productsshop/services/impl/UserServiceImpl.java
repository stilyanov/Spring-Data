package productsshop.services.impl;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import productsshop.models.dtos.ExportData.UserWithSoldProductsDto;
import productsshop.models.dtos.SeedData.UserDto;
import productsshop.models.entities.User;
import productsshop.repositories.UserRepository;
import productsshop.services.UserService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String URL_PATH = "src/main/resources/imports/users.json";

    private final UserRepository userRepository;
    private final Gson gson;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, Gson gson, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public void seedUsers() throws FileNotFoundException {
        if (this.userRepository.count() == 0) {
            FileReader fileReader = new FileReader(URL_PATH);

            List<User> users = Arrays.stream(gson.fromJson(fileReader, UserDto[].class))
                    .map(userDto -> mapper.map(userDto, User.class))
                    .toList();

            this.userRepository.saveAll(users);
        }
    }

    @Override
    @Transactional
    public List<UserWithSoldProductsDto> getUsersWithSoldProducts() {
        List<User> allWithSoldProducts = this.userRepository.findAllWithSoldProducts();

        return allWithSoldProducts.stream()
                .map(u -> this.mapper.map(u, UserWithSoldProductsDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void printAllUsersWithSoldProducts() {
        String json = this.gson.toJson(this.getUsersWithSoldProducts());
        System.out.println(json);
    }
}
