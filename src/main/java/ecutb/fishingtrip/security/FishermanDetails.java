package ecutb.fishingtrip.security;

import ecutb.fishingtrip.entity.Fisherman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class FishermanDetails implements UserDetails {

    private Fisherman fisherman;
    private Collection<GrantedAuthority> authorities;

    @Autowired
    public FishermanDetails(Fisherman fisherman, Collection<GrantedAuthority> authorities) {
        this.fisherman = fisherman;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return fisherman.getPassword();
    }

    @Override
    public String getUsername() {
        return fisherman.getUserName();
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
}
