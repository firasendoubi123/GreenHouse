package smartgreen.Entity;


import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.json.bind.annotation.JsonbVisibility;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import smartgreen.Entity.Role;
@Entity

public class User  {

    @Column("username")
    private String username;
    @Id("email")
    private String email;
    @Column("password")
    private String password;

    @Column("telephone")
    private String telephone;
    @Column("roles")
    private Set<Role> roles;
    @Column("houses")

    private Set<GreenHouse> houses;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setHouses(Set<GreenHouse> houses) {
        this.houses = houses;
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
    public Set<GreenHouse> getHouses() {
        return houses;
    }
    public String getPassword() {
        return password;
    }
    public void add(Set<Role> roles) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.addAll(roles);
    }

    public void remove(Set<Role> roles) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.removeAll(roles);
    }
    public void addHouse(Set<GreenHouse> houses) {
        if (this.houses == null) {
            this.houses = new HashSet<>();
        }
        this.houses.addAll(houses);
    }

    public void updatePassword(String password, Pbkdf2PasswordHash passwordHash) {
        this.password = passwordHash.generate(password.toCharArray());
    }

    @Override
    public String toString() {
        return
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
        private String username;

        private String email;


        private String password;
        private String telephone;
        private Set<Role> roles;

        private Set<GreenHouse> houses;






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
        public UserBuilder withHouses(Set<GreenHouse> houses) {
            this.houses = houses;
            return this;
        }




        public User build() {
            User user = new User();
            user.roles = roles;
            user.username = username;
            user.email = email;
            user.telephone = telephone;
            user.houses = houses;
            return user;
        }
    }



}
