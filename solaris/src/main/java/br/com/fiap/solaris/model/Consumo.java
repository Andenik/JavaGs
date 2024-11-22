package br.com.fiap.solaris.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Consumo {
	private int codigo;
	private Usuario usuario;
	private String consumoKw;	

}
