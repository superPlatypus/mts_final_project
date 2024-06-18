package ru.mts.depositservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mts.depositservice.entity.Request;

import ru.mts.depositservice.repository.RequestRepository;
import ru.mts.depositservice.repository.RequestStatusRepository;
import ru.mts.depositservice.service.RequestService;

import java.util.List;

@RestController
public class RequestsController {
    private final RequestService requestService;

    public RequestsController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/newRequest")
    public ResponseEntity<Integer> newRequest(@RequestParam("customerId") int customerId, @RequestParam("depositId") int depositId) {
        return ResponseEntity.ok(requestService.saveRequest(customerId, depositId));
    }

    @PostMapping("request/{id}/updateStatus")
    public ResponseEntity<String> updateStatus(@PathVariable("id") int requestId, @RequestParam("statusId") int statusId) {
        requestService.updateRequestStatus(requestId, statusId);
        return ResponseEntity.ok("Статус заявки изменен");
    }

    @GetMapping("requestByDepositId")
    public ResponseEntity<Integer> getRequestByDepositId(@RequestParam("depositId") int depositId) {
        int requestId = requestService.getRequestByDepositId(depositId);
        return ResponseEntity.ok(requestService.getRequestByDepositId(depositId));
    }

    @GetMapping("requestStatus")
    public ResponseEntity<Integer> getRequestStatus(@RequestParam("requestId") int requestId) {
        return ResponseEntity.ok(requestService.getRequestStatus(requestId));
    }

    @GetMapping("rejectedRequestsByCustomerId")
    public List<Request> getRejectedRequests(@RequestParam("customerId") int customerId) {
        return requestService.getRejectedRequestByCustomerId(customerId);
    }

    @GetMapping("/request{id}")
    public Request getRequest(@PathVariable("id") int requestId) {
        return requestService.getRequestById(requestId);
    }

    @PostMapping("/request{id}/delete")
    public void deleteRequest(@PathVariable("id") int requestId) {
        requestService.deleteRequest(requestId);
    }
}
