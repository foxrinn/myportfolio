package com.artemsolovev.service;

import com.artemsolovev.model.InvestProperty;
import com.artemsolovev.model.Request;
import com.artemsolovev.model.Solution;
import com.artemsolovev.repository.InvestPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestPropertyServiceImpl implements InvestPropertyService {

    private InvestPropertyRepository investPropertyRepository;
    private RequestService requestService;

    @Autowired
    public void setInvestPropertyRepository(InvestPropertyRepository investPropertyRepository) {
        this.investPropertyRepository = investPropertyRepository;
    }

    @Autowired
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }


    /**
     *
     Годовой ROI = Ожидаемая прибыль за год / Сумма инвестирования * 100%
     Полный ROI = Годовой ROI + ROA / Срок сделки в годах
     ROA = Ожидаемая стоимость актива к концу сделки / Сумма инвестирования * 100%
     Итоговая прибыль = Сумма инвестирования * Полный ROI * Срок сделки в годах
     Дата открытия сделки = момент создания актива
     Дата закрытия сделки = дата открытия сделки + Срок сделки в годах
     */

    @Override
    public InvestProperty add(long idRequest, double annualIncome, double expectedFinalPrice) {
        try {
            Request request = this.requestService.getById(idRequest);
            double roa = expectedFinalPrice / request.getInvestPrice() * 100;
            double annualROI = annualIncome / request.getInvestPrice() * 100;
            double fullROI = (annualROI + roa) / request.getYears();
            double totalIncome = request.getInvestPrice() * fullROI * request.getYears();
            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime endDate = startDate.plusYears(request.getYears());
            InvestProperty investProperty = new InvestProperty(roa, annualROI, fullROI, totalIncome, startDate, endDate, request);
            this.investPropertyRepository.save(investProperty);
            return investProperty;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Property has already added!");
        }
    }

    @Override
    public List<InvestProperty> get(long idUser) {
        List<Request> requests = this.requestService.findAllByUserId(idUser);
        return requests.stream().filter(x -> x.getSolution().equals(Solution.APPROVED)).map(Request::getInvestProperty).collect(Collectors.toList());
    }

}
