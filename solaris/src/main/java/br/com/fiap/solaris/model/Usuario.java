package br.com.fiap.solaris.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Usuario {
	
	private Integer codigo;
	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	private String senha;
	
}
