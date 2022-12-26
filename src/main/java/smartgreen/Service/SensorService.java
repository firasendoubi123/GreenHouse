package smartgreen.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.nosql.mapping.Database;
import jakarta.nosql.mapping.DatabaseType;
import smartgreen.Entity.GreenHouse;
import smartgreen.Entity.Sensor;
import smartgreen.Entity.User;
import smartgreen.Repository.SensorRepository;

import java.util.List;
@ApplicationScoped
public class SensorService  {
    @Inject
    @Database(DatabaseType.DOCUMENT)
    private SensorRepository repository;

    public void createsensor(Sensor sensor){
        Sensor sensor1= Sensor.builder().withId(sensor.getId())
                .withType(sensor.getType())
                .withHouse(sensor.getHouseId()).build();
        repository.save(sensor1);
    }


    public List<Sensor> getSensor() {

        return repository.findAll();
    }
    public List<Sensor> findByHouseId(Integer HouseId){
        return repository.findByHouseId(HouseId);
    }

}