package productsshop.services;

import productsshop.models.dtos.ExportData.UserWithSoldProductsDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {
    void seedUsers() throws FileNotFoundException;

    List<UserWithSoldProductsDto> getUsersWithSoldProducts();

    void printAllUsersWithSoldProducts();
}
