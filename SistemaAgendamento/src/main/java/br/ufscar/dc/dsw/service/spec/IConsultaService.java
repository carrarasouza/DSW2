package br.ufscar.dc.dsw.service.spec;

import java.util.List;


import br.ufscar.dc.dsw.domain.Consulta;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Profissional;

public interface IConsultaService {

    void salvar(Consulta consulta);

    Consulta buscaPorId(Long id);

    List<Consulta> buscarTodos(Cliente c);

    List<Consulta> buscarTodos(Profissional p);
    
    Cliente buscarConsultaCliente(Cliente c, String data, String horario);
    
    Profissional buscarConsultaProfisssional(Profissional p, String data, String horario);
}

