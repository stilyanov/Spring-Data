package exam.service.impl;

import exam.model.dto.shop.ShopSeedDto;
import exam.model.dto.shop.ShopSeedRootDto;
import exam.model.entity.Shop;
import exam.model.entity.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    private static final String SHOP_FILE_PATH = "src/main/resources/files/xml/shops.xml";
    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final StringBuilder stringBuilder;

    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository, ValidationUtil validationUtil, ModelMapper modelMapper, StringBuilder stringBuilder) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.stringBuilder = stringBuilder;
    }


    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(SHOP_FILE_PATH));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ShopSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ShopSeedRootDto shopSeedRootDto = (ShopSeedRootDto) unmarshaller.unmarshal(new File(SHOP_FILE_PATH));

        for (ShopSeedDto shopSeedDto : shopSeedRootDto.getShopSeedDtos()) {
            Optional<Shop> optionalShop = this.shopRepository.findByName(shopSeedDto.getName());
            Optional<Town> optionalTown = this.townRepository.findByName(shopSeedDto.getTownNameDto().getName());

            if (!this.validationUtil.isValid(shopSeedDto) || optionalShop.isPresent() || optionalTown.isEmpty()) {
                stringBuilder.append("Invalid shop\n");
                continue;
            }

            Shop shop = this.modelMapper.map(shopSeedDto, Shop.class);
            shop.setTown(optionalTown.get());

            this.shopRepository.saveAndFlush(shop);

            stringBuilder.append(String.format("Successfully imported Shop %s - %.0f\n",
                    shop.getName(), shop.getIncome()));

        }


        return stringBuilder.toString();
    }
}
