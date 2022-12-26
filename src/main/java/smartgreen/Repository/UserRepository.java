package smartgreen.Repository;

import smartgreen.Entity.User;
import jakarta.nosql.mapping.Repository;
import java.util.*;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped

public interface UserRepository  extends Repository<User, Integer> {

    User findByUserId(Integer Id);
    List<User> findAll();

}
