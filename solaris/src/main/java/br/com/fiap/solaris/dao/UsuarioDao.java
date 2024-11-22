package br.com.fiap.solaris.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import br.com.fiap.solaris.exception.EntidadeNaoEncontradaException;
import br.com.fiap.solaris.model.Usuario;


public class UsuarioDao {
	
	private static final String SQL_INSERT = "insert into gs_usuario (nome, email, senha, cpf, telefone) values (?, ?, ?, ?, ?)";
	private static final String SQL_SELECT_ALL = "select * from gs_usuario";
	private static final String SQL_SELECT_BY_ID = "select * from gs_usuario where cd_usuario = ?";
	private static final String SQL_UPDATE = "update gs_usuario set nome = ?, email = ?, senha = ?, cpf = ?, telefone = ? where cd_usuario = ?";
	private static final String SQL_DELETE = "delete from gs_usuario where cd_usuario = ?";
	
	private Connection connection;

    public UsuarioDao(Connection connection){
        this.connection = connection;
    }
    
    public void inserirUsuario(Usuario usuario) throws SQLException {
        try (PreparedStatement stm = connection.prepareStatement(SQL_INSERT, new String[] {"cd_usuario"})) {
            
            stm.setString(1, usuario.getNome());
            stm.setString(2, usuario.getEmail());
            stm.setString(3, usuario.getSenha());
            stm.setString(4, usuario.getCpf());
            stm.setString(5, usuario.getTelefone());         
      
            stm.executeUpdate();
                     
            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setCodigo(generatedKeys.getInt(1)); 
                }
            }
        }
    }
    
    public void atualizarUsuario(Usuario usuario) throws SQLException,EntidadeNaoEncontradaException{
        try (PreparedStatement stm = connection.prepareStatement(SQL_UPDATE)){
        	stm.setString(1, usuario.getNome());
			stm.setString(2, usuario.getEmail());
			stm.setString(3, usuario.getSenha());
			stm.setString(4, usuario.getCpf());
            stm.setString(5, usuario.getTelefone());
			stm.setInt(6 , usuario.getCodigo());
			stm.executeUpdate();
            if (stm.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("Conta não encontrada para ser atualizada");
            
        }
	}
    
    public List<Usuario> listar() throws SQLException {
        try (PreparedStatement stm= connection.prepareStatement(SQL_SELECT_ALL)){
            List<Usuario> lista = new ArrayList<>();
            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                	Usuario usuario = new Usuario();
                	usuario.setCodigo(resultSet.getInt("cd_usuario"));
                	usuario.setNome(resultSet.getString("nome"));
                	usuario.setEmail(resultSet.getString("email"));
                	usuario.setSenha(resultSet.getString("senha"));
                	usuario.setCpf(resultSet.getString("cpf"));
                	usuario.setTelefone(resultSet.getString("telefone"));
                    lista.add(usuario);
                }
            }
            return lista;
        }
    }
    
    public Usuario pesquisarPorId(Integer id) throws SQLException, EntidadeNaoEncontradaException {
        try (PreparedStatement stm = connection.prepareStatement(SQL_SELECT_BY_ID)){
            stm.setInt(1, id);
            try (ResultSet resultSet = stm.executeQuery()) {
                if (resultSet.next()) {
                	Usuario usuario = new Usuario();
                	usuario.setCodigo(resultSet.getInt("cd_usuario"));
                	usuario.setNome(resultSet.getString("nome"));
                	usuario.setEmail(resultSet.getString("email"));
                	usuario.setSenha(resultSet.getString("senha"));
                	usuario.setCpf(resultSet.getString("cpf"));
                	usuario.setTelefone(resultSet.getString("telefone"));
                    return usuario;
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
