package com.sda.projects.beerCompany;

public enum EmploeePosition {

    INŻYNIER(10), TESTER_PIWA(0), MAGAZYNIER(5);
    int beerPremium;

    EmploeePosition(int beerPremium) {
        this.beerPremium = beerPremium;
    }
}
