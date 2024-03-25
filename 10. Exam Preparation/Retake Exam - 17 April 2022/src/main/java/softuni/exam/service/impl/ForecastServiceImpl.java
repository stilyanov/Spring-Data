package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.ForecastSeedDto;
import softuni.exam.models.dto.xml.ForecastSeedRootDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class ForecastServiceImpl implements ForecastService {

    private static final String FORECAST_FILE_PATH = "src/main/resources/files/xml/forecasts.xml";

    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final StringBuilder sb;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, StringBuilder sb, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.sb = sb;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECAST_FILE_PATH));
    }

    @Override
    public String importForecasts() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ForecastSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ForecastSeedRootDto forecastSeedRootDto = (ForecastSeedRootDto) unmarshaller.unmarshal(new File(FORECAST_FILE_PATH));

        for (ForecastSeedDto forecastSeedDto : forecastSeedRootDto.getForecastSeedDtoList()) {
            Optional<Forecast> dayOfWeekAndCityId = this.forecastRepository
                    .findByDayOfWeekAndCityId(forecastSeedDto.getDayOfWeek(), forecastSeedDto.getCity());

            if (!this.validationUtil.isValid(forecastSeedDto) || dayOfWeekAndCityId.isPresent()) {
                sb.append("Invalid forecast\n");
                continue;
            }

            Forecast forecast = this.modelMapper.map(forecastSeedDto, Forecast.class);
            City city = this.cityRepository.findFirstById(forecastSeedDto.getCity());
            forecast.setCity(city);

            this.forecastRepository.saveAndFlush(forecast);

            sb.append(String.format("Successfully import forecast %s - %s\n",
                    forecast.getDayOfWeek(), forecast.getMaxTemperature()));

        }


        return sb.toString();
    }

    @Override
    public String exportForecasts() {
        List<Forecast> forecasts = this.forecastRepository
                .findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc
                        (DayOfWeek.SUNDAY, 150000);

        forecasts.forEach(forecast -> {
            sb.append(String.format("""
                                    City: %s:
                                    -min temperature: %.2f
                                    --max temperature: %.2f
                                    ---sunrise: %s
                                    ----sunset: %s""",
                            forecast.getCity().getCityName(),
                            forecast.getMinTemperature(),
                            forecast.getMaxTemperature(),
                            forecast.getSunrise(),
                            forecast.getSunset()))
                    .append(System.lineSeparator());
        });

        return sb.toString().trim();
    }
}
