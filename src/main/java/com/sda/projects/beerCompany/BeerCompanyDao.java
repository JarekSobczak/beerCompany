package com.sda.projects.beerCompany;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@EqualsAndHashCode
@Getter
public class BeerCompanyDao {

    private Map<EmploeePosition, Integer> numberOfEmploeesOnPosition;
    private List<Emploee> emploees = new ArrayList<>();
}
