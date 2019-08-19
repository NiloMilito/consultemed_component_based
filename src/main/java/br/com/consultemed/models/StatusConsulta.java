package br.com.consultemed.models;

public enum StatusConsulta {
	AGENDADA(0), REALIZADA(1), CANCELADA(2);
	
	private int valor;
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	private StatusConsulta(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}	

}
