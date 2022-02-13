package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Profissional;

public interface IProfissionalService {

    void salvar(Profissional profissional);

    void excluir(Long id);

    Profissional buscarPorId(Long id);
    Profissional buscaPorCPF(String CPF);

    List<Profissional> buscarTodos();

    boolean profissionalTemConsultas(Long id);
    List<Profissional> buscarPorEspecialidade(String especialidade);

}
