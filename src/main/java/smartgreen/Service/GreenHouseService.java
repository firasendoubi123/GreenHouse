package smartgreen.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import smartgreen.Entity.GreenHouse;
import smartgreen.Repository.GreenHouseRepository;
@ApplicationScoped
public class GreenHouseService {
    @Inject
    private GreenHouseRepository HouseRepository;

    public GreenHouse createHouse(GreenHouse house){
        GreenHouse house1 = GreenHouse.builder()
                .withId(house.getId())
                .withUsername(house.getUsername()).build();
        return HouseRepository.save(house1);

    }
    public void delete(Integer id) {

        HouseRepository.deleteById(id);

    }

}
