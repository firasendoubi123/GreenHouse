package smartgreen.Repository;

import jakarta.nosql.mapping.Repository;
import smartgreen.Entity.Sensor;
import smartgreen.Entity.SensorType;
import java.util.*;
import jakarta.enterprise.context.ApplicationScoped;




@ApplicationScoped

public interface  SensorRepository extends Repository <Sensor,SensorType> {
    Optional<Sensor> findByType(SensorType type);
    List<Sensor> findByHouseId(Integer HouseId);

    List <Sensor> findAll();
}
