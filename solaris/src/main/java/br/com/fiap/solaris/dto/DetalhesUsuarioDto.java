package br.com.fiap.solaris.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetalhesUsuarioDto {
	private Integer codigo;
	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	private String senha;

}
