package smartgreen.Controller;

import jakarta.websocket.*;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import smartgreen.Entity.SensorEncoder;
import smartgreen.Entity.SensorDecoder;
import smartgreen.Service.MqttConnection;

import java.io.*;
import java.util.*;

import jakarta.websocket.server.ServerEndpoint;
import smartgreen.Entity.Sensor;


import javax.net.ssl.SSLSocketFactory;

@ServerEndpoint(value = "/channel",
        encoders = {SensorEncoder.class},
        decoders = {SensorDecoder.class}
)
public class WebSocket {

    MqttClient client; // Persistence

    private static Session session;
    private static Set<Session> sessions = new HashSet<>();

    {
        try {
            System.out.println("MQTT started");

            //CLIENT CONNECTION OPTIONS
            MqttClient client = new MqttClient(
                    "wss://mqtt.greenhousecot.me:8083",
                    MqttClient.generateClientId(),
                    new MemoryPersistence());

            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setUserName("firas");
            mqttConnectOptions.setPassword("firas123".toCharArray());
            mqttConnectOptions.setSocketFactory(SSLSocketFactory.getDefault());

            client.connect(mqttConnectOptions);


        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void open(Session session) {
        MqttConnection connect = new MqttConnection();
        connect.start();

        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Sensor sensor, Session session) {
        System.out.println("Stock information received: " + sensor + " from " + session.getId());
        try {
            session.getBasicRemote().sendObject(sensor);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }


}




    public static void broadcastMessage(Sensor sensor) {
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendObject(sensor);

            } catch (IOException | EncodeException e) {

                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("WebSocket error for " + session.getId() + " " + throwable.getMessage());
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("WebSocket closed for " + session.getId()
                + " with reason " + closeReason.getCloseCode());
        sessions.remove(session);
    }
}



