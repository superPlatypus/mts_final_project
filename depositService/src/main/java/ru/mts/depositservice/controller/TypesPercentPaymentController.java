package ru.mts.depositservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.mts.depositservice.entity.TypesPercentPayment;
import ru.mts.depositservice.repository.TypesPercentPaymentRepository;

import java.util.List;

@RestController
public class TypesPercentPaymentController {
    private final TypesPercentPaymentRepository typesPercentPaymentRepository;

    public TypesPercentPaymentController(TypesPercentPaymentRepository typesPercentPaymentRepository) {
        this.typesPercentPaymentRepository = typesPercentPaymentRepository;
    }

    @GetMapping("typesPercentPayment")
    public List<TypesPercentPayment> getTypesPercentPayment() {
        return typesPercentPaymentRepository.findAll();
    }

    @GetMapping("typesPercentPayment/{id}")
    public TypesPercentPayment getTypesPercentPaymentById(@PathVariable int id) {
        return typesPercentPaymentRepository.findById(id).orElse(null);
    }
}
