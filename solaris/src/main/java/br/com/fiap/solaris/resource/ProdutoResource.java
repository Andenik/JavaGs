package br.com.fiap.solaris.resource;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;


import br.com.fiap.solaris.dao.ProdutoDao;
import br.com.fiap.solaris.dto.AtualizacaoProdutoDto;
import br.com.fiap.solaris.dto.CadastroProdutoDto;
import br.com.fiap.solaris.dto.DetalhesProdutoDto;
import br.com.fiap.solaris.exception.EntidadeNaoEncontradaException;
import br.com.fiap.solaris.factory.Conexao;
import br.com.fiap.solaris.model.Produto;

import java.sql.SQLException;
import java.util.List;

@Path("produto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {
private ProdutoDao produtoDao;
	
	private ModelMapper modelMapper;
	
	public ProdutoResource() {
	    try {
	        this.produtoDao = new ProdutoDao(Conexao.getConexao());
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao conectar ao banco de dados", e);
	    }
	    this.modelMapper = new ModelMapper();
	}

	
	@POST
	public Response cadastrar(@Valid CadastroProdutoDto dto, @Context UriInfo uriInfo) throws SQLException {
		Produto produto = modelMapper.map(dto, Produto.class);
		produtoDao.inserirProduto(produto);
		UriBuilder uri = uriInfo.getAbsolutePathBuilder();
		uri.path(String.valueOf(produto.getCodigo()));
		return Response.created(uri.build()).entity(modelMapper.map(produto, CadastroProdutoDto.class)).build();
	}
	@GET
	public List<Produto> listar() throws SQLException {
		return produtoDao.listar();
	}
	
	@PUT
	@Path("{id}")
	public Response atualizar(@Valid AtualizacaoProdutoDto dto, @PathParam("id") int id) throws EntidadeNaoEncontradaException,SQLException {
		Produto produto = modelMapper.map(dto, Produto.class);
		produto.setCodigo(id);
		produtoDao.atualizarProduto(produto);
		return Response.ok().entity(modelMapper.map(produto, DetalhesProdutoDto.class)).build();
	}
	@DELETE
	@Path("{id}")
	public void remover(@PathParam("id")int id) throws EntidadeNaoEncontradaException,SQLException{
		produtoDao.remover(id);
	}
}

