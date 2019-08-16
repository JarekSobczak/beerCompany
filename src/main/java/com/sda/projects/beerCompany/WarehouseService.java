package com.sda.projects.beerCompany;

import java.util.*;

public class WarehouseService {

   private final Warehouse warehouse;

    public WarehouseService(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void completeTheWarehouse(Integer numberOfBeers, TypeOfBeers typeOfBeers) throws WarehouseFullException {
            if (getState(typeOfBeers) + numberOfBeers <= 1000) {
                warehouse.getNumberOfBeersInType().put(typeOfBeers, getState(typeOfBeers) + numberOfBeers);
            } else throw new WarehouseFullException(typeOfBeers,getState(typeOfBeers));

    }

    public Integer getState(TypeOfBeers typeOfBeers) {

        return warehouse.getNumberOfBeersInType().entrySet().stream()
                .filter(k -> k.getKey().equals(typeOfBeers))
                .map(v -> v.getValue())
                .findFirst()
                .orElse(0);
    }

    public void ShowWarehouseState() {
        warehouse.getNumberOfBeersInType().entrySet()
                .stream()
                .forEach(beer -> System.out.println(beer.getKey() + " " + beer.getValue()));
    }

    public void decrementNumberOfBeersInType(TypeOfBeers typeOfBeers) throws BeerNotFoundException {
        Integer state = getState(typeOfBeers);
        if (state <= 0) {
            throw new BeerNotFoundException(typeOfBeers);
        }
        warehouse.getNumberOfBeersInType().entrySet().stream()
                .filter(k -> k.getKey().equals(typeOfBeers))
                .findFirst()
                .get().setValue(state - 1);
    }

    public boolean isAvailable(TypeOfBeers typeOfBeers) {
        return warehouse.getNumberOfBeersInType()
                .entrySet()
                .stream()
                .filter(k -> k.getKey().equals(typeOfBeers))
                .map(v -> v.getValue() > 0)
                .findAny()
                .isPresent();
    }

    public TypeOfBeers getTypeOfBeerWithTheLargestAmount() throws BeerNotFoundException {
        Optional<Map.Entry<TypeOfBeers,Integer>> amount=warehouse.getNumberOfBeersInType()
                .entrySet()
                .stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(v -> v.getValue()));

        if(amount.get().getValue()<1)throw new BeerNotFoundException(amount.get().getKey());
        return amount.get()
                .getKey();
    }

    public Integer getNumberOfAllBeers() {
        return Optional.ofNullable(warehouse.getNumberOfBeersInType())
                .orElse(Collections.emptyMap())
                .entrySet()
                .stream()
                .mapToInt(v -> v.getValue())
                .sum();
    }
}
