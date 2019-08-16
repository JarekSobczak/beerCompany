package com.sda.projects.beerCompany;

public class Main {
    public static void main(String[] args) throws MoreThanOneTheSameDataOfEmploeeException, NoEmploeeException, BeerNotFoundException, WarehouseFullException {

        BeerCompanyDao dao = new BeerCompanyDao();
        Warehouse warehouse=new Warehouse();
        WarehouseService warehouseService = new WarehouseService(warehouse);
        BeerCompanyService companyService=new BeerCompanyService(dao,warehouseService);

        companyService.addNewEmploee("Kowalski Jan", 3345.90, EmploeePosition.MAGAZYNIER);
        companyService.addNewEmploee("Kowalski Jan", 3345.90, EmploeePosition.INŻYNIER);
        companyService.addNewEmploee("Kowalski Jan", 2345.90, EmploeePosition.INŻYNIER);
        companyService.addNewEmploee("Nowak Zenon", 4395.90, EmploeePosition.INŻYNIER);
        companyService.addNewEmploee("Bąk Marek", 1999.20, EmploeePosition.TESTER_PIWA);


        warehouseService.completeTheWarehouse(200, TypeOfBeers.LECH);
        warehouseService.completeTheWarehouse(400, TypeOfBeers.PERŁA);
        warehouseService.completeTheWarehouse(12, TypeOfBeers.LEŻAJSK);

        warehouseService.ShowWarehouseState();

        companyService.showAllEmploees();

        warehouseService.completeTheWarehouse(700, TypeOfBeers.LECH);
        warehouseService.completeTheWarehouse(600, TypeOfBeers.PERŁA);
        warehouseService.ShowWarehouseState();
        companyService.drinkBeer(TypeOfBeers.LEŻAJSK, "Bąk Marek");
        companyService.drinkBeer(TypeOfBeers.LEŻAJSK, "Bąk Marek");
        companyService.drinkBeer(TypeOfBeers.LEŻAJSK, "Bąk Marek");
        companyService.drinkBeer(TypeOfBeers.LEŻAJSK, "Bąk Marek");
        companyService.drinkBeer(TypeOfBeers.LEŻAJSK, "Bąk Marek");
        companyService.drinkBeer(TypeOfBeers.LEŻAJSK, "Bąk Marek");
        companyService.drinkBeer(TypeOfBeers.LEŻAJSK, "Nowak Zenon");
        companyService.drinkBeer(TypeOfBeers.LEŻAJSK, "Nowak Zenon");
        companyService.drinkBeer(TypeOfBeers.LEŻAJSK, "Nowak Zenon");
        warehouseService.ShowWarehouseState();
        System.out.println("BĄK wypił " + dao.getEmploees().stream()
                .filter(emp -> emp.getName().equalsIgnoreCase("Bąk Marek"))
                .findFirst().get().getNumberOfDrunkBeers());
        System.out.println("NOWAK wypił " + dao.getEmploees().stream()
                .filter(emp -> emp.getName().equalsIgnoreCase("Nowak Zenon"))
                .findFirst().get().getNumberOfDrunkBeers());

        System.out.println("Pracownik roku : " + companyService.findEmploeeOfTheYear());
        System.out.println(warehouseService.isAvailable(TypeOfBeers.LEŻAJSK));
        System.out.println(warehouseService.isAvailable(TypeOfBeers.LECH));
        System.out.println(warehouseService.isAvailable(TypeOfBeers.PERŁA));

        System.out.println("Najwięcej jest piwa : " + warehouseService.getTypeOfBeerWithTheLargestAmount().toString());
        System.out.println("Liczba piwa ogółem : " + warehouseService.getNumberOfAllBeers().toString());
        System.out.println(companyService.findEmploee("Kowalski Jan").toString());
    }
}
