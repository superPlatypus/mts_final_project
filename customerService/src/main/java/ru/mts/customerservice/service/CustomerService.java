package ru.mts.customerservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.customerservice.entity.CustomerDepositId;
import ru.mts.customerservice.repostitory.CustomerDepositRepository;
import ru.mts.customerservice.repostitory.CustomerRepository;

@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final CustomerDepositRepository customerDepositRepository;
//    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, CustomerDepositRepository customerDepositRepository) {
        this.customerRepository = customerRepository;
        this.customerDepositRepository = customerDepositRepository;
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

}
