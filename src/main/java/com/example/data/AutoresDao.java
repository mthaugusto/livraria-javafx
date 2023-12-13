package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Autores;

public class AutoresDao {

    private final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private final String USER = "rm551575";
    private final String PASS = "060598";

    public void inserir(Autores autor) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);

        var ps = con.prepareStatement("INSERT INTO autores (nome, email, site) VALUES (?, ?, ?)");
        ps.setString(1, autor.getNome());
        ps.setString(2, autor.getEmail());
        ps.setString(3, autor.getSite());

        ps.executeUpdate();
        con.close();
    }

    public List<Autores> buscarTodos() throws SQLException {
        var autores = new ArrayList<Autores>();
        var con = DriverManager.getConnection(URL, USER, PASS);
        var rs = con.createStatement().executeQuery("SELECT * FROM autores");

        while (rs.next()) {
            autores.add(new Autores(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("site")));
        }

        con.close();
        return autores;
    }

    public void apagar(Autores autor) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("DELETE FROM autores WHERE id=?");
        ps.setInt(1, autor.getId());
        ps.executeUpdate();
        con.close();
    }

    public void atualizar(Autores autor) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("UPDATE autores SET nome=?, email=?, site=? WHERE id=?");
        ps.setString(1, autor.getNome());
        ps.setString(2, autor.getEmail());
        ps.setString(3, autor.getSite());
        ps.setInt(4, autor.getId());

        ps.executeUpdate();
        con.close();

    }

}
