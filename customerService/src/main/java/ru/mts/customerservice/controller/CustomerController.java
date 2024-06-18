package ru.mts.customerservice.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mts.customerservice.dto.Signin;
import ru.mts.customerservice.entity.Customer;
import ru.mts.customerservice.entity.CustomerDeposit;
import ru.mts.customerservice.entity.CustomerDepositId;
import ru.mts.customerservice.jwt.JWTUtilty;
import ru.mts.customerservice.repostitory.CustomerDepositRepository;
import ru.mts.customerservice.repostitory.CustomerRepository;
import ru.mts.customerservice.service.CustomerService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final JWTUtilty jwtUtilty;
    private final AuthenticationManager authenticationManager;
    private final CustomerDepositRepository customerDepositRepository;
    private final CustomerService customerService;

    public CustomerController(CustomerRepository customerRepository, JWTUtilty jwtUtilty, AuthenticationManager authenticationManager, CustomerDepositRepository customerDepositRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.jwtUtilty = jwtUtilty;
        this.authenticationManager = authenticationManager;
        this.customerDepositRepository = customerDepositRepository;
        this.customerService = customerService;
    }

    @GetMapping("/currentCustomer")
    public String home(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody Signin signin, HttpServletResponse response) {
        return customerService.signin(signin);


    }

    @GetMapping("customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();

    }

    @GetMapping("customers/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id);

    }

    @GetMapping("customers/{id}/bankAccount")
    public int getBankAccountByCustomerId(@PathVariable int id) {
        return customerService.getBankAccountByCustomerId(id);

    }

    @GetMapping("customers/{id}/phoneNumber")
    public String getPhoneNumberByCustomerId(@PathVariable int id) {
        return customerService.getPhoneNumberByCustomerId(id);

    }

    @GetMapping("customers/{id}/deposits")
    public List<Integer> getDepositsByCustomerId(@PathVariable int id) {
        return customerService.getDepositsByCustomerId(id);

    }

    @PostMapping("/customersDeposit/add")
    public CustomerDeposit  addCustomerDeposit(@RequestParam int customerId, @RequestParam int depositId) {
        return customerService.addCustomerDeposit(customerId, depositId);

    }

    @PostMapping("/customerDeposit/delete")
    public void deleteCustomerDeposit(@RequestParam int customerId, @RequestParam int depositId) {
        customerService.deleteCustomerDeposit(customerId, depositId);
    }


}
