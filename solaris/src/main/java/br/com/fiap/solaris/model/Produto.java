package br.com.fiap.solaris.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Produto {

		private String nome;
		private String preco;
		private int codigo;
		private String desc_produto;
		private String img;

}
