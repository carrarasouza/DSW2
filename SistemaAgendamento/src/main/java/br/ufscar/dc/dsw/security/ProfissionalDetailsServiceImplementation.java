package br.ufscar.dc.dsw.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.ufscar.dc.dsw.dao.IProfissionalDAO;
import br.ufscar.dc.dsw.domain.Profissional;
 
public class ProfissionalDetailsServiceImplementation implements UserDetailsService {
 
    @Autowired
    private IProfissionalDAO dao;
     
    @Override
    public UserDetails loadUserByUsername(String nome)
            throws UsernameNotFoundException {
        Profissional profissional = dao.getProfissionalByEmail(nome);
         
        if (profissional == null) {
            throw new UsernameNotFoundException("Profissional não foi encontrado.");
        }
         
        return new ProfissionalDetails(profissional);
    }
}
