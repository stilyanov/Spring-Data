package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.CountrySeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private static final String COUNTRY_FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final StringBuilder sb;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, StringBuilder sb) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.sb = sb;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRY_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        CountrySeedDto[] countrySeedDto = this.gson.fromJson(readCountriesFromFile(), CountrySeedDto[].class);

        for (CountrySeedDto seedDto : countrySeedDto) {
            Optional<Country> countryName = this.countryRepository.findByCountryName(seedDto.getCountryName());

            if (!this.validationUtil.isValid(seedDto) || countryName.isPresent()) {
                sb.append("Invalid country\n");
                continue;
            }

            Country country = this.modelMapper.map(seedDto, Country.class);

            this.countryRepository.saveAndFlush(country);

            sb.append(String.format("Successfully imported country %s - %s\n",
                    country.getCountryName(), country.getCurrency()));
        }

        return sb.toString();
    }
}
