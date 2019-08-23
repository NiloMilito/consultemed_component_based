package br.com.consultemed.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

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

@ManagedBean
@Getter
@Setter
@ViewScoped
public class ConsultaController implements Serializable{
	
	private static final long serialVersionUID = 1L;

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
	private Map<String, Integer> comandos;
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
	
	  public void preparaCombo() {
		  comandos  = new HashMap<String, Integer>();
		  comandos.put("Buscar Por Periodo", 1);
		  comandos.put("Verifica se possui agendamento", 2);
		  comandos.put("Consultas em um Mês", 3);
		  comandos.put("Consultas de um paciente",4);
		  comandos.put("Paciente que mais cancelou consultas", 5);
	  }
	
	public void preparaConsulta(int comando) {	
		this.comando = comando;
		switch (this.comando) {
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
			this.isMes = true;
			this.isPacient = false;			
		}break;
		case 4:{
			this.isPeriodo = false;
			this.isAgendamento = false;
			this.isMes = false;
			this.isPacient = true;					
		}break;
		default:{
			this.consultarConsultas();
		}break;
		}
		
	}
	
	public void consultarConsultas() {
		switch (this.comando) {
			case 1:{
				this.consultas = this.cservice.buscarPorPeriodo(this.consultaDto.getInicio(), this.consultaDto.getFim());
				if(this.consultas == null || this.consultas.size() == 0) {	
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta","Não foram encontrandos resultados para sua pesquisa!");		         
				    PrimeFaces.current().dialog().showMessageDynamic(message);										
				}				
			}break;
			case 2:{			
				boolean isConsulta = this.cservice.podeFazerAgendamento(this.consultaDto.getData(), this.consultaDto.getMedico().getId());
				if(!isConsulta) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação","Não Existe Consulta para essa Data com esse Médico!");		         
				    PrimeFaces.current().dialog().showMessageDynamic(message);					
				} else {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta","Existe Consulta para essa Data com esse Médico!");		         
				    PrimeFaces.current().dialog().showMessageDynamic(message);										
				}	
			}break;
			case 3:{
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(this.consultaDto.getMes());			
				int mes = calendario.get(Calendar.MONTH) + 1;
				
				this.consultas = this.cservice.buscaConsultasNoMes(mes);
				if(this.consultas == null || this.consultas.size() == 0) {		
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta","Não foram encontrandos resultados para sua pesquisa!");		         
				    PrimeFaces.current().dialog().showMessageDynamic(message);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foram encontrandos resultados para sua pesquisa!", "Warning!"));					
				}	
			}break;
			case 4:{
				this.consultas = this.cservice.buscaConsultasPorPaciente(this.consultaDto.getPaciente().getId());
				if(this.consultas == null || this.consultas.size() == 0) {	
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta","Não foram encontrandos resultados para sua pesquisa!");		         
				    PrimeFaces.current().dialog().showMessageDynamic(message);									
				}	
			}break;
			default:{
				this.paciente = this.cservice.maisCancelouConsulta();	
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Paciente","Nome: "+this.paciente.getNome());		         
			    PrimeFaces.current().dialog().showMessageDynamic(message);			  			
			}break;
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
