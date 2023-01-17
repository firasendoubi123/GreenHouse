package smartgreen.Entity;

import jakarta.json.JsonValue;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

@Entity
public class Sensor {
    @Id
    private  Integer id ;

    @Column()
    private  SensorType type ;
    @Column
    private  Integer HouseId ;


    @Column()
    private double values  ;

    public Integer getId() {
        return id;
    }
    public Integer getHouseId() {
        return HouseId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public void setHouseId(Integer houseId) {
        HouseId = houseId;
    }

    public void setValues(double values) {
        this.values = values;
    }

    public String getType() {
        return String.valueOf(type);
    }
    public double getValue() {
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

        public SensorBuilder withType(String type){
            this.type = SensorType.valueOf(type);
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
