package smartgreen.Security;

public class UserNotFound  extends RuntimeException {

    public UserNotFound(String id) {
        super("User does not found with email: " + id);
    }
}
