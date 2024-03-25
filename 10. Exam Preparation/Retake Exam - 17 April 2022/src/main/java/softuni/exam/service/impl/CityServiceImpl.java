package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.CitySeedDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private static final String CITY_FILE_PATH = "src/main/resources/files/json/cities.json";

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final StringBuilder sb;

    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson, StringBuilder sb) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.sb = sb;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITY_FILE_PATH));
    }

    @Override
    public String importCities() throws IOException {
        CitySeedDto[] citySeedDto = this.gson.fromJson(readCitiesFileContent(), CitySeedDto[].class);

        for (CitySeedDto seedDto : citySeedDto) {
            Optional<City> cityName = this.cityRepository.findByCityName(seedDto.getCityName());
            Optional<Country> countryId = this.countryRepository.findById(seedDto.getCountry());


            if (!this.validationUtil.isValid(seedDto) || cityName.isPresent() || countryId.isEmpty()) {
                sb.append("Invalid city\n");
                continue;
            }

            City city = this.modelMapper.map(seedDto, City.class);
            city.setCountry(countryId.get());

            this.cityRepository.saveAndFlush(city);

            sb.append(String.format("Successfully imported city %s - %d\n",
                    city.getCityName(), city.getPopulation()));
        }


        return sb.toString();
    }
}
