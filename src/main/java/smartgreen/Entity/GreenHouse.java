package smartgreen.Entity;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
public class GreenHouse {
    @Id
    private Integer Id;
    @Column
    private String username;

    public int getId() {
        return Id;
    }

    public String getUsername() {
        return username;
    }
    public static GreenHouseBuilder builder(){
        return new GreenHouseBuilder();
    }
    public static class GreenHouseBuilder {
        private Integer Id;
        private String username;

        public GreenHouseBuilder withId (){
             this.Id = Id;
             return this;

        }
        public GreenHouseBuilder withUsername (){
            this.username = username;
            return this;

        }
        public GreenHouse build(){
            GreenHouse greenHouse = new GreenHouse();
            greenHouse.Id=this.Id;
            greenHouse.username=this.username;
            return greenHouse;

        }


    }

    @Override
    public String toString() {
        return "GreenHouse{" +
                "Id=" + Id +
                '}';
    }
}
