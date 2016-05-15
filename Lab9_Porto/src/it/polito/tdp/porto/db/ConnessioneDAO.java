package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.porto.model.Articolo;
import it.polito.tdp.porto.model.Autore;

public class ConnessioneDAO 
{
	public void getArticoliperAutore(Autore a)
	{
		List<Articolo> articoli = new LinkedList<Articolo>();
		Connection conn = DBConnect.getConnection();
		String sql = "select `eprintid`, year, title from article where eprintid in (select eprintid from authorship where id_creator=?)";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getId());
			ResultSet res = ps.executeQuery();
			while (res.next()) 
			{
				int id = Integer.parseInt(res.getString("eprintid"));
				int anno = Integer.parseInt(res.getString("year"));
				String titolo = res.getString("title");
				Articolo art = new Articolo(id, anno, titolo);
				a.addArticolo(art);
				articoli.add(art);
			}
			conn.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore caricamento autori");
		}
	}
	
	public void getAutoriperArticolo(Articolo a)
	{
		List<Autore> autori = new LinkedList<Autore>();
		Connection conn = DBConnect.getConnection();
		String sql = "select `id_creator`, `family_name`, `given_name` from creator where `id_creator` in (select id_creator from `authorship` where `eprintid`= ?)";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getId());
			ResultSet res = ps.executeQuery();
			while (res.next()) 
			{
				int id = Integer.parseInt(res.getString("id_creator"));
				String cognome = res.getString("family_name");
				String nome = res.getString("given_name");
				Autore aut = new Autore(id, cognome, nome);
				a.addAutore(aut);
				autori.add(aut);	
			}
			conn.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore caricamento articoli");
		}
	}

	public List<Articolo> trovaArticoliTraAutori(Autore autore, Autore autore2) 
	{
		List<Articolo> art = new ArrayList<Articolo>();
		Connection conn = DBConnect.getConnection();
		String sql = "select art.`eprintid` as id, `year`, `title` from `article` art, `authorship` a, `authorship` b where a.`eprintid`=b.`eprintid` and a.`eprintid`=art.`eprintid` and b.`eprintid` = art.`eprintid` and a.`id_creator`!= b.`id_creator` and a.`id_creator`=? and b.`id_creator`=?";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, autore.getId());
			ps.setInt(2, autore2.getId());
			ResultSet res = ps.executeQuery();
			while(res.next())
			{
				int id = Integer.parseInt(res.getString("id"));
				int year = Integer.parseInt(res.getString("year"));
				String titolo = res.getString("title");
				Articolo a = new Articolo(id, year, titolo);
				art.add(a);
			}
			conn.close();
			return art;
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore caricamento articoli per autori");
		}
		
	}
}
