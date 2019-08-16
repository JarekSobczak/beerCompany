package com.sda.projects.beerCompany;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Emploee {
    private String name;
    private Double salary;
    private EmploeePosition position;
    private Integer numberOfDrunkBeers;
}
