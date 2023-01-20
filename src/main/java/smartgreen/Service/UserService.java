package smartgreen.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.nosql.mapping.Database;
import jakarta.nosql.mapping.DatabaseType;
import jakarta.nosql.mapping.document.DocumentTemplate;
import smartgreen.Entity.Role;
import smartgreen.Entity.User;
import smartgreen.Entity.GreenHouse;

import smartgreen.Security.UserAlreadyExistException;
import smartgreen.Security.UserNotAuthorized;

import smartgreen.Repository.UserRepository;
import smartgreen.Repository.GreenHouseRepository;

import java.util.*;

@ApplicationScoped
public class  UserService {
    @Inject
    @Database(DatabaseType.DOCUMENT)
    private UserRepository repository;
    @Inject
    @Database(DatabaseType.DOCUMENT)
    private GreenHouseRepository HouseRepository;


    public User create(User user) {
        if (user.getRoles().contains(Role.USER)) {
            throw new UserNotAuthorized( "User is not Authorized");

        }

        if (repository.existsById(user.getEmail())) {
            throw new UserAlreadyExistException("There is an user with this id: " + user.getEmail());
        }
        else{
            User user1 = User.builder()
                    .withUserName(user.getUsername())
                    .withEmail(user.getEmail())
                    .withPassword(user.getPassword())
                    .withRoles(user.getRoles()).withTelephone(user.getTelephone()).build();
            return repository.save(user1);
        }
    }
    public List<User> getUser() {
        return repository.findAll();
    }

    public void delete(String email) {

        repository.deleteById(email);

    }
    public User findById( String email){
        return repository.findByUserId(email);
    }
    public Set<GreenHouse> findHouses(String username)  {
        return HouseRepository.findByUserName(username);
    }





}
