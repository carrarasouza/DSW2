package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.domain.Consulta;

@SuppressWarnings("unchecked")
public interface IProfissionalDAO extends CrudRepository<Profissional, Long> {
    //Profissional findByConsulta(Consulta consulta);
    Profissional findById(long id);
    List<Profissional> findByEspecialidade(String especialidade);
    Profissional save(Profissional profissional);
    void deleteById(long id);
    List<Profissional> findAll();
    Profissional findByCPF(String CPF);

    @Query("SELECT p FROM Profissional p WHERE p.email = :email")
    public Profissional getProfissionalByEmail(@Param("email") String email);
    
    @Query("SELECT p FROM Profissional p WHERE p.especialidade LIKE :especialidade")
    public List<Profissional> getProfissionalByEspecialidade(@Param("especialidade") String especialidade);
    
}
