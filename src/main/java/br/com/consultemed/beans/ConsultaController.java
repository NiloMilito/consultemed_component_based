package br.com.consultemed.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.consultemed.dto.ConsultaDto;
import br.com.consultemed.models.Consulta;
import br.com.consultemed.models.Medico;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.services.ConsultaService;
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
	
	final static Logger logger = Logger.getLogger(ConsultaController.class);
		
	private IConsultaService cservice;		
	private MedicoService mservice;	
	private IPacienteService pservice;
	
	@Inject
	private Consulta consulta, filtro;		
	@Inject
	private Medico smedico;	
	@Inject
	private Paciente paciente;		
		
	private BeanFlash flash;
	private List<SelectItem> medicos, pacientes;
	private List<Consulta> consultas;	
	private Consulta consultaEditar;	
	private Date dataInicio, dataFim, data, mes;
	private Boolean isPeriodo, isAgendamento, isPacient, isMes;
	private ConsultaDto consultaDto;
	private Map<String, String> comandos;
	private int comando;
		
	@Inject
	public ConsultaController() throws Exception	{	
		this.mservice = new MedicoService();
		this.pservice = new PacienteService();
		this.cservice = new ConsultaService();
		this.flash = new BeanFlash();			
		this.consultas = new ArrayList<>();		
	    this.selectItemsMedicos();
	    this.selectItemsPacientes();
	    this.listaConsultas();
	    this.consultaDto = new ConsultaDto();	
	}	
	
	public void preparaConsulta(int comando) {	
		this.comando = comando;
		switch (comando) {
		case 1:{
			this.isPeriodo = true;
			this.isAgendamento = false;
			this.isPacient = false;
			this.isMes = false;
		}break;
		case 2:{
			this.isPeriodo = false;
			this.isAgendamento = true;
			this.isPacient = false;
			this.isMes = false;
		}break;
		case 3:{
			this.isPeriodo = false;
			this.isAgendamento = false;
			this.isPacient = false;
			this.isMes = true;
		}break;
		case 4:{
			this.isPeriodo = false;
			this.isAgendamento = false;
			this.isPacient = true;
			this.isMes = false;
		}break;
		default:{
			this.isPeriodo = false;
			this.isAgendamento = false;
			this.isPacient = false;
			this.isMes = false;
		}break;
		}
		
	}
	
	public String consultarConsultas() {
		switch (this.comando) {
			case 1:{
				this.consultas = this.cservice.buscarPorPeriodo(this.consultaDto.getInicio(), this.consultaDto.getFim());
				if(this.consultas != null && this.consultas.size() != 0) {
					return ("/pages/consultas/consultas.xhtml?faces-redirect=true");
				} else {
					return this.flash.redirectionAlerta("Não foram encontrandos resultados para sua pesquisa!", "/pages/consultas/consultas.xhtml?faces-redirect=true");
				}				
			}
			case 2:{			
				boolean isConsulta = this.cservice.podeFazerAgendamento(this.consultaDto.getData(), this.consultaDto.getMedico().getId());
				if(isConsulta) {
					return this.flash.redirectionAlerta("Existe Consulta nesta data para este médico!", "/pages/consultas/consultas.xhtml?faces-redirect=true");
				} else {
					return this.flash.redirectionAlerta("Não Existe Consulta nesta data para este médico!", "/pages/consultas/consultas.xhtml?faces-redirect=true");
				}	
			}
			case 3:{
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(this.consultaDto.getMes());			
				int mes = calendario.get(Calendar.MONTH);
				
				this.consultas = this.cservice.buscaConsultasNoMes(mes);
				if(this.consultas != null && this.consultas.size() != 0) {
					return ("/pages/consultas/consultas.xhtml?faces-redirect=true");
				} else {
					return this.flash.redirectionAlerta("Não foram encontrandos resultados para sua pesquisa!", "/pages/consultas/consultas.xhtml?faces-redirect=true");
				}	
			}
			case 4:{
				this.consultas = this.cservice.buscaConsultasPorPaciente(this.consultaDto.getPaciente().getId());
				if(this.consultas != null && this.consultas.size() != 0) {
					return ("/pages/consultas/consultas.xhtml?faces-redirect=true");
				} else {
					return this.flash.redirectionAlerta("Não foram encontrandos resultados para sua pesquisa!", "/pages/consultas/consultas.xhtml?faces-redirect=true");
				}	
			}
			default:{
				this.paciente = this.cservice.maisCancelouConsulta();
				this.flash.exibirPaciente();
				return ("/pages/consultas/consultas.xhtml?faces-redirect=true");
			}
		}
		
    }	
	
    private void selectItemsMedicos() {
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
		this.cservice.remover(this.consulta.getId());		
		return this.flash.redirectionAviso("Cadastrado com Sucesso!", "/pages/consultas/consultas.xhtml?faces-redirect=true");		
	}
	
	public String novaConsulta() {
		this.consulta = new Consulta();
		return "/pages/consultas/addConsultas.xhtml?faces-redirect=true";
	}
	
	public String addConsulta() throws Exception {																																																																													
		this.consulta.setMedico(this.smedico);
		this.consulta.setPaciente(this.paciente);
		if(this.cservice.podeFazerAgendamento(this.consulta.getDataConsulta(), this.consulta.getMedico().getId())) {																																												
			this.cservice.salvar(this.consulta);							
			return this.flash.redirectionAviso("Cadastrado com Sucesso!", "/pages/consultas/consultas.xhtml?faces-redirect=true");	
		} else {	
			return this.flash.redirectionAlerta("Já existe uma Consulta para essa data", "/pages/consultas/addConsultas.xhtml?faces-redirect=true");			
		}			
	}
	
	public String cancelarConsulta() {
		this.cservice.cancelarConsulta(this.consulta);			
		return this.flash.redirectionAviso("Cancelada com Sucesso!", "/pages/consultas/consultas.xhtml?faces-redirect=true");
	}
	
	public Collection<Consulta> listaConsultas() throws Exception{
		this.consultas = (List<Consulta>) this.cservice.listar();	
		return this.consultas;		
	}

}
