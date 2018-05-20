/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import infra.ConexaoJDBC;
import infra.ConexaoMySQLJDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Usuario;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import util.Status;

/**
 *
 * @author Andressa
 */
public class UsuarioDAO {
    private final ConexaoJDBC conexao;
    
    public UsuarioDAO() throws SQLException, ClassNotFoundException {
        this.conexao = new ConexaoMySQLJDBC();
    }
    
    public int cadastrar(Usuario usuario) throws SQLException, ClassNotFoundException {
        int id=-1;
        String sqlQuery = "INSERT INTO usuario (nome, dataNascimento, idioma, ocupacao, email,"
                + "senha, localizacao, statusConta) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        String sqlQuery2 = "SELECT LAST_INSERT_ID() AS 'aux';";
                
        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getDataNascimento());
            stmt.setString(3, usuario.getIdioma());
            stmt.setString(4, usuario.getOcupacao());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getSenha());
            stmt.setString(7, usuario.getLocalizacao());
            stmt.setString(8, usuario.getStatusConta().toString());
            
            stmt.executeUpdate();                        
            
            PreparedStatement stmt2 = this.conexao.getConnection().prepareStatement(sqlQuery2);
            
            ResultSet rs = stmt2.executeQuery();            
            
            if(rs.next())
                id = rs.getInt("aux");
            
            this.conexao.commit();            
        } catch (SQLException e) {
             this.conexao.rollback();
             throw e;
         }
                        
        return id;
    }
    
    public int atualizar(Usuario usuario) throws SQLException, ClassNotFoundException {
        String sqlQuery = "UPDATE usuario SET nome = ?, dataNascimento = ?, idioma = ?, ocupacao = ?,"
                + "email = ?, senha = ?, localizacao = ?, statusConta = ? WHERE idUsuario = ?;";
        int linhasAfetadas = 0;
        
        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getDataNascimento());
            stmt.setString(3, usuario.getIdioma());
            stmt.setString(4, usuario.getOcupacao());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getSenha());
            stmt.setString(7, usuario.getLocalizacao());
            stmt.setString(8, usuario.getStatusConta().toString());
            stmt.setInt(9, usuario.getIdUsuario());
            
            linhasAfetadas = stmt.executeUpdate();
            this.conexao.commit();
            
        } catch(SQLException e) {
            this.conexao.rollback();
            throw e;
        }
        
        return linhasAfetadas;
    }

    public int excluir(int id) throws SQLException, ClassNotFoundException {
        int linhasAfetadas = 0;
        String sqlQuery = "DELETE FROM usuario WHERE idUsuario = ?;";
        
        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, id);
            
            linhasAfetadas = stmt.executeUpdate();
            this.conexao.commit();
            this.conexao.rollback();
            
        } catch(SQLException e) {
            throw e;
        }
        
        return linhasAfetadas;
    }
    
    public Usuario consultar(int id) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM usuario WHERE idUsuario = ?;";
        
        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                return parser(rs);
            }            
        } catch(SQLException e) {
            throw e;
        }
        return null;
    }
    
    public List<Usuario> listar() throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM usuario ORDER by idUsuario";       
        
        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);            
            ResultSet rs = stmt.executeQuery();
            
            List<Usuario> usuarios = new ArrayList();
            
            while(rs.next()) {
                usuarios.add(parser(rs));                
            }
            
            return usuarios;         
        } catch(SQLException e) {
            throw e;
        }
    }
    
    private Usuario parser(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        
        u.setIdUsuario(rs.getInt("idUsuario"));
        u.setNome(rs.getString("nome"));
        u.setDataNascimento(rs.getString("dataNascimento"));
        u.setIdioma(rs.getString("idioma"));
        u.setOcupacao(rs.getString("ocupacao"));
        u.setEmail(rs.getString("email"));
        u.setSenha(rs.getString("senha"));
        u.setLocalizacao(rs.getString("localizacao"));        
        u.setStatusConta(Status.valueOf(rs.getString("statusConta")));
        
        return u;
    }
    
    //fazer o método desativar()
}
