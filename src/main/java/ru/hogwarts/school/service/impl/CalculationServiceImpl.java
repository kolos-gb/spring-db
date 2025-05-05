package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.CalculationService;

@Service
public class CalculationServiceImpl implements CalculationService {

    @Override
    public int calculateSum() {
        int N = 1_000_000;
        return N * (N + 1) / 2;
    }
}
