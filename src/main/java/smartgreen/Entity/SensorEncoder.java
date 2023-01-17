package smartgreen.Entity;
import smartgreen.Entity.Sensor;
import smartgreen.Entity.SensorType;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class SensorEncoder implements Encoder.Text<Sensor> {

    @Override
    public String encode(Sensor message) throws EncodeException {

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("idSensor", message.getId())
                .add("sensorType", message.getType())
                .add("sensorValue", message.getValue())
                .build();
        return jsonObject.toString();

    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("Initializing message encoder");
    }

    @Override
    public void destroy() {
        System.out.println("Destroying encoder...");
    }
}


