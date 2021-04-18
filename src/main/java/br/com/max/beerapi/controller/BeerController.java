package br.com.max.beerapi.controller;

import br.com.max.beerapi.dto.BeerDTO;
import br.com.max.beerapi.dto.QuantityDTO;
import br.com.max.beerapi.exception.BeerAlreadyRegisteredException;
import br.com.max.beerapi.exception.BeerNotFoundException;
import br.com.max.beerapi.exception.BeerStockExceededMaxException;
import br.com.max.beerapi.exception.BeerStockExceededMinException;
import br.com.max.beerapi.service.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/beers")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerController {

    private BeerService beerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO createBeer(@RequestBody @Valid BeerDTO beerDTO) throws BeerAlreadyRegisteredException {
       return beerService.createBeer(beerDTO);
    }

    @GetMapping
    public List<BeerDTO> findAll() {
        return beerService.findAll();
    }

    @GetMapping("/{name}")
    public BeerDTO findByName(@PathVariable String name) throws BeerNotFoundException {
        return beerService.findByName(name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws BeerNotFoundException {
        beerService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public BeerDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws BeerNotFoundException, BeerStockExceededMaxException {
        return beerService.increment(id, quantityDTO.getQuantity());
    }

    @PatchMapping("/{id}/decrement")
    public BeerDTO decrement(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws BeerNotFoundException, BeerStockExceededMinException {
        return beerService.decrement(id, quantityDTO.getQuantity());
    }
}
