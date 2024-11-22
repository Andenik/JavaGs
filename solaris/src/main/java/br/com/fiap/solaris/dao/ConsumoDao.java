package br.com.fiap.solaris.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.solaris.exception.EntidadeNaoEncontradaException;
import br.com.fiap.solaris.model.Consumo;
import br.com.fiap.solaris.model.Usuario;

public class ConsumoDao {
	private static final String SQL_INSERT = "insert into gs_consumo (cd_usuario, consumo) values (?, ?)";
	private static final String SQL_SELECT_ALL = "select * from gs_consumo";
	private static final String SQL_SELECT_BY_ID = "select * from gs_consumo where cd_consumo = ?";
	private static final String SQL_SELECT_BY_ID_USUARIO = "select * from gs_consumo where cd_usuario = ?";
	private static final String SQL_UPDATE = "update gs_consumo set consumo = ? where cd_consumo = ?";
	private static final String SQL_DELETE = "delete from gs_consumo where cd_consumo = ?";

	private Connection connection;

	public ConsumoDao(Connection connection) {
		this.connection = connection;
	}

	public void inserirConsumo(Usuario usuario, Consumo consumo) throws SQLException {

		try (PreparedStatement stm = connection.prepareStatement(SQL_INSERT)) {

			stm.setInt(1, usuario.getCodigo());
			stm.setString(2, consumo.getConsumoKw());

			stm.executeUpdate();

			try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					consumo.setCodigo(generatedKeys.getInt(1));
				}
			}
		}
	}

	public void atualizarConsumo(Consumo consumo) throws SQLException, EntidadeNaoEncontradaException {
		try (PreparedStatement stm = connection.prepareStatement(SQL_UPDATE)) {
			stm.setString(1, consumo.getConsumoKw());
			stm.setInt(2, consumo.getCodigo());
			stm.executeUpdate();
			if (stm.executeUpdate() == 0)
				throw new EntidadeNaoEncontradaException("Conta não encontrada para ser atualizada");

		}
	}

	public List<Consumo> listar() throws SQLException {
		try (PreparedStatement stm = connection.prepareStatement(SQL_SELECT_ALL)) {
			List<Consumo> lista = new ArrayList<>();
			try (ResultSet resultSet = stm.executeQuery()) {
				while (resultSet.next()) {
					Consumo consumo = new Consumo();
					consumo.setCodigo(resultSet.getInt("cd_consumo"));
					consumo.setConsumoKw(resultSet.getString("consumo"));
					Usuario usuario = new Usuario();
					usuario.setCodigo(resultSet.getInt("cd_usuario"));
					consumo.setUsuario(usuario);

					lista.add(consumo);
				}
			}
			return lista;
		}
	}

	

	public Consumo pesquisarPorId(Integer id) throws SQLException, EntidadeNaoEncontradaException {
		try (PreparedStatement stm = connection.prepareStatement(SQL_SELECT_BY_ID)) {
			stm.setInt(1, id);
			try (ResultSet resultSet = stm.executeQuery()) {
				if (resultSet.next()) {
					Consumo consumo = new Consumo();
					consumo.setCodigo(resultSet.getInt("cd_consumo"));
					consumo.setConsumoKw(resultSet.getString("consumo"));
					Usuario usuario = new Usuario();
					usuario.setCodigo(resultSet.getInt("cd_usuario"));
					consumo.setUsuario(usuario);
					return consumo;
				} else {
					throw new EntidadeNaoEncontradaException("conta não encontrado");
				}
			}
		}
	}

	public void remover(Integer id) throws SQLException, EntidadeNaoEncontradaException {
		try (PreparedStatement stm = connection.prepareStatement(SQL_DELETE)) {
			stm.setInt(1, id);
			if (stm.executeUpdate() == 0)
				throw new EntidadeNaoEncontradaException("conta não encontrada para ser removido");
		}
	}

}
