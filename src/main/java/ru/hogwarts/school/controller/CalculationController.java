package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculations")
public class CalculationController {

    @GetMapping("/sum")
    public int getSum() {
        int N = 1_000_000;
        return N * (N + 1) / 2;
    }

}
