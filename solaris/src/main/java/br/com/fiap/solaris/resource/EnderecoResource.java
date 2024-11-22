package br.com.fiap.solaris.resource;

import java.sql.SQLException;
import java.util.List;

import org.modelmapper.ModelMapper;


import br.com.fiap.solaris.dao.EnderecoDao;
import br.com.fiap.solaris.dto.AtualizacaoEnderecoDto;
import br.com.fiap.solaris.dto.CadastroEnderecoDto;
import br.com.fiap.solaris.dto.DetalhesEnderecoDto;
import br.com.fiap.solaris.exception.EntidadeNaoEncontradaException;
import br.com.fiap.solaris.factory.Conexao;
import br.com.fiap.solaris.model.Endereco;
import br.com.fiap.solaris.model.Usuario;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
@Path("endereco")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {
private EnderecoDao enderecoDao;
	
	private ModelMapper modelMapper;
	
	public EnderecoResource() {
	    try {
	        this.enderecoDao = new EnderecoDao(Conexao.getConexao());
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao conectar ao banco de dados", e);
	    }
	    this.modelMapper = new ModelMapper();
	}

	
	@POST
	public Response cadastrar(@Valid CadastroEnderecoDto dto, @Context UriInfo uriInfo) throws SQLException {
        
        Usuario usuario = new Usuario();
        usuario.setCodigo(dto.getUsuarioId()); 

        Endereco endereco = new Endereco();
        endereco.setEndereco(dto.getEndereco());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());
        endereco.setUsuario(usuario);

        enderecoDao.inserirEndereco(usuario, endereco);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(String.valueOf(endereco.getCodigo()));

        return Response.created(uriBuilder.build()).entity(endereco).build();
        }
	
	@GET
	public List<Endereco> listar() throws SQLException {
		return enderecoDao.listar();
	}
	
	@PUT
	@Path("{id}")
	public Response atualizar(@Valid AtualizacaoEnderecoDto dto, @PathParam("id") int id) throws EntidadeNaoEncontradaException,SQLException {
		Endereco endereco = modelMapper.map(dto, Endereco.class);
		endereco.setCodigo(id);
		enderecoDao.atualizarEndereco(endereco);
		return Response.ok().entity(modelMapper.map(endereco, DetalhesEnderecoDto.class)).build();
	}
	@DELETE
	@Path("{id}")
	public void remover(@PathParam("id")int id) throws EntidadeNaoEncontradaException,SQLException{
		enderecoDao.remover(id);
	}


}
