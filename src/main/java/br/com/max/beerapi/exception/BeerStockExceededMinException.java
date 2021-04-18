package br.com.max.beerapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerStockExceededMinException extends Exception {
    public BeerStockExceededMinException(Long id, int quantityToDecrement) {
        super(String.format("Beer with id %s to decrement informed exceeds the min stock capacity: %s", id, quantityToDecrement));
    }
}
