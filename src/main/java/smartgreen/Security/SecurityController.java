package smartgreen.Security;
import smartgreen.Entity.User;
import smartgreen.Entity.RoleDTO;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("security")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SecurityController {

    @Inject
    private SecurityService service;
    @Path("signup")
    @PermitAll
    @POST
    public void create(@Valid User user) {
        service.create(user);
    }




    @DELETE
    @Path("user/{id}")
    @RolesAllowed("ADMIN")
    public void delete(@PathParam("id") String id) {
        service.delete(id);
    }

    @Path("{id}")
    @PUT
    public void changePassword(@PathParam("id") String id, @Valid User dto) {
        service.updatePassword(id, dto);
    }

    @Path("roles/{id}")
    @PUT
    @RolesAllowed("ADMIN")
    public void addRole(@PathParam("id") String id, RoleDTO dto){
        service.addRole(id, dto);
    }

    @Path("roles/{id}")
    @DELETE
    @RolesAllowed("ADMIN")
    public void removeRole(@PathParam("id") String id, RoleDTO dto){
        service.removeRole(id, dto);
    }




    @DELETE
    @RolesAllowed("ADMIN")
    @Path("delete/{id}")
    public void removeUser(@PathParam("id") String id) {
        service.removeUser(id);
    }


    @DELETE
    @PermitAll
    @Path("token/{token}")
    public void removeToken(@PathParam("token") String token) {
        service.removeToken(token);
    }
}