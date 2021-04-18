package br.com.max.beerapi.service;

import br.com.max.beerapi.dto.BeerDTO;
import br.com.max.beerapi.entity.Beer;
import br.com.max.beerapi.exception.BeerAlreadyRegisteredException;
import br.com.max.beerapi.exception.BeerNotFoundException;
import br.com.max.beerapi.exception.BeerStockExceededMaxException;
import br.com.max.beerapi.exception.BeerStockExceededMinException;
import br.com.max.beerapi.mapper.BeerMapper;
import br.com.max.beerapi.repository.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper = BeerMapper.INSTANCE;

    public BeerDTO createBeer(BeerDTO beerDTO) throws BeerAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(beerDTO.getName());
        Beer beer = beerMapper.toModel(beerDTO);
        Beer savedBeer = beerRepository.save(beer);
        return beerMapper.toDTO(savedBeer);
    }

    public List<BeerDTO> findAll() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BeerDTO findByName(String name) throws BeerNotFoundException {
        Beer beer = beerRepository.findByName(name).orElseThrow(
                () -> new BeerNotFoundException(name)
        );

        return beerMapper.toDTO(beer);
    }

    public void deleteById(Long id) throws BeerNotFoundException {
        VerifyIfExists(id);
        beerRepository.deleteById(id);
    }

    public BeerDTO increment(Long id, int quantityToIncrement) throws BeerNotFoundException, BeerStockExceededMaxException {
        Beer beerToIncrementStock = VerifyIfExists(id);
        int quantityAfterIncrement = quantityToIncrement + beerToIncrementStock.getQuantity();
        if (quantityAfterIncrement <= beerToIncrementStock.getMax()) {
            beerToIncrementStock.setQuantity(quantityAfterIncrement);
            Beer incrementedBeerStock = beerRepository.save(beerToIncrementStock);
            return beerMapper.toDTO(incrementedBeerStock);
        }
        throw new BeerStockExceededMaxException(id, quantityToIncrement);
    }

    public BeerDTO decrement(Long id, int quantityToDecrement) throws BeerNotFoundException, BeerStockExceededMinException {
        Beer beerToDecrementStock = VerifyIfExists(id);
        int quantityAfterDecrement = beerToDecrementStock.getQuantity() - quantityToDecrement;
        if (quantityAfterDecrement >= 0) {
            beerToDecrementStock.setQuantity(quantityAfterDecrement);
            Beer decrementedBeerStock = beerRepository.save(beerToDecrementStock);
            return beerMapper.toDTO(decrementedBeerStock);
        }
        throw new BeerStockExceededMinException(id, quantityToDecrement);
    }

    private void verifyIfIsAlreadyRegistered(String name) throws BeerAlreadyRegisteredException {
        Optional<Beer> optSavedBeer = beerRepository.findByName(name);
        if (optSavedBeer.isPresent()) {
            throw new BeerAlreadyRegisteredException(name);
        }
    }

    private Beer VerifyIfExists(Long id) throws BeerNotFoundException {
        return beerRepository.findById(id).orElseThrow(
                () -> new BeerNotFoundException(id)
        );
    }


}
