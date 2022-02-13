package br.ufscar.dc.dsw.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.IProfissionalService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private IClienteService daoC;
	
	@Autowired
	private IProfissionalService daoP;
	
	@Autowired
	ServletContext context;
	
	@GetMapping("/listarCliente")
	public String listaCliente(ModelMap model) {
		model.addAttribute("clientes", daoC.buscarTodos());
		return "admin/listarCliente";
	}
	
	@GetMapping("/listarProfissional")
	public String listaProfissional(ModelMap model) {
		model.addAttribute("profissionais", daoP.buscarTodos());
		return "admin/listarProfissional";
	}
	
	@GetMapping("/cadastrarCliente")
	public String cadastrarCliente(Cliente cliente) {
		return "admin/cadastrarCliente";
	}
	
	@GetMapping("/cadastrarProfissional")
	public String cadastrarProfissional(Profissional profissional) {
		return "admin/cadastrarProfissional";
	}
	
	@GetMapping("/excluirCliente/{id}")
	public String excluirCliente(@PathVariable("id") Long id, ModelMap model) {
		if(daoC.clienteTemConsultas(id))
			model.addAttribute("fail", "Cliente não excluído. Possui consulta(s) vinculada(s).");
		else {
			daoC.excluir(id);
			model.addAttribute("sucess", "Cliente excluído com sucesso.");
		}
		return listaCliente(model);
	}
	
	@GetMapping("/excluirProfissional/{id}")
	public String excluirProfissional(@PathVariable("id") Long id, ModelMap model) {
		if (daoP.profissionalTemConsultas(id))
			model.addAttribute("fail", "Profissional não excluído. Possui consulta(s) vinculada(s).");
		else {
			daoP.excluir(id);
			String FilePath = context.getRealPath("") + File.separator + "upload" + File.separator + "curriculo_"+ Long.toString(id);
			File file = new File(FilePath);
			file.delete();
			model.addAttribute("sucess", "Profissional excluído com sucesso.");
		}
		return listaProfissional(model);
	}
	
	@PostMapping("/salvarCliente")
	public String salvarCliente(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors())
			return "admin/cadastrarCliente";
		else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			cliente.setSenha(encoder.encode(cliente.getSenha()));
			daoC.salvar(cliente);
			attr.addFlashAttribute("sucess", "Cliente adicionado com sucesso.");
			return "redirect:/admin/listarCliente";
		}
	}
	
	@PostMapping("/salvarProfissional")
	public String salvaProfissional(@RequestParam("file") MultipartFile file, @Valid Profissional profissional, BindingResult result,RedirectAttributes attr)
			throws IOException{
		if (result.hasErrors())
			return "admin/cadastrarProfissional";
		else {
			if(file != null) {
				try {
					String fileName = "curriculo_id_"+profissional.getId();
					String uploadPath = context.getRealPath("") + File.separator + "upload";
					File uploadDir = new File(uploadPath);
					if (!uploadDir.exists()) {
						uploadDir.mkdir();
					}
					
					file.transferTo(new File(uploadDir, fileName));
				
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			else {
				Profissional aux = daoP.buscaPorCPF(profissional.getCPF());
				if(aux.getCurriculo() == null || aux.getCurriculo() == "") {
					attr.addFlashAttribute("fail", "Currículo do profissional não adicionado.");
					return "admin/cadastrarProfissional";
				}
				else {
					profissional.setCurriculo(aux.getCurriculo());
				}
			}
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		profissional.setSenha(encoder.encode(profissional.getSenha()));
		daoP.salvar(profissional);
		if (profissional.getCurriculo() == null || profissional.getCurriculo() == "") {
			Profissional profissional2 = daoP.buscaPorCPF(profissional.getCPF());
			profissional.setCurriculo("curriculo_id_"+profissional2.getId());
			daoP.salvar(profissional);
		}
		else
			daoP.salvar(profissional);
		attr.addFlashAttribute("sucess", "Profissional adicionado com sucesso.");
		return "redirect:/admin/listarProfissional";
	}
	
	@GetMapping("/editarCliente/{id}")
	public String preEditarCliente(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cliente", daoC.buscarPorId(id));
		return "admin/cadastrarCliente";
	}
	
	@GetMapping("/editarProfissional/{id}")
	public String preEditarProfissional(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("profissional", daoP.buscarPorId(id));
		return "admin/cadastrarProfissional";
	}
	
	
	@PostMapping("/editarCliente")
	public String editarCliente(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors())
			return "admin/cadastrarCliente";
		daoC.salvar(cliente);
		attr.addFlashAttribute("sucess", "Cliente editado com sucesso");
		return "redirect:/admin/listarCliente";
	}
	
	@PostMapping("/editarProfissional")
	public String editarProfissional(@RequestParam("file") MultipartFile file, @Valid Profissional profissional, BindingResult result, RedirectAttributes attr) 
		throws IOException{
		
		if (result.hasErrors())
			return "admin/cadastrarProfissional";
		else {
			if(file != null) {
				try {
					String fileName = "curriculo_id_"+profissional.getId();
					String uploadPath = context.getRealPath("") + File.separator + "upload";
					File uploadDir = new File(uploadPath);
					if (!uploadDir.exists()) {
						uploadDir.mkdir();
					}
					
					file.transferTo(new File(uploadDir, fileName));
				
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			else {
				Profissional aux = daoP.buscaPorCPF(profissional.getCPF());
				if(aux.getCurriculo() == null || aux.getCurriculo() == "") {
					attr.addFlashAttribute("fail", "Currículo do profissional não adicionado.");
					return "admin/cadastrarProfissional";
				}
				else {
					profissional.setCurriculo(aux.getCurriculo());
				}
			}
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		profissional.setSenha(encoder.encode(profissional.getSenha()));
		daoP.salvar(profissional);
		if (profissional.getCurriculo() == null || profissional.getCurriculo() == "") {
			Profissional profissional2 = daoP.buscaPorCPF(profissional.getCPF());
			profissional.setCurriculo("curriculo_id_"+profissional2.getId());
			daoP.salvar(profissional);
		}
		else
			daoP.salvar(profissional);
		attr.addFlashAttribute("sucess", "Profissional editado com sucesso.");
		return "redirect:/admin/listarProfissional";
	}
	
}
