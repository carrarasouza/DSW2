package br.ufscar.dc.dsw.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Consulta;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.security.ClienteDetails;
import br.ufscar.dc.dsw.security.ProfissionalDetails;
import br.ufscar.dc.dsw.service.spec.IConsultaService;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.IProfissionalService;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private IConsultaService service;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IProfissionalService profissionalService;

    @GetMapping("/cadastrar")
    public String cadastrar(Consulta consulta) {
        consulta.setCliente(this.getCliente());
        consulta.setProfissional(this.getProfissional());
        consulta.setData("15/06/2021");
        //compra.setValor(compra.getLivro().getPreco());
        return "consulta/cadastro";
    }

    private Cliente getCliente() {
        ClienteDetails clienteDetails = (ClienteDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clienteDetails.getCliente();
    }

    private Profissional getProfissional() {
        ProfissionalDetails profissionalDetails = (ProfissionalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return profissionalDetails.getProfissional();
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {

        model.addAttribute("consultas",service.buscarTodos(this.getCliente()));

        return "consulta/lista";
    }

    @PostMapping("/salvar")
    public String salvar(Consulta consulta, BindingResult result, RedirectAttributes attr) {

        String data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        consulta.setCliente(this.getCliente());
        consulta.setProfissional(this.getProfissional());
        consulta.setData(data);

        service.salvar(consulta);
        attr.addFlashAttribute("sucess", "Consulta inserida com sucesso.");
        return "redirect:/consulta/listar";
    }

    @ModelAttribute("clientes")
    public List<Cliente> listaClientes() {
        return clienteService.buscarTodos();
    }

    @ModelAttribute("profissionais")
    public List<Profissional> listaProfissionais() {
        return profissionalService.buscarTodos();
    }
}
