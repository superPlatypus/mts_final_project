package ru.mts.customerservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mts.customerservice.repostitory.CustomerRepository;

@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;
//    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByPhoneNumber(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
