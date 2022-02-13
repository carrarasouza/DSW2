package br.ufscar.dc.dsw.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.dao.IProfissionalDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Profissional;
 
public class ClienteDetailsServiceImplementation implements UserDetailsService {
 
    @Autowired
    private IClienteDAO dao;
    
    @Autowired
    private IProfissionalDAO daoP;
     
    @Override
    public UserDetails loadUserByUsername(String nome)
            throws UsernameNotFoundException {
        Cliente cliente = dao.getClienteByEmail(nome);
        if (cliente == null) {
        	
        	Profissional profissional = daoP.getProfissionalByEmail(nome);
        	if (profissional == null)
        		throw new UsernameNotFoundException("Usuário não foi encontrado.");
        	else
        		return new ProfissionalDetails(profissional);
        }
        else 
        	return new ClienteDetails(cliente);
    }
}
