package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.domain.Consulta;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Profissional;

@SuppressWarnings("unchecked")
public interface IConsultaDAO extends CrudRepository<Consulta, Long> {

    List<Consulta> findAllByCliente(Cliente cliente);
    List<Consulta> findAllByProfissional(Profissional profissional);
    Consulta findById(long id);
    Consulta save(Consulta consulta);
    void deleteById(long id);
    
    @Query("SELECT c.cliente FROM Consulta c WHERE c.cliente = :cliente and c.data = :data and c.horario = :horario")
    public Cliente getConsultaByCliente(@Param("cliente") Cliente cliente, @Param("data") String data, @Param("horario") String horario);
    
    @Query("SELECT c.profissional FROM Consulta c WHERE c.profissional = :profissional and c.data = :data and c.horario = :horario")
    public Profissional getConsultaByProfissional(@Param("profissional") Profissional profissional, @Param("data") String data, @Param("horario") String horario);

}
