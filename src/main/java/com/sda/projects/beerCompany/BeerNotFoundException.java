package com.sda.projects.beerCompany;

public class BeerNotFoundException extends Throwable {
    public BeerNotFoundException(TypeOfBeers typeOfBeers){
        super("Brak  piwa "+typeOfBeers+" w magazynie !");
    }
}
