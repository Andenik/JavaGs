package br.com.fiap.solaris.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";
    private static final String USER = "rm554900";
    private static final String PASSWORD = "071098";

    // Método para obter a conexão
    public static Connection getConexao() throws SQLException {
        try {
            // Carregando o driver Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado.", e);
        }

        // Retornando a conexão
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}