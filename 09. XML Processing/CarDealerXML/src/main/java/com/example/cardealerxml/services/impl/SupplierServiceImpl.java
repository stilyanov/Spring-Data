package com.example.cardealerxml.services.impl;

import com.example.cardealerxml.models.dtos.exports.LocalSuppliersDto;
import com.example.cardealerxml.models.dtos.exports.LocalSuppliersRootDto;
import com.example.cardealerxml.models.dtos.imports.SuppliersDto.SupplierSeedDto;
import com.example.cardealerxml.models.dtos.imports.SuppliersDto.SupplierSeedRootDto;
import com.example.cardealerxml.models.entities.Supplier;
import com.example.cardealerxml.repositories.SupplierRepository;
import com.example.cardealerxml.services.SupplierService;
import com.example.cardealerxml.util.ValidationUtil;
import com.example.cardealerxml.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/suppliers.xml";
    private static final String FILE_EXPORT_PATH = "src/main/resources/xml/exports/local-suppliers.xml";
    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public SupplierServiceImpl(SupplierRepository supplierRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.supplierRepository = supplierRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedSupplier() throws JAXBException {
        if (this.supplierRepository.count() == 0) {
            SupplierSeedRootDto supplierSeedRootDto = xmlParser.parse(SupplierSeedRootDto.class, FILE_IMPORT_PATH);

            for (SupplierSeedDto supplierSeedDto : supplierSeedRootDto.getSupplierSeedDtoList()) {
                if (!this.validationUtil.isValid(supplierSeedDto)) {
                    this.validationUtil.getViolations(supplierSeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));

                    continue;
                }

                Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);

                this.supplierRepository.saveAndFlush(supplier);
            }
        }
    }

    @Override
    public void exportLocalSuppliers() throws JAXBException {
        List<LocalSuppliersDto> localSuppliersDtos = this.supplierRepository.findAllByIsImporter(false)
                .stream()
                .map(supplier -> {
                    LocalSuppliersDto suppliersDto = this.modelMapper.map(supplier, LocalSuppliersDto.class);
                    suppliersDto.setPartsCount(supplier.getParts().size());
                    return suppliersDto;
                }).collect(Collectors.toList());

        LocalSuppliersRootDto localSuppliersRootDto = new LocalSuppliersRootDto();
        localSuppliersRootDto.setLocalSuppliersDtos(localSuppliersDtos);

        this.xmlParser.exportToFile(localSuppliersRootDto, FILE_EXPORT_PATH);
    }
}
