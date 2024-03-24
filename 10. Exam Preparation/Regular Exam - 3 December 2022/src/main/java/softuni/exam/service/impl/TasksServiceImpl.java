package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.TaskSeedDto;
import softuni.exam.models.dto.xml.TaskSeedRootDto;
import softuni.exam.models.entity.*;
import softuni.exam.repository.CarsRepository;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
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
public class TasksServiceImpl implements TasksService {
    private static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";

    private final TasksRepository tasksRepository;
    private final MechanicsRepository mechanicsRepository;
    private final PartsRepository partsRepository;
    private final CarsRepository carsRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public TasksServiceImpl(TasksRepository tasksRepository, MechanicsRepository mechanicsRepository, PartsRepository partsRepository, CarsRepository carsRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.tasksRepository = tasksRepository;
        this.mechanicsRepository = mechanicsRepository;
        this.partsRepository = partsRepository;
        this.carsRepository = carsRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.tasksRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(TASKS_FILE_PATH)));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        JAXBContext jaxbContext = JAXBContext.newInstance(TaskSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        TaskSeedRootDto taskSeedRootDto = (TaskSeedRootDto) unmarshaller.unmarshal(new File(TASKS_FILE_PATH));

        for (TaskSeedDto taskSeedDto : taskSeedRootDto.getTaskSeedDtoList()) {
            Optional<Mechanic> mechanic = this.mechanicsRepository.findByFirstName(taskSeedDto.getMechanic().getFirstName());
            Optional<Part> part = this.partsRepository.findById(taskSeedDto.getPart().getId());
            Optional<Car> car = this.carsRepository.findById(taskSeedDto.getCar().getId());

            if (!this.validationUtil.isValid(taskSeedDto) || mechanic.isEmpty() || part.isEmpty() || car.isEmpty()) {
                sb.append("Invalid task\n");
                continue;
            }


            Task task = this.modelMapper.map(taskSeedDto, Task.class);
            task.setCar(car.get());
            task.setMechanic(mechanic.get());
            task.setPart(part.get());

            this.tasksRepository.saveAndFlush(task);

            sb.append(String.format("Successfully imported task %.2f\n", taskSeedDto.getPrice()));

        }


        return sb.toString();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        StringBuilder sb = new StringBuilder();

        List<Task> result = this.tasksRepository.findAllByCarCarTypeOrderByPriceDesc(CarType.coupe);

        result.forEach(task -> sb.append(String.format("Car %s %s with %dkm\n" +
                                "-Mechanic: %s %s - task №%d:\n" +
                                " --Engine: %s\n" +
                                "---Price: %s$",
                        task.getCar().getCarMake(), task.getCar().getCarModel(), task.getCar().getKilometers(),
                        task.getMechanic().getFirstName(), task.getMechanic().getLastName(), task.getId(),
                        task.getCar().getEngine(),
                        task.getPrice()))
                .append(System.lineSeparator()));

        return sb.toString().trim();
    }
}
