package ru.platypus.aggregator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.platypus.aggregator.feign.AccountServiceFeignClient;
import ru.platypus.aggregator.feign.CustomerServiceFeignClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final AccountServiceFeignClient accountServiceFeignClient;

    public MainController(CustomerServiceFeignClient customerServiceFeignClient, AccountServiceFeignClient accountServiceFeignClient) {
        this.customerServiceFeignClient = customerServiceFeignClient;
        this.accountServiceFeignClient = accountServiceFeignClient;
    }

    @GetMapping("/")
    public String index(Model model) {
        try {
            int id = Integer.parseInt(customerServiceFeignClient.getCurrentCustomer());
            model.addAttribute("phoneNumber", customerServiceFeignClient.getPhoneNumberByCustomerId(id));
            model.addAttribute("bankAccount", accountServiceFeignClient.getBankAccountById(customerServiceFeignClient.getBankAccountByCustomerId(id)));
        }
        catch (Exception e) {}
        return "index";
    }


    @GetMapping("customers")
    public List<Map<String, Object>> customer() {
        return customerServiceFeignClient.getAllCustomers();
    }

    @GetMapping("customers/{id}")
    public Map<String, Object> getCustomerById(@PathVariable int id) {
        return customerServiceFeignClient.getCustomerById(id);
    }

    @GetMapping("/addMoney")
    public String addMoney(Model model) {
        return "addMoney";
    }

    @PostMapping("/addMoney")
    public String addMoney(@RequestParam("amount") BigDecimal amount, Model model) {
        int customerId = Integer.parseInt(customerServiceFeignClient.getCurrentCustomer());
        int bankAccountId = customerServiceFeignClient.getBankAccountByCustomerId(customerId);
        accountServiceFeignClient.addMoney(bankAccountId, amount);
        return "redirect:/";
    }

}
