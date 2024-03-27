package com.example.football.service.impl;

import com.example.football.models.dto.stat.StatSeedDto;
import com.example.football.models.dto.stat.StatSeedRootDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StatServiceImpl implements StatService {

    private static final String STAT_FILE_PATH = "src/main/resources/files/xml/stats.xml";
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final StringBuilder stringBuilder;
    private final ValidationUtil validationUtil;

    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, StringBuilder stringBuilder, ValidationUtil validationUtil) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.stringBuilder = stringBuilder;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STAT_FILE_PATH));
    }

    @Override
    public String importStats() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(StatSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StatSeedRootDto statSeedRootDto = (StatSeedRootDto) unmarshaller.unmarshal(new File(STAT_FILE_PATH));

        for (StatSeedDto statSeedDto : statSeedRootDto.getStatSeedDtoList()) {
            if (!this.validationUtil.isValid(statSeedDto)) {
                stringBuilder.append("Invalid stat\n");
                continue;
            }

            Stat stat = this.modelMapper.map(statSeedDto, Stat.class);
            this.statRepository.saveAndFlush(stat);

            stringBuilder.append(String.format("Successfully imported Stat %.2f - %.2f - %.2f\n",
                    stat.getShooting(), stat.getPassing(), stat.getEndurance()));

        }


        return stringBuilder.toString();
    }
}
