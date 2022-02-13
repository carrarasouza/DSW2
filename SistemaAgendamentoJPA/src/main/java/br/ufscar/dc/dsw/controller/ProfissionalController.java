package br.ufscar.dc.dsw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.security.ClienteDetails;
import br.ufscar.dc.dsw.security.ProfissionalDetails;
import br.ufscar.dc.dsw.service.spec.IConsultaService;
import br.ufscar.dc.dsw.service.spec.IProfissionalService;

@Controller
@RequestMapping("/profissional")
public class ProfissionalController {

    @Autowired
    private IProfissionalService service;
    
    @Autowired
    private IConsultaService daoCon;


    @GetMapping("/listarConsulta")
    public String listarConsulta(ModelMap model) {
    	Profissional profissional = this.getProfissional();
    	model.addAttribute("consultas", daoCon.buscarTodos(profissional));
        return "profissional/listarConsulta";
    }


    
	private Profissional getProfissional() {
		ProfissionalDetails profissionalDetails = (ProfissionalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return profissionalDetails.getProfissional();
	}

}
