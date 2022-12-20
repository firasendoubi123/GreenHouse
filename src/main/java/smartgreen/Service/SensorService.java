package smartgreen.Service;

import smartgreen.Entity.Sensor;
import smartgreen.Entity.User;
import smartgreen.Repository.SensorRepository;

import java.util.List;

public class SensorService  {
    private SensorRepository repository;

    public List<Sensor> getSensor() {
        return repository.findAll();
    }
}