package br.com.fiap.solaris.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CadastroUsuarioDto {
	@NotBlank
	@Size(max = 255)
	private String nome;
	@Size(max = 255)
	private String email;
	@Size(max = 11)
	private String cpf;
	@Size(max = 11)
	private String telefone;
	@Size(max = 32)
	private String senha;
}
