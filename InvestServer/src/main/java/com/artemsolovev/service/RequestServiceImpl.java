package com.artemsolovev.service;

import com.artemsolovev.model.Request;
import com.artemsolovev.model.Solution;
import com.artemsolovev.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;
    private UserService userService;

    @Autowired
    public void setRequestRepository(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void add(long idUser, Request request) {
        try {
            request.setUser(this.userService.get(idUser));
            this.requestRepository.save(request);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Request has already added!");
        }
    }

    @Override
    public List<Request> get() {
        return this.requestRepository.findRequestBySolution(Solution.UNWATCHED);
    }

    @Override
    public List<Request> get(long idUser) {
        return this.requestRepository.findRequestBySolutionAndUser_id(Solution.UNWATCHED, idUser);
    }

    @Override
    public List<Request> findAllByUserId(long idUser) {
        return this.requestRepository.findAllByUser_Id(idUser);
    }

    @Override
    public Request getById(long id) {
        return this.requestRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Nothing found"));
    }

    @Override
    public Request makeDecision(long id, boolean decision, String comment) {
        Request request = this.getById(id);
        request.setComment(comment);
        if (decision) {
            request.setSolution(Solution.APPROVED);
        } else {
            request.setSolution(Solution.DECLINED);
        }
        this.requestRepository.save(request);
        return request;
    }
}
