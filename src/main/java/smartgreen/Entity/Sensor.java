package smartgreen.Entity;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
import java.util.*;
@Entity
public class Sensor {
    @Id
    private  Integer id ;

    @Column()
    private  SensorType type ;
    @Column
    private  Integer HouseId ;


    @Column()
    private Set<Double> values =new HashSet<>() ;

    public Integer getId() {
        return id;
    }
    public Integer getHouseId() {
        return HouseId;
    }



    public SensorType getType() {
        return type;
    }
    public Set<Double> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id + " " +
                ", type=" + type +
                '}';
    }
    public static SensorBuilder builder(){
        return new SensorBuilder ();
    }
    public static class SensorBuilder {
        private Integer id;
        private SensorType type;

        private Integer houseId;

        public SensorBuilder withId(Integer id){
            this.id = id;
            return this;
        }

        public SensorBuilder withType(SensorType type){
            this.type = type;
            return this ;
        }
        public SensorBuilder withHouse(Integer houseId){
            this.houseId = houseId;
            return this ;
        }
        public Sensor build (){
            Sensor sensor = new Sensor();
            sensor.id=id;
            sensor.type=type;
            sensor.HouseId=houseId;
            return sensor;

        }
    }


}
