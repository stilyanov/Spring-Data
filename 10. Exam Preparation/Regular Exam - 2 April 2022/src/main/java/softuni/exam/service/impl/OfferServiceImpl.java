package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.Offer.OfferSeedDto;
import softuni.exam.models.dto.Offer.OfferSeedRootDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
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
public class OfferServiceImpl implements OfferService {

    private static final String OFFER_FILE_PATH = "src/main/resources/files/xml/offers.xml";

    private final OfferRepository offerRepository;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final StringBuilder sb;

    public OfferServiceImpl(OfferRepository offerRepository, AgentRepository agentRepository, ApartmentRepository apartmentRepository, ModelMapper modelMapper, ValidationUtil validationUtil, StringBuilder sb) {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.sb = sb;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFER_FILE_PATH));
    }

    @Override
    public String importOffers() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(OfferSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        OfferSeedRootDto offerSeedRootDto = (OfferSeedRootDto) unmarshaller.unmarshal(new File(OFFER_FILE_PATH));

        for (OfferSeedDto offerSeedDto : offerSeedRootDto.getOfferSeedDtoList()) {
            Optional<Agent> optionalAgent = this.agentRepository.findByFirstName(offerSeedDto.getAgent().getName());

            if (!this.validationUtil.isValid(offerSeedDto) || optionalAgent.isEmpty()) {
                sb.append("Invalid offer\n");
                continue;
            }

            Optional<Apartment> optionalApartment = this.apartmentRepository.findById(offerSeedDto.getApartment().getId());

            Offer offer = this.modelMapper.map(offerSeedDto, Offer.class);
            offer.setAgent(optionalAgent.get());
            offer.setApartment(optionalApartment.get());

            this.offerRepository.saveAndFlush(offer);

            sb.append(String.format("Successfully imported offer %.2f\n", offer.getPrice()));

        }


        return sb.toString();
    }

    @Override
    public String exportOffers() {
        List<Offer> result = this.offerRepository
                .findAllByApartment_ApartmentTypeOrderByApartment_AreaDescPriceAsc(ApartmentType.three_rooms);

        result.forEach(o -> sb.append(String.format("""
                                        Agent %s %s with offer №%d:
                                        -Apartment area: %.2f
                                        --Town: %s
                                        ---Price: %.2f$""",
                                o.getAgent().getFirstName(), o.getAgent().getLastName(), o.getId(),
                                o.getApartment().getArea(),
                                o.getAgent().getTown().getTownName(),
                                o.getPrice()))
                        .append(System.lineSeparator()));


        return sb.toString().trim();
    }
}
