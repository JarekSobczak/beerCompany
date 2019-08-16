package com.sda.projects.beerCompany;

import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class BeerCompanyServiceTest {

    BeerCompanyService service;
    WarehouseService warehouseService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        BeerCompanyDao dao=new BeerCompanyDao();
        Warehouse warehouse=new Warehouse();
        warehouseService=new WarehouseService(warehouse);
        service = new BeerCompanyService(dao,warehouseService);
    }

    @Test
    public void findEmploeeImplementsExistingNameReturnsEmploee() throws MoreThanOneTheSameDataOfEmploeeException, NoEmploeeException {
        //given
        String name = "Nowak Jan";
        Emploee expected = service.addNewEmploee("Nowak Jan", 2200.0, EmploeePosition.MAGAZYNIER);

        //when
        Emploee result = service.findEmploee(name);
        //then
        assertEquals(expected, result);
    }

    @Test
    public void findEmploeeImplementsNoExistingNameReturnsNull() throws MoreThanOneTheSameDataOfEmploeeException, NoEmploeeException {
        //given
        String name = "Now Jaś";
        service.addNewEmploee("Nowak Zenon", 2200.0, EmploeePosition.MAGAZYNIER);
        Emploee expected = null;
        //when
        Emploee result = service.findEmploee(name);
        //then
        assertEquals(result, expected);
    }

    @Test
    public void findEmploeeImplementsExistingNameThrowsMoreThanOneTheSameDataOfEmploeeException() throws MoreThanOneTheSameDataOfEmploeeException, NoEmploeeException {
        //given
        thrown.expect(MoreThanOneTheSameDataOfEmploeeException.class);
        String name = "Bąk Adam";
        service.addNewEmploee("Bąk Adam", 2200.0, EmploeePosition.MAGAZYNIER);
        service.addNewEmploee("Bąk Adam", 2200.0, EmploeePosition.MAGAZYNIER);
        //when
        service.findEmploee(name);
    }

    @Test
    public void getEmploeeByNameImplementsExistingNameReturnsIndividualEmploee() throws NoEmploeeException {
        //given
        String name = "Dąb Marian";
        Emploee expected = service.addNewEmploee("Dąb Marian", 2000.90, EmploeePosition.INŻYNIER);
        //when
        Emploee result = service.getEmploeeByName(name);
        assertEquals(expected, result);
    }

    @Test(expected = NoEmploeeException.class)
    public void getEmploeeByNameImplementsFalseNameReturnsException() throws NoEmploeeException {
        //given
        String name = "Dzik Zbigniew";
        service.addNewEmploee("Dąb Marian", 2000.90, EmploeePosition.INŻYNIER);
        //when
        service.getEmploeeByName(name);
    }

    @Test
    public void findEmploeeWithBestWageImplementsExistingNameReturnsCorrectEmploee() {
        //given
        String name = "Brzoza Anna";
        service.addNewEmploee("Brzoza Anna", 1345.0, EmploeePosition.INŻYNIER);
        Emploee expected = service.addNewEmploee("Brzoza Anna", 2345.0, EmploeePosition.INŻYNIER);
        //when
        Emploee result = service.findEmploeeWithBestWage(name);
        //then
        assertEquals(expected, result);
    }

    @Test
    public void findEmploeeWithBestWageImplementsFalseNameReturnsCorrectEmploee() {
        //given
        String name = "Kłoda Anna";
        service.addNewEmploee("Brzoza Anna", 1345.0, EmploeePosition.INŻYNIER);
        Emploee expected = null;
        //when
        Emploee result = service.findEmploeeWithBestWage(name);
        //then
        assertEquals(expected, result);
    }

    @Test
    public void countTheSameNameEmploeesImplementsTheSameNamesReturnsValidNumber() {
        //given
        String name = "Byk Roman";
        service.addNewEmploee("Byk Roman", 3200.0, EmploeePosition.TESTER_PIWA);
        service.addNewEmploee("Byk Roman", 3200.0, EmploeePosition.TESTER_PIWA);
        service.addNewEmploee("Byk Roman", 3200.0, EmploeePosition.TESTER_PIWA);
        service.addNewEmploee("Byk Roman", 3200.0, EmploeePosition.TESTER_PIWA);
        //when
        long expected = 4;
        long result = service.countTheSameNameEmploees(name);
        //then
        assertEquals(expected, result);
    }

    @Test
    public void countTheSameNameEmploeesImplementsFalseNameReturnsZero() {
        //given
        String name = "Bzyk Roman";
        service.addNewEmploee("Bąk Roman", 3200.0, EmploeePosition.TESTER_PIWA);
        service.addNewEmploee("Bąk Roman", 3200.0, EmploeePosition.TESTER_PIWA);
        service.addNewEmploee("Bąk Roman", 3200.0, EmploeePosition.TESTER_PIWA);
        service.addNewEmploee("Bąk Roman", 3200.0, EmploeePosition.TESTER_PIWA);
        //when
        long expected = 0;
        long result = service.countTheSameNameEmploees(name);
        assertEquals(expected, result);
    }

    @Test
    public void drinkBeerImplementsCorrectNameAddsDrunkBeerTakesBeerFromStock() throws BeerNotFoundException, NoEmploeeException, MoreThanOneTheSameDataOfEmploeeException, WarehouseFullException {
        //given
        String name = "Smakosz Jan";
        service.addNewEmploee("Smakosz Jan", 1790.0, EmploeePosition.TESTER_PIWA);
        warehouseService.completeTheWarehouse(1, TypeOfBeers.LECH);

        //when
        int drunkExpected = 1;
        int stateExpected =warehouseService.getState(TypeOfBeers.LECH) - 1;
        service.drinkBeer(TypeOfBeers.LECH, name);
        int drunkResult = service.findEmploee(name).getNumberOfDrunkBeers();
        int stateResult =warehouseService.getState(TypeOfBeers.LECH);
        //then
        assertEquals(drunkExpected, drunkResult);
        assertEquals(stateExpected, stateResult);
    }

    @Test(expected = BeerNotFoundException.class)
    public void drinkBeerImplementsOverDrunkBeerTypeThrowsBeerNotFoundException() throws BeerNotFoundException, NoEmploeeException, MoreThanOneTheSameDataOfEmploeeException, WarehouseFullException {
        //given
        String name = "Smakosz Jan";
        service.addNewEmploee("Smakosz Jan", 1790.0, EmploeePosition.TESTER_PIWA);
        warehouseService.completeTheWarehouse(1, TypeOfBeers.LECH);
        //when
        service.drinkBeer(TypeOfBeers.LECH, name);
        service.drinkBeer(TypeOfBeers.LECH, name);
    }

    @Test(expected = NoEmploeeException.class)
    public void drinkBeerImplementsFalseNameThrowsNoEmploeeException() throws BeerNotFoundException, NoEmploeeException, MoreThanOneTheSameDataOfEmploeeException, WarehouseFullException {
        //given
        String name = "Smakoszyński Jan";
        service.addNewEmploee("Smakoszkiewicz Jan", 1790.0, EmploeePosition.TESTER_PIWA);
        warehouseService.completeTheWarehouse(1, TypeOfBeers.LECH);
        //when
        service.drinkBeer(TypeOfBeers.LECH, name);
    }

    @Test(expected = MoreThanOneTheSameDataOfEmploeeException.class)
    public void drinkBeerImplementsCorrectTwoNamesThrowsMoreThanOneTheSameEmploeeException() throws BeerNotFoundException, NoEmploeeException, MoreThanOneTheSameDataOfEmploeeException, WarehouseFullException {
        //given
        String name = "Smak Jan";
        TypeOfBeers typeOfBeers = TypeOfBeers.LECH;
        service.addNewEmploee("Smak Jan", 1790.0, EmploeePosition.TESTER_PIWA);
        service.addNewEmploee("Smak Jan", 1790.0, EmploeePosition.TESTER_PIWA);
        warehouseService.completeTheWarehouse(1, TypeOfBeers.LECH);
        //when
        service.drinkBeer(TypeOfBeers.LECH, name);
    }


    @Test
    public void findEmploeeOfTheYearImplementsTwoEmploeesReturnsTheWiner() throws BeerNotFoundException, NoEmploeeException, MoreThanOneTheSameDataOfEmploeeException, WarehouseFullException {
        //given
        service.addNewEmploee("Żak Damian", 1599.0, EmploeePosition.TESTER_PIWA);
        service.addNewEmploee("Ząb Julian", 3699.99, EmploeePosition.INŻYNIER);
        warehouseService.completeTheWarehouse(20, TypeOfBeers.PERŁA);
        //when
        service.drinkBeer(TypeOfBeers.PERŁA, "Żak Damian");
        service.drinkBeer(TypeOfBeers.PERŁA, "Żak Damian");
        service.drinkBeer(TypeOfBeers.PERŁA, "Żak Damian");
        service.drinkBeer(TypeOfBeers.PERŁA, "Żak Damian");
        service.drinkBeer(TypeOfBeers.PERŁA, "Ząb Julian");
        String expected = service.findEmploee("Ząb Julian").toString();
        String result = service.findEmploeeOfTheYear();
        //then
        assertEquals(expected, result);
    }

    @Test(expected = NoEmploeeException.class)
    public void findEmploeeOfTheYearReturnsNull() throws BeerNotFoundException, NoEmploeeException, MoreThanOneTheSameDataOfEmploeeException {
        //when
        String expected = null;
        String result = service.findEmploeeOfTheYear();

    }
}