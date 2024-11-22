package br.com.fiap.solaris.dto;

import br.com.fiap.solaris.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class AtualizacaoEnderecoDto {
	@NotBlank
	@Size(max = 255)
	private String endereco;
	@Size(max = 10)
	private String numero;
	@Size(max = 255)
	private String complemento;
	@Size(max = 255)
	private String bairro;
	@Size(max = 255)
	private String cidade;
	@Size(max = 2)
	private String estado;
	@Size(max = 10)
	private String cep;
	
	private Integer usuarioId;

}
