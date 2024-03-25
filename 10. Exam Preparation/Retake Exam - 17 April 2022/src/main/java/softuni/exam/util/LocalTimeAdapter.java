package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;

@Component
public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {


    @Override
    public LocalTime unmarshal(String time) throws Exception {
        return LocalTime.parse(time);
    }

    @Override
    public String marshal(LocalTime time) throws Exception {
        return time.toString();
    }
}
