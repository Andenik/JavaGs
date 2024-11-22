package br.com.fiap.solaris.resource;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import br.com.fiap.solaris.dao.ConsumoDao;
import br.com.fiap.solaris.dto.AtualizacaoConsumoDto;
import br.com.fiap.solaris.dto.CadastroConsumoDto;
import br.com.fiap.solaris.dto.DetalhesConsumoDto;
import br.com.fiap.solaris.exception.EntidadeNaoEncontradaException;
import br.com.fiap.solaris.factory.Conexao;
import br.com.fiap.solaris.model.Consumo;
import br.com.fiap.solaris.model.Usuario;

import java.sql.SQLException;
import java.util.List;
@Path("consumo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConsumoResource {
private ConsumoDao consumoDao;
	
	private ModelMapper modelMapper;
	
	public ConsumoResource() {
	    try {
	        this.consumoDao = new ConsumoDao(Conexao.getConexao());
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao conectar ao banco de dados", e);
	    }
	    this.modelMapper = new ModelMapper();
	}

	
	@POST
	public Response cadastrar(@Valid CadastroConsumoDto dto, @Context UriInfo uriInfo) throws SQLException {
        
        Usuario usuario = new Usuario();
        usuario.setCodigo(dto.getUsuarioId()); 

        Consumo consumo = new Consumo();
        consumo.setConsumoKw(dto.getConsumoKw());
        consumo.setUsuario(usuario);

        consumoDao.inserirConsumo(usuario, consumo);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(String.valueOf(consumo.getCodigo()));

        return Response.created(uriBuilder.build()).entity(consumo).build();
        }
	
	@GET
	public List<Consumo> listar() throws SQLException {
		return consumoDao.listar();
	}
	

	
	@PUT
	@Path("{id}")
	public Response atualizar(@Valid AtualizacaoConsumoDto dto, @PathParam("id") int id) throws EntidadeNaoEncontradaException,SQLException {
		Consumo consumo = modelMapper.map(dto, Consumo.class);
		consumo.setCodigo(id);
		consumoDao.atualizarConsumo(consumo);
		return Response.ok().entity(modelMapper.map(consumo, DetalhesConsumoDto.class)).build();
	}
	@DELETE
	@Path("{id}")
	public void remover(@PathParam("id")int id) throws EntidadeNaoEncontradaException,SQLException{
		consumoDao.remover(id);
	}

}
