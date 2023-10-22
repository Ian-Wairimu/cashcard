package com.wairimuian.CashCard.controller;

import com.wairimuian.CashCard.entity.CashCard;
import com.wairimuian.CashCard.repository.CashCardRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    private final Log log = LogFactory.getLog(getClass());
    private CashCardRepository cashCardRepository;
    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }
    @PostMapping
    public ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }
    @GetMapping("/{requestedId}")
    public ResponseEntity<CashCard> findById(@PathVariable("requestedId") Long id) {
        Optional<CashCard> cashCardOptional = cashCardRepository.findById(id);
        if(cashCardOptional.isPresent()) {
            log.info("getting a request with an id and passing it through the response entity");
            return ResponseEntity.ok(cashCardOptional.get());
        } else {
            log.debug("it will return not found if id doesn't find the CashCard by Id");
            return ResponseEntity.notFound().build();
        }
    }
}
