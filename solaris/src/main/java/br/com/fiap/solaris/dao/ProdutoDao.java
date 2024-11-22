package br.com.fiap.solaris.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.solaris.exception.EntidadeNaoEncontradaException;
import br.com.fiap.solaris.model.Produto;
import br.com.fiap.solaris.model.Usuario;

public class ProdutoDao {
	
	private static final String SQL_INSERT = "insert into gs_produto (nome, preco, desc_produto, img) values (?, ?, ?, ?)";
	private static final String SQL_SELECT_ALL = "select * from gs_produto";
	private static final String SQL_SELECT_BY_ID = "select * from gs_produto where cd_produto = ?";
	private static final String SQL_UPDATE = "update gs_produto set nome = ?, preco = ?, desc_produto = ?, img = ? where cd_produto = ?";
	private static final String SQL_DELETE = "delete from gs_produto where cd_produto = ?";
	
	private Connection connection;

    public ProdutoDao(Connection connection){
        this.connection = connection;
    }
    
    public void inserirProduto(Produto produto) throws SQLException {
        try (PreparedStatement stm = connection.prepareStatement(SQL_INSERT, new String[] {"cd_produto"})) {
            
            stm.setString(1, produto.getNome());
            stm.setString(2, produto.getPreco());
            stm.setString(3, produto.getDesc_produto());
            stm.setString(4, produto.getImg());            
            stm.executeUpdate();
                     
            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                	produto.setCodigo(generatedKeys.getInt(1)); 
                }
            }
        }
    }
    
    public void atualizarProduto(Produto produto) throws SQLException,EntidadeNaoEncontradaException{
        try (PreparedStatement stm = connection.prepareStatement(SQL_UPDATE)){
        	stm.setString(1, produto.getNome());
            stm.setString(2, produto.getPreco());
            stm.setString(3, produto.getDesc_produto());
            stm.setString(4, produto.getImg());   
			stm.setInt(5 , produto.getCodigo());
			stm.executeUpdate();
            if (stm.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("Produto não encontrada para ser atualizada");
            
        }
	}
    
    public List<Produto> listar() throws SQLException {
        try (PreparedStatement stm= connection.prepareStatement(SQL_SELECT_ALL)){
            List<Produto> lista = new ArrayList<>();
            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                	Produto produto = new Produto();
                	produto.setCodigo(resultSet.getInt("cd_produto"));
                	produto.setNome(resultSet.getString("nome"));
                	produto.setPreco(resultSet.getString("preco"));
                	produto.setDesc_produto(resultSet.getString("desc_produto"));
                	produto.setImg(resultSet.getString("img"));
                    lista.add(produto);
                }
            }
            return lista;
        }
    }
    
    public Produto pesquisarPorId(Integer id) throws SQLException, EntidadeNaoEncontradaException {
        try (PreparedStatement stm = connection.prepareStatement(SQL_SELECT_BY_ID)){
            stm.setInt(1, id);
            try (ResultSet resultSet = stm.executeQuery()) {
                if (resultSet.next()) {
                	Produto produto = new Produto();
                	produto.setCodigo(resultSet.getInt("cd_produto"));
                	produto.setNome(resultSet.getString("nome"));
                	produto.setPreco(resultSet.getString("preco"));
                	produto.setDesc_produto(resultSet.getString("desc_produto"));
                	produto.setImg(resultSet.getString("img"));               	
                    return produto;
                } else{
                    throw new EntidadeNaoEncontradaException("Produto não encontrado");
                }
            }
        }
    }
    
    public void remover(Integer id) throws SQLException, EntidadeNaoEncontradaException {
        try (PreparedStatement stm = connection.prepareStatement(SQL_DELETE)){
            stm.setInt(1, id);
            if (stm.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("Produto não encontrada para ser removido");
        }
    }

}
