package br.com.fiap.solaris.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetalhesProdutoDto {
	private String nome;
	private String preco;
	private int codigo;
	private String desc_produto;
	private String img;

}
