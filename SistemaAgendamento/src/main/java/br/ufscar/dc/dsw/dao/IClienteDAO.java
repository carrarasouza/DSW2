package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Consulta;

@SuppressWarnings("unchecked")
public interface IClienteDAO extends CrudRepository<Cliente, Long> {
    //Cliente findByConsulta(Consulta consulta);
    Cliente findById(long id);
    Cliente save(Cliente cliente);
    List<Cliente> findAll();
    void deleteById(long id);

    @Query("SELECT c FROM Cliente c WHERE c.email = :email")
    public Cliente getClienteByEmail(@Param("email") String email);
}
