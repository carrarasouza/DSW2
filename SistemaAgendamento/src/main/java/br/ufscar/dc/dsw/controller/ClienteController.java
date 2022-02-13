package br.ufscar.dc.dsw.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Consulta;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.security.ClienteDetails;
import br.ufscar.dc.dsw.service.impl.EmailService;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.IConsultaService;
import br.ufscar.dc.dsw.service.spec.IProfissionalService;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
    @Autowired
    private IClienteService daoC;

    @Autowired
    private IProfissionalService daoP;
    
    @Autowired
    private IConsultaService daoCon;
    
    @Autowired
    private EmailService mailSender;
    
    

    @GetMapping("/listarProfissional")
    public String listar(ModelMap model) {
        model.addAttribute("profissionais",daoP.buscarTodos());
        return "cliente/listarProfissional";
    }

    @GetMapping("/listarProfissionalEspecialidade/{especialidade}")
    public @ResponseBody List<Profissional> listarEspecialidade(@PathVariable("especialidade") String especialidade, ModelMap model) {
    	// model.addAttribute("profissionais", daoP.buscarPorEspecialidade(especialidade));
        // return "cliente/listarProfissionalEspecialidade";
        return daoP.buscarPorEspecialidade(especialidade);
    }

    @GetMapping("/cadastrarConsulta/{id}")
    public String preCadastrarConsulta(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("profissional", daoP.buscarPorId(id));
        return "cliente/cadastrarConsulta";
    }
    
    @GetMapping("/listarConsulta")
    public String listarConsulta(ModelMap model) {
    	Cliente cliente = this.getCliente();
    	model.addAttribute("consultas", daoCon.buscarTodos(cliente));
        return "cliente/listarConsulta";
    }
    
	private Cliente getCliente() {
		ClienteDetails clienteDetails = (ClienteDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return clienteDetails.getCliente();
	}

    @PostMapping("/salvarConsulta")
    public String salvarConsulta(@RequestParam("idProfissional") Long id, Consulta consulta, RedirectAttributes attr, ModelMap model) {
    	
    	if(consulta.getData() == null || consulta.getHorario() == null || consulta.getData()=="" || consulta.getHorario() =="") {
    		attr.addFlashAttribute("fail", "Data ou horário não informado!");
    		model.addAttribute("profissional", daoP.buscarPorId(id));
    		return "cliente/cadastrarConsulta";
    	}
    	String[] d = consulta.getData().split("-");
    	String data = d[2] + "-" + d[1] + "-" + d[0];
    	Cliente cliente = this.getCliente();
    	Profissional profissional = daoP.buscarPorId(id);
    	if(daoCon.buscarConsultaCliente(cliente, data, consulta.getHorario()) != null) {
    		attr.addFlashAttribute("fail", "Você já possui consulta marcada nesta data");
    		model.addAttribute("profissional", daoP.buscarPorId(id));
    		return "cliente/cadastrarConsulta";
    	}
    	if(daoCon.buscarConsultaProfisssional(profissional, data, consulta.getHorario()) != null) {
    		attr.addFlashAttribute("fail", "Profissional já possui consulta marcada nesta data");
    		model.addAttribute("profissional", daoP.buscarPorId(id));
    		return "cliente/cadastrarConsulta";
    	}
    	consulta.setCliente(cliente);
    	consulta.setProfissional(profissional);
    	consulta.setData(data);
    	consulta.setUrl("https://meet.google.com/vwy-wfwg-dfm");
    	daoCon.salvar(consulta);
    	
    	try {
    		InternetAddress from = new InternetAddress("dsw1ufscar@gmail.com", "Sistema Agendamento");
 		    InternetAddress to1 = new InternetAddress(cliente.getEmail(), cliente.getNome());
 		    InternetAddress to2 = new InternetAddress(profissional.getEmail(), profissional.getNome());
 		    
 		    String subject = "Agendamento de Consulta";
 		    String body1 =  "Consulta marcada com o profissional "+profissional.getNome()+" no dia "+data+
			        " às "+consulta.getHorario()+"\nLink da reunião: https://meet.google.com/vwy-wfwg-dfm";
 		   
 		    String body2 =  "Consulta marcada com o cliente "+cliente.getNome()+" no dia "+data+
					        " às "+consulta.getHorario()+"\nLink da reunião: https://meet.google.com/vwy-wfwg-dfm";
 		    mailSender.send(from, to1, subject, body1); 
			mailSender.send(from, to2, subject, body2);
			
    	}catch (Exception e) {
    		System.out.println("Erro no envio");
		}
    	
    	
    	return "redirect:/cliente/listarProfissional";
    }
}
