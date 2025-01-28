package com.artemsolovev.controllers;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Card;
import com.artemsolovev.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "card_methods", description = "Контроллер для управления картами.")
@RestController
@RequestMapping("/card")
public class CardController {
    private CardService cardService;

    @Autowired
    public void setCardService(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(
            summary = "Добавление карты",
            description = "Добавление новой карты по ID категории."
    )
    @PostMapping(path = "/{idCategory}")
    public ResponseEntity<ResponseResult<Card>> add(@RequestBody Card card, @PathVariable @Parameter(description = "Идентификатор пользователя", required = true) long idCategory) {
        try {
            this.cardService.add(idCategory, card);
            return new ResponseEntity<>(new ResponseResult<>(null, card), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "idCategory/{idCategory}")
    public ResponseEntity<ResponseResult<List<Card>>> getByIdCategory(@PathVariable long idCategory) {
        List<Card> list = this.cardService.getByIdCategory(idCategory);
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping(path = "idUser/{idUser}")
    public ResponseEntity<ResponseResult<List<Card>>> getByIdUser(@PathVariable long idUser) {
        List<Card> list = this.cardService.getByIdUser(idUser);
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Card>> get(@PathVariable long id) {
        try {
            Card card = this.cardService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, card), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Card>> delete(@PathVariable long id) {
        try {
            Card card = this.cardService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, card), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<ResponseResult<Card>> update(@RequestBody Card card) {
        try {
            if (card.getId() <= 0) {
                return new ResponseEntity<>(new ResponseResult<>("Incorrect format id", null),
                        HttpStatus.BAD_REQUEST);
            }
            Card cardNew = this.cardService.update(card);
            return new ResponseEntity<>(new ResponseResult<>(null, cardNew), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
