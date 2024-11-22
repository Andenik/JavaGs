package br.com.fiap.solaris.resource;

import java.sql.SQLException;
import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.fiap.solaris.dao.UsuarioDao;
import br.com.fiap.solaris.dto.AtualizacaoUsuarioDto;
import br.com.fiap.solaris.dto.CadastroUsuarioDto;
import br.com.fiap.solaris.dto.DetalhesUsuarioDto;
import br.com.fiap.solaris.exception.EntidadeNaoEncontradaException;
import br.com.fiap.solaris.factory.Conexao;
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

@Path("usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
private UsuarioDao usuarioDao;
	
	private ModelMapper modelMapper;
	
	public UsuarioResource() {
	    try {
	        this.usuarioDao = new UsuarioDao(Conexao.getConexao());
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao conectar ao banco de dados", e);
	    }
	    this.modelMapper = new ModelMapper();
	}

	
	@POST
	public Response cadastrar(@Valid CadastroUsuarioDto dto, @Context UriInfo uriInfo) throws SQLException {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		usuarioDao.inserirUsuario(usuario);
		UriBuilder uri = uriInfo.getAbsolutePathBuilder();
		uri.path(String.valueOf(usuario.getCodigo()));
		return Response.created(uri.build()).entity(modelMapper.map(usuario, DetalhesUsuarioDto.class)).build();
	}
	
	@GET
	public List<Usuario> listar() throws SQLException {
		return usuarioDao.listar();
	}
	
	@PUT
	@Path("{id}")
	public Response atualizar(@Valid AtualizacaoUsuarioDto dto, @PathParam("id") int id) throws EntidadeNaoEncontradaException,SQLException {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		usuario.setCodigo(id);
		usuarioDao.atualizarUsuario(usuario);
		return Response.ok().entity(modelMapper.map(usuario, DetalhesUsuarioDto.class)).build();
	}
	@DELETE
	@Path("{id}")
	public void remover(@PathParam("id")int id) throws EntidadeNaoEncontradaException,SQLException{
		usuarioDao.remover(id);
	}
	

}
