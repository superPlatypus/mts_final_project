package ru.mts.customerservice.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mts.customerservice.dto.Signin;
import ru.mts.customerservice.entity.Customer;
import ru.mts.customerservice.jwt.JWTUtilty;
import ru.mts.customerservice.repostitory.CustomerRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final JWTUtilty jwtUtilty;
    private final AuthenticationManager authenticationManager;

    public CustomerController(CustomerRepository customerRepository, JWTUtilty jwtUtilty, AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.jwtUtilty = jwtUtilty;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/currentCustomer")
    public String home(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody Signin signin, HttpServletResponse response) {
        Optional<Customer> customer = customerRepository.findByPhoneNumber(signin.getPhoneNumber());
        if (customer.isPresent()){
            String jwtToken = jwtUtilty.generateJwtToken(customer.get().getId_customer());
            return ResponseEntity.ok(jwtToken);
        }
        else {
            return ResponseEntity.ok("Invalid phone number");
        }

    }

    @GetMapping("customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("customers/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        return customerRepository.findById(id).orElse(null);
    }

    @GetMapping("customers/{id}/bankAccount")
    public int getBankAccountByCustomerId(@PathVariable int id) {
        return customerRepository.findById(id).orElse(null).getBank_account_id();
    }

    @GetMapping("customers/{id}/phoneNumber")
    public String getPhoneNumberByCustomerId(@PathVariable int id) {
        return customerRepository.findById(id).orElse(null).getPhoneNumber();
    }


}
