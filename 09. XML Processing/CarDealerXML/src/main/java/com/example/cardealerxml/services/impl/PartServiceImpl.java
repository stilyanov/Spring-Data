package com.example.cardealerxml.services.impl;

import com.example.cardealerxml.models.dtos.imports.PartsDto.PartSeedDto;
import com.example.cardealerxml.models.dtos.imports.PartsDto.CarSeedRootDto;
import com.example.cardealerxml.models.entities.Part;
import com.example.cardealerxml.models.entities.Supplier;
import com.example.cardealerxml.repositories.PartRepository;
import com.example.cardealerxml.repositories.SupplierRepository;
import com.example.cardealerxml.services.PartService;
import com.example.cardealerxml.util.ValidationUtil;
import com.example.cardealerxml.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {
    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/parts.xml";
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedParts() throws JAXBException {
        if (this.partRepository.count() == 0) {
            CarSeedRootDto partSeedRootDto = xmlParser.parse(CarSeedRootDto.class, FILE_IMPORT_PATH);

            for (PartSeedDto partSeedDto : partSeedRootDto.getPartSeedDtoList()) {
                if (!this.validationUtil.isValid(partSeedDto)) {
                    this.validationUtil.getViolations(partSeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));

                    continue;
                }

                Part part = this.modelMapper.map(partSeedDto, Part.class);
                part.setSupplier(getRandomSupplier());

                this.partRepository.saveAndFlush(part);
            }
        }
    }

    private Supplier getRandomSupplier() {
        return this.supplierRepository
                .findById(ThreadLocalRandom.current().nextLong(1, this.supplierRepository.count() + 1))
                .get();
    }
}
