package br.ufscar.dc.dsw.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.service.spec.IProfissionalService;

@Controller
@RequestMapping("/")
public class ListaController {

	@Autowired
	private IProfissionalService dao;
	
	
	@GetMapping("listar")
	public String listaProfissionais(ModelMap model) {
		model.addAttribute("profissionais", dao.buscarTodos());
		return "/listar";
	}
	
    @RequestMapping(value = "especialidade", method = RequestMethod.GET)
    public @ResponseBody List<String> listarPorEspecialidade(@RequestBody String especialidade){
    	List<Profissional> profissionais = dao.buscarPorEspecialidade(especialidade);
    	List<String> listaProfissional = new ArrayList<String>();
    	System.out.println("veio aqui #######################");
    	for(Profissional p : profissionais) {
    		listaProfissional.add(p.getId() + "-"+ p.getNome() + "-" + p.getEmail() + "-" + p.getEspecialidade() + "-" + p.getCurriculo());
    	}
    	return listaProfissional;
    }

}
