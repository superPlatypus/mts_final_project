package ru.mts.customerservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.customerservice.dto.Signin;
import ru.mts.customerservice.entity.Customer;
import ru.mts.customerservice.entity.CustomerDeposit;
import ru.mts.customerservice.entity.CustomerDepositId;
import ru.mts.customerservice.jwt.JWTUtilty;
import ru.mts.customerservice.repostitory.CustomerDepositRepository;
import ru.mts.customerservice.repostitory.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final CustomerDepositRepository customerDepositRepository;
    private final JWTUtilty jwtUtilty;

    public CustomerService(CustomerRepository customerRepository, CustomerDepositRepository customerDepositRepository, JWTUtilty jwtUtilty) {
        this.customerRepository = customerRepository;
        this.customerDepositRepository = customerDepositRepository;
        this.jwtUtilty = jwtUtilty;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByPhoneNumber(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional
    public void deleteCustomerDeposit(int customerId, int depositId) {
        CustomerDepositId customerDepositId = new CustomerDepositId(customerId, depositId);
        customerDepositRepository.deleteById(customerDepositId);
    }
    @Transactional
    public ResponseEntity<String> signin(Signin signin) {
        Optional<Customer> customer = customerRepository.findByPhoneNumber(signin.getPhoneNumber());
        if (customer.isPresent()){
            String jwtToken = jwtUtilty.generateJwtToken(customer.get().getIdCustomer());
            return ResponseEntity.ok(jwtToken);
        }
        else {
            return ResponseEntity.ok("Invalid phone number");
        }
    }


    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    public int getBankAccountByCustomerId(int id) {
        return customerRepository.findById(id).orElse(null).getBank_account_id();
    }

    public String getPhoneNumberByCustomerId(int id) {
        return customerRepository.findById(id).orElse(null).getPhoneNumber();
    }

    public List<Integer> getDepositsByCustomerId(int id) {
        return customerDepositRepository.findByCustomer_IdCustomer(id)
                .stream()
                .map(customerDeposit -> customerDeposit.getId().getDeposit_id())
                .collect(Collectors.toList());
    }
    @Transactional
    public CustomerDeposit addCustomerDeposit(int customerId, int depositId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        CustomerDepositId customerDepositId = new CustomerDepositId(customerId, depositId);
        CustomerDeposit customerDeposit = new CustomerDeposit(customerDepositId, customer);
        return customerDepositRepository.save(customerDeposit);
    }
}
