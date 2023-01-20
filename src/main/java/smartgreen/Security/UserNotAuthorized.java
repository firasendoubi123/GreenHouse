package smartgreen.Security;

public class UserNotAuthorized  extends  RuntimeException {
        String message  ;
        public  UserNotAuthorized  (String msg){
            super(msg);
            this.message=msg;
        }

    }

