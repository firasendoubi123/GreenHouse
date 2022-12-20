package smartgreen.Entity;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
@Entity
public class Sensor {
    @Id
    private  Integer id ;

    @Column("sensor_type")
    private  SensorType type ;

    public Integer getId() {
        return id;
    }

    public SensorType getType() {
        return type;
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

        public SensorBuilder withId(){
            this.id = id;
            return this;
        }

        public SensorBuilder withType(){
            this.type = type;
            return this ;
        }
        public Sensor build (){
            Sensor sensor = new Sensor();
            sensor.id=id;
            sensor.type=type;
            return sensor;

        }
    }


}
