package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.porto.model.Articolo;
import it.polito.tdp.porto.model.Autore;

public class ArticoloDAO 
{
	public List<Articolo> getArticoli()
	{
		List<Articolo> articoli = new LinkedList<Articolo>();
		Connection conn = DBConnect.getConnection();
		String sql = "select `eprintid`, `year`, `title` from article";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			while (res.next()) 
			{
				int id = Integer.parseInt(res.getString("eprintid"));
				int anno = Integer.parseInt(res.getString("year"));
				String titolo = res.getString("title");
				Articolo a = new Articolo(id, anno, titolo);
				articoli.add(a);
			}
			conn.close();
			return articoli;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore caricamento articoli");
		}
	}
}
