package com.artemsolovev.service;

import com.artemsolovev.model.Card;

import java.util.List;

public interface CardService {
    void add(long idCategory, Card card);
    List<Card> getByIdCategory(long idCategory);
    List<Card> getByIdUser(long idUser);

    Card get(long id);
    Card update(Card card);
    Card delete(long id);
}
