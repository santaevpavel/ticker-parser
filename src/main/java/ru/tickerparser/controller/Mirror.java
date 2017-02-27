package ru.tickerparser.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Mirror {

    @RequestMapping("/mirror")
    public String mirror(@RequestParam(value = "value", required = false, defaultValue = "Empty") String value) {
        return "Ticker Parser Hello World!  " + value;


    }
}
