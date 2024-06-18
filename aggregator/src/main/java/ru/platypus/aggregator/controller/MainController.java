package ru.platypus.aggregator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.platypus.aggregator.dto.NewDeposit;
import ru.platypus.aggregator.feign.AccountServiceFeignClient;
import ru.platypus.aggregator.feign.CustomerServiceFeignClient;
import ru.platypus.aggregator.feign.DepositServiceFeignClient;
import ru.platypus.aggregator.feign.SmsServiceFeignClient;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final AccountServiceFeignClient accountServiceFeignClient;
    private final DepositServiceFeignClient depositServiceFeignClient;
    private final SmsServiceFeignClient smsServiceFeignClient;

    public MainController(CustomerServiceFeignClient customerServiceFeignClient, AccountServiceFeignClient accountServiceFeignClient, DepositServiceFeignClient depositServiceFeignClient, SmsServiceFeignClient smsServiceFeignClient, SmsServiceFeignClient smsServiceFeignClient1) {
        this.customerServiceFeignClient = customerServiceFeignClient;
        this.accountServiceFeignClient = accountServiceFeignClient;
        this.depositServiceFeignClient = depositServiceFeignClient;
        this.smsServiceFeignClient = smsServiceFeignClient1;
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

    @GetMapping("requests/{id}")
    public String requests(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        Map<String,Object> request = depositServiceFeignClient.getRequest(id);
        int requestId = Integer.parseInt( request.get("id").toString());
        int customerId = (int)request.get("customerId");
        int depositId = (int) request.get("depositId");
        model.addAttribute("date", depositServiceFeignClient.getRequest(id).get("requestDate"));
        model.addAttribute("amount", depositServiceFeignClient.getDeposit(depositId).get("depositAmount"));
        model.addAttribute("account", accountServiceFeignClient.getBankAccountById((int)depositServiceFeignClient.getDeposit(depositId).get("depositAccountId")).get("numBankAccount"));
        return "request";
    }

    @PostMapping("request/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        Map<String,Object> request = depositServiceFeignClient.getRequest(id);
        int depositId = (int) request.get("depositId");
        customerServiceFeignClient.deleteCustomerDeposit(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer()), depositId);
        depositServiceFeignClient.deleteRequest(id);
        return "redirect:/deposits";
    }

    @GetMapping("/deposits")
    public String deposits(Model model) {
        List<Integer> idDeposits = customerServiceFeignClient.getDepositsByCustomerId(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer()));
        List<Map<String, Object>> deposits = idDeposits.stream()
                .map(depositServiceFeignClient::getDeposit)
                .collect(Collectors.toList())
                .stream()
                .filter(deposit -> depositServiceFeignClient.getRequestStatus(depositServiceFeignClient.getRequestByDepositId((int)deposit.get("id")).getBody()).getBody() == 3)
                .sorted(Comparator.comparing(deposit -> (double) deposit.get("depositAmount"), Comparator.reverseOrder()))
                .collect(Collectors.toList());

        model.addAttribute("deposits", deposits);
        model.addAttribute("account", accountServiceFeignClient.getBankAccountById(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer())));
        List<Map<String, Object>> rejectedRequests = depositServiceFeignClient.getRejectedRequests(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer()));
        model.addAttribute("rejectedRequests", rejectedRequests);
        return "deposits";
    }

    @GetMapping("/deposits/{id}")
    public String deposit(@PathVariable("id") int id, Model model){
        Map<String,Object> deposit = depositServiceFeignClient.getDeposit(id);
        model.addAttribute("deposit", depositServiceFeignClient.getDeposit(id));
        model.addAttribute("account", accountServiceFeignClient.getBankAccountById((int) deposit.get("depositAccountId")).get("numBankAccount"));
        LocalDate startDate =  LocalDate.parse((String) deposit.get("startDate"),  DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate =  LocalDate.parse((String) deposit.get("endDate"),  DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Period period = Period.between(startDate, endDate);
        int month = period.getYears()*12 + period.getMonths();
        model.addAttribute("month", month);
        model.addAttribute("amount", deposit.get("depositAmount"));
        model.addAttribute("rate", deposit.get("depositRate"));
        model.addAttribute("capitalization", deposit.get("capitalization"));
        if ((boolean) model.getAttribute("capitalization") ==  false) {
            model.addAttribute("typePercentPayment", depositServiceFeignClient.getTypesPercentPaymentById((int)deposit.get("typePercentPaymentId")).get("period"));
            model.addAttribute("percentPaymentDate", deposit.get("percentPaymentDate"));
            model.addAttribute("percentPaymentAccountId", accountServiceFeignClient.getBankAccountById((int)deposit.get("percentPaymentAccountId")).get("numBankAccount"));
        }


        model.addAttribute("startDate", deposit.get("startDate"));
        model.addAttribute("endDate", deposit.get("endDate"));

        model.addAttribute("depositRefundAccount", accountServiceFeignClient.getBankAccountById((int) deposit.get("depositRefundAccountId")).get("numBankAccount"));
        model.addAttribute("depositType", depositServiceFeignClient.getDepositType((int)deposit.get("depositTypeId")).get("name"));
        model.addAttribute("depositTypeId", depositServiceFeignClient.getDepositType((int)deposit.get("depositTypeId")).get("id"));


        return "deposit";
    }

    @GetMapping("/deposits/{id}/addMoney")
    public String depositAddMoney(@PathVariable("id") int id, Model model){
        model.addAttribute("id", id);
        return "depositAddMoney";
    }

    @PostMapping("/deposits/{id}/addMoney")
    public String depositAddMoney(@PathVariable("id") int id,
                                  @RequestParam("amount") BigDecimal amount,
                                  Model model,
                                  RedirectAttributes redirectAttributes){
        String phoneNumber = customerServiceFeignClient.getPhoneNumberByCustomerId(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer()));
        smsServiceFeignClient.send(phoneNumber);
        redirectAttributes.addAttribute("amount", amount);
        redirectAttributes.addAttribute("phoneNumber", phoneNumber);
        return "redirect:/deposits/" + id + "/addMoney/verify";
    }

    @GetMapping("/deposits/{id}/addMoney/verify")
    public String depositAddMoneyVerify(@PathVariable("id") int id,
                                        @RequestParam("amount") BigDecimal amount,
                                        @RequestParam("phoneNumber") String phoneNumber,
                                        Model model){
        model.addAttribute("amount", amount);
        model.addAttribute("phoneNumber", phoneNumber);
        return "depositAddMoneyVerify";
    }

    @PostMapping("/deposits/{id}/addMoney/verify")
    public String depositAddMoneyVerify(@PathVariable("id") int id,
                                        @RequestParam("amount") BigDecimal amount,
                                        @RequestParam("phoneNumber") String phoneNumber,
                                        @RequestParam("code") String code,
                                        RedirectAttributes redirectAttributes,
                                        Model model){
        ResponseEntity<String> isValid = smsServiceFeignClient.verify(phoneNumber, code);
        if (isValid.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("amount", amount);
            model.addAttribute("date", LocalDate.now());
            if (accountServiceFeignClient.isAllow(accountServiceFeignClient.getCurrentBankAccount(), amount).getBody()){
                model.addAttribute("ok", true);
                accountServiceFeignClient.minusMoney(accountServiceFeignClient.getCurrentBankAccount(), amount);
                depositServiceFeignClient.addMoney(id, amount);
            }
            else{
                model.addAttribute("ok", false);
            }
            return "depositAddMoneySummary";
        }
        else{
            return "redirect:/deposits/" + id + "/addMoney/verify";
        }
    }

    @GetMapping("/deposits/{id}/close")
    public String depositClose(@PathVariable("id") int id, Model model){
        String phoneNumber = customerServiceFeignClient.getPhoneNumberByCustomerId(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer()));
        model.addAttribute("id", id);
        model.addAttribute("phoneNumber", phoneNumber);
        smsServiceFeignClient.send(phoneNumber);
        return "depositClose";
    }

    @PostMapping("deposits/{id}/close")
    public String depositClosePost(@PathVariable("id") int id,
                                   @RequestParam("code") String code,
                                   @RequestParam("phoneNumber") String phoneNumber,
                                   Model model,
                                   RedirectAttributes redirectAttributes){
//        String phoneNumber = (String) model.getAttribute("phoneNumber");
        ResponseEntity<String> isValid = smsServiceFeignClient.verify(phoneNumber, code);
        if (isValid.getStatusCode() == HttpStatus.OK) {
            accountServiceFeignClient.addMoney(accountServiceFeignClient.getCurrentBankAccount(), BigDecimal.valueOf((double) depositServiceFeignClient.getDeposit(id).get("depositAmount")));
            customerServiceFeignClient.deleteCustomerDeposit(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer()), id);
            depositServiceFeignClient.deleteDeposit(id);
            model.addAttribute("id", id);
            model.addAttribute("date", LocalDate.now());
            return "depositDeleteSummary";
        }
        else{
            return "redirect:/deposits/" + id + "/close";
        }
    }

    @GetMapping("/newDeposit")
    public String newDeposit(Model model) {
        Object bankAccount = accountServiceFeignClient.getBankAccountById(customerServiceFeignClient.getBankAccountByCustomerId(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer()))).get("numBankAccount");
        model.addAttribute("depositTypes", depositServiceFeignClient.getDepositTypes());
        model.addAttribute("typesPercentPayment", depositServiceFeignClient.getTypesPercentPayment());
        model.addAttribute("account", bankAccount);

        model.addAttribute("depositTypeId", 0);
        model.addAttribute("depositAmount", 0);
        model.addAttribute("typePercentPaymentId", 0);
        model.addAttribute("month", 0);
        model.addAttribute("capitalization", false);

        model.addAttribute("newDeposit", new NewDeposit());


        return "newDeposit";
    }

    @PostMapping("/newDeposit")
    public String newDeposit(Model model,
                             @ModelAttribute("newDeposit") NewDeposit newDeposit) {
        String phoneNumber = customerServiceFeignClient.getPhoneNumberByCustomerId(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer()));
        smsServiceFeignClient.send(phoneNumber);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("newDeposit", newDeposit);
        System.out.println(newDeposit);
        int savedDepositId;
        if (newDeposit.isCapitalization()) {
            savedDepositId = depositServiceFeignClient.addDepositWithCapitalization(
                    customerServiceFeignClient.getBankAccountByCustomerId(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer())),
                    newDeposit.getDepositTypeId(),
                    newDeposit.getDepositAmount(),
                    newDeposit.getMonth()).getBody();
        } else {
            savedDepositId = depositServiceFeignClient.addDepositWithPercents(
                    customerServiceFeignClient.getBankAccountByCustomerId(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer())),
                    newDeposit.getDepositTypeId(),
                    newDeposit.getDepositAmount(),
                    newDeposit.getTypePercentPaymentId(),
                    newDeposit.getMonth()).getBody();
        }
        customerServiceFeignClient.addCustomerDeposit(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer()), savedDepositId);
        depositServiceFeignClient.newRequest(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer()), savedDepositId);
//        depositServiceFeignClient.updateStatus(depositServiceFeignClient.getRequestByDepositId(savedDepositId).getBody(), 1);
        model.addAttribute("savedDepositId", savedDepositId);
        return "depositVerify";
    }

    @PostMapping("depositVerify")
    public String depositVerify(Model model,
                                @RequestParam("code") String code,
                                @ModelAttribute("newDeposit") NewDeposit newDeposit,
                                @ModelAttribute("savedDepositId") int savedDepositId,
                                RedirectAttributes redirectAttributes) {
        ResponseEntity<String> isValid = smsServiceFeignClient.verify(newDeposit.getPhoneNumber(), code);
        int id = (int) model.asMap().get("savedDepositId");

        if (isValid.getStatusCode() == HttpStatus.OK) {
            depositServiceFeignClient.updateStatus(depositServiceFeignClient.getRequestByDepositId(savedDepositId).getBody(), 2);
            customerServiceFeignClient.addCustomerDeposit(Integer.parseInt(customerServiceFeignClient.getCurrentCustomer()), savedDepositId);
            if (accountServiceFeignClient.isAllow(accountServiceFeignClient.getCurrentBankAccount(), newDeposit.getDepositAmount()).getBody()){
                depositServiceFeignClient.updateStatus(depositServiceFeignClient.getRequestByDepositId(savedDepositId).getBody(), 3);
                accountServiceFeignClient.minusMoney(accountServiceFeignClient.getCurrentBankAccount(), newDeposit.getDepositAmount());
                redirectAttributes.addAttribute("depositType", depositServiceFeignClient.getDepositType(newDeposit.getDepositTypeId()).get("name"));
                redirectAttributes.addAttribute("amount", newDeposit.getDepositAmount());
                redirectAttributes.addAttribute("date", LocalDate.now());
                redirectAttributes.addAttribute("rate", 16.2);
                redirectAttributes.addAttribute("ok", true);
                redirectAttributes.addAttribute("reason", "");
                return "redirect:/depositRequest";

            }
            else {
                depositServiceFeignClient.updateStatus(depositServiceFeignClient.getRequestByDepositId(savedDepositId).getBody(), 4);
                redirectAttributes.addAttribute("depositType", depositServiceFeignClient.getDepositType(newDeposit.getDepositTypeId()).get("name"));
                redirectAttributes.addAttribute("amount", newDeposit.getDepositAmount());
                redirectAttributes.addAttribute("date", LocalDate.now());
                redirectAttributes.addAttribute("rate", 16.2);
                redirectAttributes.addAttribute("reason", "Недостаточно средств");
                redirectAttributes.addAttribute("ok", false);
                return "redirect:/depositRequest";
            }
        }
        return "redirect:/depositVerify";
    }

    @GetMapping("/depositRequest")
    public String depositRequest(Model model,
                                 @RequestParam("depositType") String depositType,
                                 @RequestParam("amount") BigDecimal amount,
                                 @RequestParam("date") LocalDate date,
                                 @RequestParam("rate") double rate,
                                 @RequestParam("reason") String reason,
                                 @RequestParam("ok") boolean ok){
        model.addAttribute("depositType", depositType);
        model.addAttribute("amount", amount);
        model.addAttribute("date", date);
        model.addAttribute("rate", rate);
        model.addAttribute("reason", reason);
        model.addAttribute("ok", ok);
        return "depositRequest";
    }


}
