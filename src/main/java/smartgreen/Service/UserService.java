package smartgreen.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.nosql.mapping.Database;
import jakarta.nosql.mapping.DatabaseType;
import smartgreen.Entity.User;
import smartgreen.Security.UserAlreadyExist;
import smartgreen.Repository.UserRepository;
import java.util.*;

@ApplicationScoped
public class  UserService {

    private UserRepository repository;
    public void create(User user) {
        if (repository.existsById(user.getId())) {
            throw new UserAlreadyExist("There is an user with this id: " + user.getId());
        }
        else{
            User user1 = User.builder()
                    .withId(user.getId())
                    .withUserName(user.getUsername())
                    .withEmail(user.getEmail())
                    .withPassword(user.getPassword())
                    .withRoles(user.getRoles()).withTelephone(user.getTelephone()).build();
            repository.save(user1);
        }
    }
    public List<User> getUser() {
        return repository.findAll();
    }

    public void delete(Integer id) {

        repository.deleteById(id);

    }
    public User findById( Integer id){
        return repository.findByUserId(id);
    }





}
