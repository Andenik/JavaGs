package br.com.fiap.solaris.dto;

import br.com.fiap.solaris.model.Usuario;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class DetalhesConsumoDto {
	private int codigo;
	private Usuario usuario;
	private String consumoKw;
	private int usuarioId;
}
