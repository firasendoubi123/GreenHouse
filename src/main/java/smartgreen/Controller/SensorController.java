package smartgreen.Controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import smartgreen.Entity.SensorType;
import smartgreen.Entity.User;
import smartgreen.Service.SensorService;
import smartgreen.Entity.Sensor;
import java.util.*;
@ApplicationScoped
@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorController {
    private SensorService sensorservice;

    @POST
    @Path("/addsensor")
    public void createSensor(@Valid Sensor sensor){

        sensorservice.createsensor(sensor);
    }

    @GET
    @Path("/{houseId}/sensors")
    public List<Sensor> findByhouseId(@PathParam("houseId") Integer houseId){
        return sensorservice.findByHouseId(houseId);
    }
}
