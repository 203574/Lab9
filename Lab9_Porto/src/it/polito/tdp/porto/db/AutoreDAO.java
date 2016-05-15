package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.management.RuntimeErrorException;

import it.polito.tdp.porto.model.Autore;

public class AutoreDAO
{
	public List<Autore> getAutori()
	{
		List<Autore> autori = new LinkedList<Autore>();
		Connection conn = DBConnect.getConnection();
		String sql = "select `id_creator`, `family_name`, `given_name` from creator";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			while (res.next()) 
			{
				int id = Integer.parseInt(res.getString("id_creator"));
				String cognome = res.getString("family_name");
				String nome = res.getString("given_name");
				Autore a = new Autore(id, cognome, nome);
				autori.add(a);	
			}
			conn.close();
			return autori;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore caricamento autori");
		}
	}
}
