package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Autores;
import com.example.model.Livros;

public class LivrosDao {

    private final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private final String USER = "rm551575";
    private final String PASS = "060598";
    
    public void inserir(Livros livro) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASS);

        var ps = con.prepareStatement("INSERT INTO livros (titulo, genero, paginas, valor, autor_id) VALUES (?, ?, ?, ?, ?)");
        ps.setString(1, livro.getTitulo());
        ps.setString(2, livro.getGenero());
        ps.setInt(3, livro.getPaginas());
        ps.setBigDecimal(4, livro.getValor());
        ps.setInt(5, livro.getAutor().getId());

        ps.executeUpdate();
        con.close();
    }

    public List<Livros> buscarTodos() throws SQLException{
        var livros = new ArrayList<Livros>();
        var con = DriverManager.getConnection(URL, USER, PASS);
        var rs = con.createStatement().executeQuery("SELECT livros.*, autores.nome FROM livros INNER JOIN autores ON livros.autor_id=autores.id");

        while(rs.next()){
            livros.add(new Livros(
                rs.getInt("id"),
                rs.getString("titulo"), 
                rs.getString("genero"), 
                rs.getInt("paginas"), 
                rs.getBigDecimal("valor"),
                new Autores(
                    rs.getInt("autor_id"),
                    rs.getString("nome"),
                    null,
                    null
                )
            ));
        }

        con.close();
        return livros;
    }

    public void apagar(Livros livro) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("DELETE FROM livros WHERE id=?"); 
        ps.setInt(1, livro.getId());
        ps.executeUpdate();
        con.close();
    }

    public void atualizar(Livros livro) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("UPDATE livros SET titulo=?, genero=?, paginas=?, valor=? WHERE id=?");
        ps.setString(1, livro.getTitulo());
        ps.setString(2, livro.getGenero());
        ps.setInt(3, livro.getPaginas());
        ps.setBigDecimal(4, livro.getValor());
        ps.setInt(5, livro.getId());
        ps.executeUpdate();
        con.close();

    }

}
