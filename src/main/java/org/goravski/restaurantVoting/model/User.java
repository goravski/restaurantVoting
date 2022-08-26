package org.goravski.restaurantVoting.model;

import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.*;

@ToString
public class User extends AbstractNamedEntity {
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Date registered = new Date();

    private Set<Role> roles;


    public User() {
    }

    public User(User u) {
        this(u.id, u.name, u.email, u.password, u.registered, u.roles);
    }

    public User(Integer id, String name, String email, String password, Role... roles) {
        this(id, name, email, password, new Date(), Arrays.asList((roles)));
    }

    public User(Integer id, String name, String email, String password, Date registered, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.registered = registered;
        setRoles(roles);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

}