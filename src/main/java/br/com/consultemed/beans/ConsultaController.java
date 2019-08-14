package br.com.consultemed.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultemed.models.Consulta;
import br.com.consultemed.models.Medico;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.services.IConsultaService;
import br.com.consultemed.services.IPacienteService;
import br.com.consultemed.services.MedicoService;
import br.com.consultemed.services.PacienteService;
import lombok.Getter;
import lombok.Setter;

@Named
@Getter
@Setter
@RequestScoped
public class ConsultaController {
	@Inject
	private Consulta consulta;
	
	private Consulta consultaEditar;	
	
	@Inject
	private IConsultaService service;	
	
	@Inject
	private MedicoService mservice;
	
	@Inject
	private IPacienteService pservice;
	
	private BeanFlash flash = new BeanFlash();
	
	private List<SelectItem> medicos;
	private List<SelectItem> pacientes;
	
	@Inject
	private Medico smedico;
	
	@Inject
	private Paciente paciente;
	
	private Date dataAtual = new Date();
	
	@Inject
	public ConsultaController() throws Exception	{	
		this.mservice = new MedicoService();
		this.pservice = new PacienteService();
	    fillSelectItems();
	    selectItemsPacientes();
	}	

    private void fillSelectItems() {
        this.medicos = new ArrayList<SelectItem>();
        for (Medico foo : this.mservice.listaMedico()) {
            this.medicos.add(new SelectItem(foo, foo.getNome()));
        }
    }

    private void selectItemsPacientes() throws Exception {
        this.pacientes = new ArrayList<SelectItem>();
        for (Paciente foo : this.pservice.listar()) {
            this.pacientes.add(new SelectItem(foo, foo.getNome()));
        }
    }	
	
	public String editar() {
		this.consulta = this.consultaEditar;
		return "/pages/consultas/addConsultas.xhtml";
	}
	
	public String excluir() throws Exception {
		this.consulta = this.consultaEditar;
		this.service.remover(this.consulta.getId());		
		return this.flash.redirectionConsulta("Cadastrado com Sucesso!", "/pages/consultas/consultas.xhtml?faces-redirect=true");		
	}
	
	public String novaConsulta() {
		this.consulta = new Consulta();
		return "/pages/consultas/addConsultas.xhtml?faces-redirect=true";
	}
	
	public String addConsulta() throws Exception {		
		this.consulta.setMedico(this.smedico);
		this.consulta.setPaciente(this.paciente);
		if(this.service.podeFazerAgendamento(this.consulta)) {
			this.service.salvar(this.consulta);							
			return this.flash.redirectionConsulta("Cadastrado com Sucesso!", "/pages/consultas/consultas.xhtml?faces-redirect=true");	
		} else {	
			return this.flash.redirectionConsulta("Já existe uma Consulta para essa data", "/pages/consultas/addConsultas.xhtml?faces-redirect=true");			
		}			
	}
	
	public Collection<Consulta> listaConsultas() throws Exception{
		return this.service.listar();		
	}

}
