package com.sda.projects.beerCompany;

import java.util.Collections;
import java.util.Comparator;

import java.util.Objects;
import java.util.Optional;

public class BeerCompanyService {

    private final BeerCompanyDao dao;
    private final WarehouseService warehouseService;

    public BeerCompanyService(BeerCompanyDao dao, WarehouseService warehouseService) {
        this.dao = dao;
        this.warehouseService = warehouseService;
    }

    public Emploee addNewEmploee(String name, Double salary, EmploeePosition position) {
        Emploee emploee = new Emploee(name, salary, position, 0);
        dao.getEmploees().add(emploee);
        return emploee;
    }

    public void showAllEmploees() {
        dao.getEmploees()
                .forEach(emp -> System.out.println(emp.toString()));
    }

    public Emploee findEmploee(String name) throws MoreThanOneTheSameDataOfEmploeeException, NoEmploeeException {

        if (countTheSameNameEmploees(name) < 1) return null;
        if (countTheSameNameEmploees(name) > 1) {
            if (dao.getEmploees().stream()
                    .filter(emp -> emp.getName().equals(name))
                    .filter(emp -> emp.getSalary().equals(findEmploeeWithBestWage(name).getSalary()))
                    .distinct()
                    .count() > 1) {
                throw new MoreThanOneTheSameDataOfEmploeeException();
            } else return findEmploeeWithBestWage(name);
        }
        return getEmploeeByName(name);
    }


    public Emploee getEmploeeByName(String name) throws NoEmploeeException {
        return dao.getEmploees().stream()
                .filter(k -> k.getName().equals(name))
                .findFirst()
                .orElseThrow(NoEmploeeException::new);
    }


    public Emploee findEmploeeWithBestWage(String name) {
        return Optional.ofNullable(dao.getEmploees()).orElse(Collections.emptyList())
                .stream()
                .filter(k -> k.getName().equals(name))
                .filter(Objects::nonNull)
                .max(Comparator.comparing(v -> v.getSalary()))
                .orElse(null);
    }

    public long countTheSameNameEmploees(String name) {
        return dao.getEmploees().stream()
                .filter(k -> k.getName().equals(name))
                .distinct()
                .count();
    }

    public void drinkBeer(TypeOfBeers typeOfBeers, String name) throws BeerNotFoundException, NoEmploeeException, MoreThanOneTheSameDataOfEmploeeException {
        if (countTheSameNameEmploees(name) > 1) throw new MoreThanOneTheSameDataOfEmploeeException();
        else
            warehouseService.decrementNumberOfBeersInType(typeOfBeers);
        Integer drunkBeers = dao.getEmploees().stream()
                .filter(emp -> emp.getName().equals(name))
                .findFirst()
                .orElseThrow(NoEmploeeException::new)
                .getNumberOfDrunkBeers();

        getEmploeeByName(name).setNumberOfDrunkBeers(drunkBeers + 1);
        System.out.println(name + " pije " + typeOfBeers);
    }

    public String findEmploeeOfTheYear() throws NoEmploeeException {

        return dao.getEmploees().stream()
                .max(Comparator.comparing(v -> v.getNumberOfDrunkBeers() + (v.getPosition().beerPremium)))
                .orElseThrow(NoEmploeeException::new)
                .toString();


    }
}
