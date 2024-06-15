package ru.platypus.aggregator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.platypus.aggregator.feign.AccountServiceFeignClient;
import ru.platypus.aggregator.feign.CustomerServiceFeignClient;
import ru.platypus.aggregator.feign.DepositServiceFeignClient;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final AccountServiceFeignClient accountServiceFeignClient;
    private final DepositServiceFeignClient depositServiceFeignClient;

    public MainController(CustomerServiceFeignClient customerServiceFeignClient, AccountServiceFeignClient accountServiceFeignClient, DepositServiceFeignClient depositServiceFeignClient) {
        this.customerServiceFeignClient = customerServiceFeignClient;
        this.accountServiceFeignClient = accountServiceFeignClient;
        this.depositServiceFeignClient = depositServiceFeignClient;
    }

    @GetMapping("/")
    public String index(Model model) {
        try {
            int id = Integer.parseInt(customerServiceFeignClient.getCurrentCustomer());
            model.addAttribute("phoneNumber", customerServiceFeignClient.getPhoneNumberByCustomerId(id));
            model.addAttribute("bankAccount", accountServiceFeignClient.getBankAccountById(customerServiceFeignClient.getBankAccountByCustomerId(id)));
        } catch (Exception e) {
        }
        return "index";
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


    @GetMapping("/deposits")
    public String deposits(Model model) {
        List<Integer> idDeposits = customerServiceFeignClient.getDepositsByCustomerId(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer()));
        List<Map<String, Object>> deposits = idDeposits.stream()
                .map(depositServiceFeignClient::getDeposit)
                .collect(Collectors.toList());
        model.addAttribute("deposits", deposits);
        model.addAttribute("account", accountServiceFeignClient.getBankAccountById(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer())));
        return "deposits";
    }

    @GetMapping("/newDeposit")
    public String newDeposit(Model model) {
        model.addAttribute("depositTypes", depositServiceFeignClient.getDepositTypes());
        model.addAttribute("typesPercentPayment", depositServiceFeignClient.getTypesPercentPayment());
        model.addAttribute("account", customerServiceFeignClient.getBankAccountByCustomerId(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer())));
        return "newDeposit";
    }

    @PostMapping("/newDeposit")
    public String newDeposit(@RequestParam("amount") BigDecimal amount, Model model) {}


}
