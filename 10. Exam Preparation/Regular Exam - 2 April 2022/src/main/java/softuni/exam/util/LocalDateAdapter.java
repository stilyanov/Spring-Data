package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate.format(dateFormat);
    }

    @Override
    public LocalDate unmarshal(String localDate) throws Exception {
        return LocalDate.parse(localDate, dateFormat);
    }

}
