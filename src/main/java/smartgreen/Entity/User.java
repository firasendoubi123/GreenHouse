package smartgreen.Entity;


import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
import java.util.HashSet;
import java.util.Set;
import smartgreen.Entity.Role;
@Entity
public class User  {
    @Id
    private int Id;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;

    @Column
    private String telephone;
    @Column
    private Set<Role> roles;

    public Integer getId() {

        return Id ;
    }
    public String getUsername() {

        return username;
    }
    public String getTelephone() {

        return telephone;
    }
    public String getEmail() {
        return email;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public String getPassword() {
        return password;
    }
    @Override
    public String toString() {
        return  "Id = " +Id +
                "user='" + username + ' ' +
                "email='" + email + ' ' +
                "Phone='" + telephone + ' ' +
                ", password='" + password + ' ' +
                ", roles=" + roles +
                '}';
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }
    public static class UserBuilder {
        private Integer id;
        private String username;

        private String email;


        private String password;
        private String telephone;
        private Set<Role> roles;


        private UserBuilder() {
        }
        public UserBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder withUserName(String username) {
            this.username = username;
            return this;
        }


        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withTelephone(String telephone) {
            this.telephone= telephone;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withRoles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }



        public User build() {
            User user = new User();
            user.Id = id;
            user.roles = roles;
            user.username = username;
            user.email = email;
            user.telephone = telephone;
            return user;
        }
    }



}
