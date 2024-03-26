package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.Agent.AgentSeedDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {


    private static final String AGENT_FILE_PATH = "src/main/resources/files/json/agents.json";
    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final StringBuilder stringBuilder;
    private final Gson gson;

    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, StringBuilder stringBuilder, Gson gson) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.stringBuilder = stringBuilder;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENT_FILE_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        AgentSeedDto[] agentSeedDtos = this.gson.fromJson(readAgentsFromFile(), AgentSeedDto[].class);
        for (AgentSeedDto agentDto : agentSeedDtos) {

            Optional<Town> town = this.townRepository.findByTownName(agentDto.getTown());
            Optional<Agent> firstName = this.agentRepository.findByFirstName(agentDto.getFirstName());
            Optional<Agent> email = this.agentRepository.findByEmail(agentDto.getEmail());

            if (!this.validationUtil.isValid(agentDto) || firstName.isPresent()
                    || email.isPresent() || town.isEmpty()) {
                stringBuilder.append("Invalid agent\n");
                continue;
            }

            Agent agent = this.modelMapper.map(agentDto, Agent.class);
            agent.setTown(town.get());

            this.agentRepository.saveAndFlush(agent);

            stringBuilder.append(String.format("Successfully imported agent - %s %s\n",
                    agent.getFirstName(), agent.getLastName()));
        }

        return stringBuilder.toString();
    }
}
