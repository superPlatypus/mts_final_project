package ru.mts.depositservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.mts.depositservice.entity.DepositTypes;
import ru.mts.depositservice.repository.DepositTypesRepository;

import java.util.List;

@RestController
public class DepositTypesController {

    private final DepositTypesRepository depositTypesRepository;

    public DepositTypesController(DepositTypesRepository depositTypesRepository) {
        this.depositTypesRepository = depositTypesRepository;
    }

    @GetMapping("depositTypes")
    public List<DepositTypes> getDepositTypes() {
        return depositTypesRepository.findAll();
    }

    @GetMapping("depositTypes/{id}")
    public DepositTypes getDepositType(@PathVariable int id) {
        return depositTypesRepository.findById(id).orElse(null);
    }
}
