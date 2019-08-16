package com.sda.projects.beerCompany;

public class NoEmploeeException extends Throwable {
    public NoEmploeeException(){
        super("Brak pracownika o takich danych !");
    }
}
