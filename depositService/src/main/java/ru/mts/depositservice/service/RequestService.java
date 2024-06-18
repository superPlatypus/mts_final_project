package ru.mts.depositservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mts.depositservice.entity.Request;
import ru.mts.depositservice.repository.DepositRepository;
import ru.mts.depositservice.repository.RequestRepository;
import ru.mts.depositservice.repository.RequestStatusRepository;

import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final RequestStatusRepository requestStatusRepository;
    private final DepositRepository depositRepository;

    public RequestService(RequestRepository requestRepository, RequestStatusRepository requestStatusRepository, DepositRepository depositRepository) {
        this.requestRepository = requestRepository;
        this.requestStatusRepository = requestStatusRepository;
        this.depositRepository = depositRepository;
    }

    @Transactional
    public int saveRequest(int customerId, int depositId) {
        Request request = new Request(customerId, depositId);
        return requestRepository.save(request).getId();
    }

    @Transactional
    public void updateRequestStatus(int requestId, int statusId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Заявка не найдена"));
        request.setRequestStatusId(statusId);
        requestRepository.save(request);
    }
    @Transactional
    public int getRequestByDepositId(int depositId) {
        Request request =  requestRepository.findByDepositId(depositId).orElseThrow(() -> new RuntimeException("Заявка не найдена"));
        return request.getId();
    }
    @Transactional
    public int getRequestStatus(int requestId){
        return requestRepository.findById(requestId).get().getRequestStatusId();
    }
    @Transactional
    public List<Request> getRejectedRequestByCustomerId(int customerId) {
        return requestRepository.findAllByCustomerIdAndRequestStatusId(customerId, 4);
    }
    @Transactional
    public Request getRequestById(int requestId) {
        return requestRepository.findById(requestId).get();
    }
    @Transactional
    public void deleteRequest(int requestId) {
        int depositId = requestRepository.findById(requestId).get().getDepositId();
        requestRepository.deleteById(requestId);
        depositRepository.deleteById(depositId);
    }
}
