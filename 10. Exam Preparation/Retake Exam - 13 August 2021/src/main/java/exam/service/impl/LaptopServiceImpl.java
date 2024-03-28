package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.laptop.LaptopSeedDto;
import exam.model.entity.Laptop;
import exam.model.entity.Shop;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class LaptopServiceImpl implements LaptopService {

    private static final String LAPTOP_FILE_PATH = "src/main/resources/files/json/laptops.json";
    private final LaptopRepository laptopRepository;
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final StringBuilder stringBuilder;
    private final Gson gson;

    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository, ModelMapper modelMapper, ValidationUtil validationUtil, StringBuilder stringBuilder, Gson gson) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.stringBuilder = stringBuilder;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOP_FILE_PATH));
    }

    @Override
    public String importLaptops() throws IOException {
        LaptopSeedDto[] laptopSeedDto = this.gson.fromJson(readLaptopsFileContent(), LaptopSeedDto[].class);

        for (LaptopSeedDto laptopDto : laptopSeedDto) {
            Optional<Laptop> optionalLaptop = this.laptopRepository.findByMacAddress(laptopDto.getMacAddress());
            Optional<Shop> optionalShop = this.shopRepository.findByName(laptopDto.getShop().getName());

            if (!this.validationUtil.isValid(laptopDto) || optionalLaptop.isPresent() || optionalShop.isEmpty()) {
                stringBuilder.append("Invalid Laptop\n");
                continue;
            }

            Laptop laptop = this.modelMapper.map(laptopDto, Laptop.class);
            laptop.setShop(optionalShop.get());

            this.laptopRepository.saveAndFlush(laptop);

            stringBuilder
                    .append(String.format("Successfully imported Laptop %s - %.2f - %d - %d\n",
                    laptop.getMacAddress(), laptop.getCpuSpeed(), laptop.getRam(), laptop.getStorage()));

        }


        return stringBuilder.toString();
    }

    @Override
    public String exportBestLaptops() {
        List<Laptop> result = this.laptopRepository.findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc();
        result.forEach(l -> {
            stringBuilder.append(String.format("Laptop - %s\n" +
                                               "*Cpu Speed - %.2f\n" +
                                               "**Ram - %d\n" +
                                               "***Storage - %d\n" +
                                               "****Price - %.2f\n" +
                                               "#Shop name - %s\n" +
                                               "##Town - %s",
                            l.getMacAddress(),
                            l.getCpuSpeed(),
                            l.getRam(),
                            l.getStorage(),
                            l.getPrice(),
                            l.getShop().getName(),
                            l.getShop().getTown().getName()))
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        });


        return stringBuilder.toString().trim();
    }
}
