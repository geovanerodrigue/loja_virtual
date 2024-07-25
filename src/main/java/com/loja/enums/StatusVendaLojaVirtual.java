package com.loja.enums;

public enum StatusVendaLojaVirtual {
	
	FINALIZADA("Finalizada"),
	CANCELADA("Cancelada"),
	ABANDONOU_CARRINHO("Abandonou Carrinho");
	
	
	private String descricao = "";
	
	
	private StatusVendaLojaVirtual(String valor) {
		
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}

}
