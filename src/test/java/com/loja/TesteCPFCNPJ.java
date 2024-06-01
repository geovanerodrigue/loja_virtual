package com.loja;

import com.loja.util.ValidaCPF;
import com.loja.util.ValidaCnpj;

public class TesteCPFCNPJ {

	public static void main(String[] args) {
		
		boolean isCnpj =ValidaCnpj.isCNPJ(null);
		
		System.out.println("Cnpj válido: "+ isCnpj);
		
		boolean IsCpf = ValidaCPF.isCPF(null);
		
		System.out.println("Cpf válido: " + IsCpf);
		
	}
	
}
