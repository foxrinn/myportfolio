package com.artemsolovev.controllers;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.InvestProperty;
import com.artemsolovev.model.Request;
import com.artemsolovev.service.InvestPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/investProperty")
public class InvestPropertyController {
    private InvestPropertyService investPropertyService;
    @Autowired
    public void setInvestPropertyService(InvestPropertyService investPropertyService) {
        this.investPropertyService = investPropertyService;
    }

    /**
     *
     * @param idRequest
     * @param annualIncome Ожидаемая прибыль за год
     * @param expectedFinalPrice Ожидаемая стоимость актива к концу сделки
     * @return
     */
    @PostMapping()
    public ResponseEntity<ResponseResult<InvestProperty>> add(@RequestParam long idRequest,
                                                              @RequestParam double annualIncome, @RequestParam double expectedFinalPrice) {
        try {
            InvestProperty investProperty = this.investPropertyService.add(idRequest, annualIncome, expectedFinalPrice);
            return new ResponseEntity<>(new ResponseResult<>(null, investProperty), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{idUser}")
    public ResponseEntity<ResponseResult<List<InvestProperty>>> getByIdUser(@PathVariable long idUser) {
        List<InvestProperty> list = this.investPropertyService.get(idUser);
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

}
