package smartgreen.Controller;



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
    @Path("/single/{id}")
    public User findById(@PathParam("id") Integer id) {
        return userService.findById(id);
    }


    @GET
    @Path("users")
    @RolesAllowed("ADMIN")
    public List<User> getUsers() {
        return userService.getUser() ;
    }
    @Path("delete/{id}")
    @DELETE
    @RolesAllowed("ADMIN")
    public void delete(@PathParam("id") Integer id) {
        userService.delete(id);
    }

}


