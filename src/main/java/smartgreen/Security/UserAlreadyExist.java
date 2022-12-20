package smartgreen.Security;

public class UserAlreadyExist extends  RuntimeException{
    String message ;
    public UserAlreadyExist(String msg) {
        super(msg);
        this.message=msg ;
    }

}
