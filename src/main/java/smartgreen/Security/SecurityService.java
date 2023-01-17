package smartgreen.Security;



import smartgreen.Entity.RoleDTO;
import smartgreen.Entity.User;
import smartgreen.Repository.UserRepository;
import smartgreen.Repository.UserTokenRepository;
import smartgreen.Entity.Role;
import smartgreen.Security.Oauth.OauthRequest;
import smartgreen.Security.Oauth.UserToken;
import smartgreen.Security.Oauth.RemoveToken;
import smartgreen.Security.UserAlreadyExist;
import smartgreen.Security.UserNotFound;



import jakarta.nosql.mapping.Database;
import jakarta.nosql.mapping.DatabaseType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.*;


@ApplicationScoped
public class SecurityService {

    @Inject
    @Database(DatabaseType.DOCUMENT)
    private UserRepository repository;

    @Inject
    private UserTokenRepository token_repository;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private Validator validator;

    @Inject
    private SecurityContext securityContext;



    @Inject
    private Event<RemoveToken> removeTokenEvent;


    public void create(User userDTO) {
        if (repository.existsById(userDTO.getEmail())) {
            throw new UserAlreadyExist("There is an user with this id: " + userDTO.getEmail());
        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            User user = User.builder()
                    .withUserName(userDTO.getUsername())
                    .withPassword(userDTO.getPassword())
                    .withEmail(userDTO.getEmail())
                    .withTelephone(userDTO.getTelephone())
                    .withRoles(getRole())
                    .build();
            repository.save(user);
        }
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public void updatePassword(String id, User dto) {

        final Principal principal = securityContext.getCallerPrincipal();
        if (isForbidden(id, securityContext, principal)) {
            throw new UserForbidden();
        }

        final User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));
        user.updatePassword(dto.getPassword(), passwordHash);
        repository.save(user);
    }


    public void addRole(String id, Role dto) {
        final User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));

        user.add(dto.getRole());
        repository.save(user);

    }


    public void removeRole(String id, RoleDTO dto) {
        final User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));
        user.remove(dto.getRoles());
        repository.save(user);
    }

    public User getUser() {
        final User user = getLoggedUser();
        User dto = toDTO(user);
        return dto;
    }

    public List<User> getUsers() {
        return repository.findAll()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    public int numberUsers(){
        return getUsers().size();
    }



    public User findBy(String username, String password) {
        final User user = repository.findById(username)
                .orElseThrow(() -> new UserNotAuthorized("User not authorized"));

        if (passwordHash.verify(password.toCharArray(), user.getPassword())) {
            return user;
        }
        throw new UserNotAuthorized("User not authorized");

    }

    public User findBy(String username) {
        return repository.findById(username)
                .orElseThrow(() -> new UserNotAuthorized("User not authorized"));
    }

    public void removeUser(String userId) {
        final User user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));
        repository.deleteById(user.getEmail());

    }

    public void removeToken(String token) {
        final User loggedUser = getLoggedUser();
        RemoveToken removeToken = new RemoveToken(loggedUser, token);
        removeTokenEvent.fire(removeToken);
    }

    private User getLoggedUser() {
        final Principal principal = securityContext.getCallerPrincipal();
        if (principal == null) {
            throw new UserNotAuthorized("User not authorized");
        }
        return repository.findById(principal.getName())
                .orElseThrow(() -> new UserNotFound(principal.getName()));
    }

    private User toDTO(User user) {
        User dto = new User();
        dto.setEmail(user.getEmail());
        dto.setTelephone(user.getTelephone());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles());
        return dto;
    }

    private Set<Role> getRole() {
        if (repository.count() == 0) {
            return Collections.singleton(Role.ADMIN);
        } else {
            return Collections.singleton(Role.USER);
        }
    }

    private boolean isForbidden(String id, SecurityContext context, Principal principal) {
        return !(context.isCallerInRole(Role.ADMIN.name()) || id.equals(principal.getName()));
    }


}