package com.sda.projects.beerCompany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
public class Warehouse {
    private Map<TypeOfBeers,Integer>numberOfBeersInType=new HashMap<>();
}
