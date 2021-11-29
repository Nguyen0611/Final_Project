package fpt.toeic.config;

import fpt.toeic.service.dto.DsRolesDTO;
import fpt.toeic.service.dto.UsersDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private UsersDTO user;
    private String jwt;
    private List<DsRolesDTO> list;
    private List<String> role;


    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public List<DsRolesDTO> getList() {
        return list;
    }

    public void setList(List<DsRolesDTO> list) {
        this.list = list;
    }

    public CustomUserDetails(UsersDTO user, String jwt) {
        super();
        this.user = user;
        this.jwt = jwt;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // // Mặc định sẽ để tất cả là ROLE_USER. Để demo cho đơn giản.
        return Collections.singleton( new SimpleGrantedAuthority( "ROLE_USER" ) );
    }

    @Override
    public String getPassword() {
        return null;
    }


    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UsersDTO getUser() {
        return user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setUser(UsersDTO user) {
        this.user = user;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
