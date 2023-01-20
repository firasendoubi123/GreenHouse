package smartgreen.Controller;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.nosql.mapping.document.DocumentTemplate;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import smartgreen.Entity.User;
import smartgreen.Entity.Role;
import smartgreen.Service.UserService;
import java.util.*;
@Path("")
@RequestScoped
public class HelloworldController {

    public static void main(String[] args) {


        System.out.println("Hello firass World!");

    }
    @Path("admin")
    @GET
    @Produces("text/plain")
    public String admin() {
        return "hello from admin";
    }
    @Path("every")
    @GET
    @PermitAll
    @Produces("text/plain")
    public String everyOne() {

        return "Hello  everyone";
    }
}

