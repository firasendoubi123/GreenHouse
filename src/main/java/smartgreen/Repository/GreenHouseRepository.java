package smartgreen.Repository;

import jakarta.nosql.mapping.Repository;
import smartgreen.Entity.GreenHouse;
import java.util.*;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public interface  GreenHouseRepository extends Repository <GreenHouse,Integer> {
    Set<GreenHouse> findAll()  ;
    Set<GreenHouse> findByUserName(String username) ;
}