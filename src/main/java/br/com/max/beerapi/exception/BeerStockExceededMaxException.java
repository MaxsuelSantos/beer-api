package br.com.max.beerapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerStockExceededMaxException extends Exception {
    public BeerStockExceededMaxException(Long id, int quantityToIncrement) {
        super(String.format("Beer with id %s to increment informed exceeds the max stock capacity: %s", id, quantityToIncrement));
    }
}
