package br.com.fiap.solaris.dto;

import br.com.fiap.solaris.model.Usuario;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class DetalhesEnderecoDto {
	private int codigo;
	private Usuario usuario;
	private String endereco;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;

}
