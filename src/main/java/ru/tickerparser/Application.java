package ru.tickerparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.tickerparser.util.TessdataUtil;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        TessdataUtil.extractTessdata();
        SpringApplication.run(Application.class, args);
    }

}