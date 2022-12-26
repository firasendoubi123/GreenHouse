package smartgreen.Controller;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("")
@RequestScoped
public class HelloWorldController {

    public static void main(String[] args) {

        System.out.println("Hello, World!");

    }

    @GET
    @PermitAll
    @Produces("text/plain")
    public String everyOne() {

        return "Hello  everyone";
    }

}