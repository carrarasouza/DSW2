package br.ufscar.dc.dsw.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufscar.dc.dsw.domain.Profissional;

@SuppressWarnings("serial")
public class ProfissionalDetails implements UserDetails {

    private Profissional profissional;

    public ProfissionalDetails(Profissional profissional) {
        this.profissional = profissional;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(profissional.getPapel());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return profissional.getSenha();
    }

    @Override
    public String getUsername() {
        return profissional.getEmail();
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

    public Profissional getProfissional() {
        return profissional;
    }

}
