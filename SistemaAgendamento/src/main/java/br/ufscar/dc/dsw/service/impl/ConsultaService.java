package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IConsultaDAO;
import br.ufscar.dc.dsw.domain.Consulta;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.service.spec.IConsultaService;

@Service
@Transactional(readOnly = false)
public class ConsultaService implements IConsultaService {

    @Autowired
    IConsultaDAO dao;

    public void salvar(Consulta consulta) {
        dao.save(consulta);
    }

    @Transactional(readOnly = true)
    public Consulta buscaPorId(Long id) {
        return dao.findById(id.longValue());
    }

    @Transactional(readOnly = true)
    public List<Consulta> buscarTodos(Cliente c) {
        return dao.findAllByCliente(c);
    }

    @Transactional(readOnly = true)
    public List<Consulta> buscarTodos(Profissional p) {
        return dao.findAllByProfissional(p);
    }
    
    @Transactional(readOnly = true)
    public Cliente buscarConsultaCliente(Cliente c, String data, String horario) {
    	return dao.getConsultaByCliente(c, data, horario);
    }
    
    @Transactional(readOnly = true)
    public Profissional buscarConsultaProfisssional(Profissional p, String data, String horario) {
    	return dao.getConsultaByProfissional(p, data, horario);
    }
}

