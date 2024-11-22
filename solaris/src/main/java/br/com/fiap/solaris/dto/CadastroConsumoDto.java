package br.com.fiap.solaris.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class CadastroConsumoDto {
	@NotBlank
	@Size(max = 10)
	private String consumoKw;
	
	private Integer usuarioId;
}
