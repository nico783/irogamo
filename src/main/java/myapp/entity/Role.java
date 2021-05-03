package myapp.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Embeddable;

@Embeddable
public class Role implements GrantedAuthority {

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Role{" +
                "authority='" + authority + '\'' +
                '}';
    }
}
