package com.artemsolovev.service;

import com.artemsolovev.model.InvestProperty;

import java.util.List;

public interface InvestPropertyService {

    InvestProperty add(long idRequest, double roa, double roe);
    List<InvestProperty> get(long idUser);
}
