package softuni.exam.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskSeedRootDto implements Serializable {

    @XmlElement(name = "task")
    private List<TaskSeedDto> taskSeedDtoList;

    public List<TaskSeedDto> getTaskSeedDtoList() {
        return taskSeedDtoList;
    }

    public void setTaskSeedDtoList(List<TaskSeedDto> taskSeedDtoList) {
        this.taskSeedDtoList = taskSeedDtoList;
    }
}
