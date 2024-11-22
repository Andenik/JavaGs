package br.com.fiap.solaris.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.solaris.exception.EntidadeNaoEncontradaException;
import br.com.fiap.solaris.model.Endereco;
import br.com.fiap.solaris.model.Usuario;

public class EnderecoDao {
	
	private static final String SQL_INSERT = "insert into gs_endereco (cd_usuario, endereco, numero, complemento, bairro, cidade, estado, cep) values (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_SELECT_ALL = "select * from gs_endereco";
	private static final String SQL_SELECT_BY_ID = "select * from gs_endereco where cd_endereco = ?";
	private static final String SQL_UPDATE = "update gs_endereco set endereco = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, cep = ? where cd_endereco = ?";
	private static final String SQL_DELETE = "delete from gs_endereco where cd_endereco = ?";
	
	private Connection connection;

    public EnderecoDao(Connection connection){
        this.connection = connection;
    }
    
    public void inserirEndereco(Usuario usuario, Endereco endereco) throws SQLException {
        
        try (PreparedStatement stm = connection.prepareStatement(SQL_INSERT)) {
            
            stm.setInt(1, usuario.getCodigo());
            stm.setString(2, endereco.getEndereco());
            stm.setString(3, endereco.getNumero());
            stm.setString(4, endereco.getComplemento());
            stm.setString(5, endereco.getBairro());
            stm.setString(6, endereco.getCidade());
            stm.setString(7, endereco.getEstado());
            stm.setString(8, endereco.getCep());          
            stm.executeUpdate();
           
            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                	endereco.setCodigo(generatedKeys.getInt(1)); 
                }
            }
        }
    }

    
    public void atualizarEndereco(Endereco endereco) throws SQLException,EntidadeNaoEncontradaException{
        try (PreparedStatement stm = connection.prepareStatement(SQL_UPDATE)){
        	stm.setString(1, endereco.getEndereco());
            stm.setString(2, endereco.getNumero());
            stm.setString(3, endereco.getComplemento());
            stm.setString(4, endereco.getBairro());
            stm.setString(5, endereco.getCidade());
            stm.setString(6, endereco.getEstado());
            stm.setString(7, endereco.getCep());
			stm.setInt(8, endereco.getCodigo());
			stm.executeUpdate();
            if (stm.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("Conta não encontrada para ser atualizada");
            
        }
	}
    
    public List<Endereco> listar() throws SQLException {
        try (PreparedStatement stm= connection.prepareStatement(SQL_SELECT_ALL)){
            List<Endereco> lista = new ArrayList<>();
            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                	Endereco endereco = new Endereco();
                	endereco.setCodigo(resultSet.getInt("cd_endereco"));
                	endereco.setEndereco(resultSet.getString("endereco"));
                	endereco.setNumero(resultSet.getString("numero"));
                	endereco.setComplemento(resultSet.getString("complemento"));
                	endereco.setBairro(resultSet.getString("bairro"));
                	endereco.setCidade(resultSet.getString("cidade"));
                	endereco.setEstado(resultSet.getString("estado"));
                	endereco.setCep(resultSet.getString("cep"));
                	Usuario usuario = new Usuario();
                    usuario.setCodigo(resultSet.getInt("cd_usuario")); 
                    endereco.setUsuario(usuario);
                    lista.add(endereco);
                }
            }
            return lista;
        }
    }
    
    public Endereco pesquisarPorId(Integer id) throws SQLException, EntidadeNaoEncontradaException {
        try (PreparedStatement stm = connection.prepareStatement(SQL_SELECT_BY_ID)){
            stm.setInt(1, id);
            try (ResultSet resultSet = stm.executeQuery()) {
                if (resultSet.next()) {
                	Endereco endereco = new Endereco();
                	endereco.setCodigo(resultSet.getInt("cd_endereco"));              
                	endereco.setEndereco(resultSet.getString("endereco"));
                	endereco.setNumero(resultSet.getString("numero"));
                	endereco.setComplemento(resultSet.getString("complemento"));
                	endereco.setBairro(resultSet.getString("bairro"));
                	endereco.setCidade(resultSet.getString("cidade"));
                	endereco.setEstado(resultSet.getString("estado"));
                	endereco.setCep(resultSet.getString("cep"));
                	Usuario usuario = new Usuario();
                    usuario.setCodigo(resultSet.getInt("cd_usuario")); 
                    endereco.setUsuario(usuario);
                    return endereco;
                } else{
                    throw new EntidadeNaoEncontradaException("conta não encontrado");
                }
            }
        }
    }
    
    public void remover(Integer id) throws SQLException, EntidadeNaoEncontradaException {
        try (PreparedStatement stm = connection.prepareStatement(SQL_DELETE)){
            stm.setInt(1, id);
            if (stm.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("conta não encontrada para ser removido");
        }
    }



}
