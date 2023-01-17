package smartgreen.Service;

import jakarta.inject.Inject;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import smartgreen.Entity.Sensor;
import smartgreen.Repository.SensorRepository;
import smartgreen.Entity.SensorType;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import javax.net.ssl.SSLSocketFactory;
import smartgreen.Controller.WebSocket;

@Singleton
@Startup
public class MqttConnection {



    @Inject
    private SensorRepository sensorRepository;

    public void SendMsg (MqttClient client,String msg,String topic) throws MqttException {
        MqttMessage message = new MqttMessage(msg.getBytes());
        client.publish(topic,message);
    }

    @PostConstruct
    public void start() {
        try {
            System.out.println("MQTT started");

            //CLIENT CONNECTION OPTIONS
            MqttClient client = new MqttClient(
                    "wss://mqtt.smart-irrigation.me:8083",
                    MqttClient.generateClientId(),
                    new MemoryPersistence());

            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setUserName("firas");
            mqttConnectOptions.setPassword("firas123".toCharArray());
            mqttConnectOptions.setSocketFactory(SSLSocketFactory.getDefault());

            client.connect(mqttConnectOptions);


            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("client lost connection " + cause);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    // System.out.println(new String(message.getPayload()));
                    System.out.println("We have message from  " + topic);

                     if (topic.equals("Sensor")) {
                        try {
                            System.out.println("Sensor :"+ message+" is successfully added");
                            JSONObject obj = new JSONObject(new String(message.getPayload()));
                            Sensor sensor =new Sensor();
                            sensor.setId(1);
                            sensor.setType(SensorType.valueOf(obj.getString("sensorType")));
                            sensor.setValues(obj.getInt("sensorValue"));
                            WebSocket.broadcastMessage(sensor);

                        }
                        catch (Exception e ) {
                        }
                    }
                }


                @Override
                // Called when an outgoing publish is complete
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("delivery complete " + token);
                }
            });


            client.subscribe("Sensor", 1);
        } catch (MqttException e) {

        }
    }
}