package smartgreen.Controller;



import smartgreen.Entity.GreenHouse;
import smartgreen.Security.UserAlreadyExist;
import smartgreen.Entity.User;
import smartgreen.Service.UserService;


import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.*;
@ApplicationScoped
@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    private UserService userService ;
    @POST
    @Path("/signup")
    public void createUser(@Valid User user){

        userService.create(user);
    }
    @GET
    @Path("/single/{email}")
    public User findById(@PathParam("email") String email) {

        return userService.findById(email);
    }
    @GET
    @Path("/{username}/greenhouse")
    public Set<GreenHouse> getHouses(@PathParam("username") String username){
        return userService.findHouses(username);

    }

    @GET
    @Path("users")
    @RolesAllowed("ADMIN")
    public List<User> getUsers() {
        return userService.getUser() ;
    }
    @Path("delete/{email}")
    @DELETE
    @RolesAllowed("ADMIN")
    public void delete(@PathParam("email") String email) {
        userService.delete(email);
    }

}


