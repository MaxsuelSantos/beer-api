package br.com.max.beerapi.dto;

import br.com.max.beerapi.enums.BeerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerDTO {

    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private int max;
    @Column(nullable = false)
    private int quantity;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BeerType type;
}
