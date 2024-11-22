package br.com.fiap.solaris.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CadastroProdutoDto {
	@NotBlank
	@Size(max = 100)
	private String nome;
	@Size(max = 10)
	private String preco;
	@Size(max = 255)
	private String desc_produto;
	@Size(max = 255)
	private String img;

}
