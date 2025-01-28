package com.artemsolovev.service;

import com.artemsolovev.model.Card;
import com.artemsolovev.model.Category;
import com.artemsolovev.model.User;
import com.artemsolovev.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService{

    private CardRepository cardRepository;

    private CategoryService categoryService;
    private UserService userService;


    @Autowired
    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void add(long idCategory, Card card) {
        try {
            card.setCategory(this.categoryService.get(idCategory));
            this.cardRepository.save(card);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Card has already added!");
        }
    }

    @Override
    public List<Card> getByIdCategory(long idCategory) {
        return this.cardRepository.findByCategory_Id(idCategory);
    }

    @Override
    public List<Card> getByIdUser(long idUser) {
        List<Card> cards = new ArrayList<>();
        User user = this.userService.get(idUser);
        List<Category> categories = user.getCategories();
        for (Category category : categories) {
            cards.addAll(category.getCards());
        }
        return cards;
    }

    @Override
    public Card get(long id) {
        return this.cardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Card does not found!"));
    }

    @Override
    public Card update(Card card) {
        try {
            Card old = this.get(card.getId());
            old.setQuestion(card.getQuestion());
            old.setAnswer(card.getAnswer());
            this.cardRepository.save(old);
            return old;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Card has already added!");
        }
    }

    @Override
    public Card delete(long id) {
        Card card = this.get(id);
        this.cardRepository.deleteById(id);
        return card;
    }
}
