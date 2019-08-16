package com.sda.projects.beerCompany;

public class WarehouseFullException extends Exception{

    public WarehouseFullException(TypeOfBeers typeOfBeers,Integer state){
        super( "Brak miejsca w magazynie ! Można przyjąć "+(1000-state)+ " sztuk piwa "+typeOfBeers.toString());

    }
}
