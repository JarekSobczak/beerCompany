package com.sda.projects.beerCompany;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class WarehouseServiceTest {
    private WarehouseService service;
    @Before
    public void setUp() throws Exception {
        Warehouse warehouse=new Warehouse();
        service=new WarehouseService(warehouse);
    }

    @Test
    public void completeTheWarehouseImplementsBeerTypeAndBeerQuantityTopsUpTheStock() throws WarehouseFullException {
        //when
        service.completeTheWarehouse(200,TypeOfBeers.PERŁA);
        service.completeTheWarehouse(300,TypeOfBeers.PERŁA);
        Integer expected=500;
        Integer result=service.getState(TypeOfBeers.PERŁA);
        //then
        assertEquals(expected,result);
    }
    @Test(expected = WarehouseFullException.class)
    public void completeTheWarehouseOverloadsBeerQuantityThrowsException() throws WarehouseFullException {
        //when
        service.completeTheWarehouse(200,TypeOfBeers.PERŁA);
        service.completeTheWarehouse(801,TypeOfBeers.PERŁA);

    }

    @Test
    public void getStateImplementsFindableTypeOfBeerReturnsQuantity() throws WarehouseFullException {
        //given
        TypeOfBeers typeOfBeers=TypeOfBeers.LECH;
        //when
        service.completeTheWarehouse(7,TypeOfBeers.LECH);
        Integer expected=7;
        Integer result=service.getState(TypeOfBeers.LECH);
        //then
        assertEquals(expected,result);
    }
    @Test
    public void getStateImplementsUnfindableTypeOfBeerReturnsQuantity() throws WarehouseFullException {
        //given
        TypeOfBeers typeOfBeers=TypeOfBeers.LEŻAJSK;
        //when
        service.completeTheWarehouse(7,TypeOfBeers.LECH);
        Integer expected=0;
        Integer result=service.getState(typeOfBeers);
        //then
        assertEquals(expected,result);
    }

    @Test
    public void decrementNumberOfBeersInTypeImplementsBeerTypeAndDecreasesQuantity() throws WarehouseFullException, BeerNotFoundException {
        //given
        TypeOfBeers typeOfBeers=TypeOfBeers.LEŻAJSK;
        service.completeTheWarehouse(1,TypeOfBeers.LEŻAJSK);
        //when
        service.decrementNumberOfBeersInType(typeOfBeers);
        int expected=0;
        int result=service.getState(typeOfBeers);
        //then
        assertEquals(expected,result);
    }
    @Test(expected = BeerNotFoundException.class)
    public void decrementNumberOfBeersInTypeImplementsBeerTypeAndDecreasesQuantityOutOfState() throws WarehouseFullException, BeerNotFoundException {
        //given
        TypeOfBeers typeOfBeers=TypeOfBeers.LEŻAJSK;
        service.completeTheWarehouse(1,TypeOfBeers.LEŻAJSK);
        //when
        service.decrementNumberOfBeersInType(typeOfBeers);
        service.decrementNumberOfBeersInType(typeOfBeers);

    }
    @Test
    public void isAvailableImplementsBeerTypeReturnsTrue() throws WarehouseFullException {
        //given
        TypeOfBeers typeOfBeer=TypeOfBeers.LEŻAJSK;
        service.completeTheWarehouse(1,TypeOfBeers.LEŻAJSK);
        //when
        boolean result=service.isAvailable(typeOfBeer);
        //then
        assertTrue(result);
    }
    @Test
    public void isAvailableImplementsBeerTypeReturnsFalse() throws WarehouseFullException {
        //given
        TypeOfBeers typeOfBeer=TypeOfBeers.LECH;
        service.completeTheWarehouse(1,TypeOfBeers.LEŻAJSK);
        //when
        boolean result=service.isAvailable(typeOfBeer);
        //then
        assertFalse(result);
    }
    @Test
    public void getTypeOfBeerWithTheLargestAmountImplementsBeerTypesReturnsTheLargest() throws WarehouseFullException, BeerNotFoundException {
        //given
        service.completeTheWarehouse(100,TypeOfBeers.LEŻAJSK);
        service.completeTheWarehouse(200,TypeOfBeers.LECH);
        service.completeTheWarehouse(300,TypeOfBeers.PERŁA);
        //when
        TypeOfBeers expected=TypeOfBeers.PERŁA;
        TypeOfBeers result=service.getTypeOfBeerWithTheLargestAmount();
        //then
        assertEquals(expected,result);
    }
    @Test(expected = NoSuchElementException.class)
    public void getTypeOfBeerWithTheLargestAmountImplementsNullBeerTypesThrowsException() throws BeerNotFoundException {
        //when
        TypeOfBeers expected=null;
        TypeOfBeers result=service.getTypeOfBeerWithTheLargestAmount();
    }
    @Test(expected = BeerNotFoundException.class)
    public void getTypeOfBeerWithTheLargestAmountImplementsBeerTypesWithZeroAmountReturnsException() throws WarehouseFullException, BeerNotFoundException {
        //given
        service.completeTheWarehouse(0,TypeOfBeers.LEŻAJSK);
        service.completeTheWarehouse(0,TypeOfBeers.LECH);
        service.completeTheWarehouse(0,TypeOfBeers.PERŁA);
        //when
        TypeOfBeers result=service.getTypeOfBeerWithTheLargestAmount();

    }
    @Test
    public void getNumberOfAllBeersImplementsFewBeerTypesReturnsQuantity() throws WarehouseFullException {
        //given
        service.completeTheWarehouse(100,TypeOfBeers.LEŻAJSK);
        service.completeTheWarehouse(101,TypeOfBeers.LECH);
        service.completeTheWarehouse(102,TypeOfBeers.PERŁA);
        //when
        int expected=303;
        int result=service.getNumberOfAllBeers();
        //then
        assertEquals(expected,result);
    }
    @Test
    public void getNumberOfAllBeersImplementsNullReturnsZero() throws WarehouseFullException {
        //when
        int expected=0;
        int result= service.getNumberOfAllBeers();
        //then
        assertEquals(expected,result);

    }
}