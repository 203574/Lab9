package it.polito.tdp.porto.model;

import java.util.*;

public class Autore 
{
	private int id;
	private String cognome;
	private String nome;
	private List<Articolo> articoli = new ArrayList<Articolo>();
	private List<Autore> coautori = new ArrayList<Autore>();
	
	public Autore(int id, String cognome, String nome) 
	{
		this.id = id;
		this.cognome = cognome;
		this.nome = nome;
	}

	public int getId() 
	{
		return id;
	}

	public String getCognome() 
	{
		return cognome;
	}

	public String getNome() 
	{
		return nome;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autore other = (Autore) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public void addArticolo(Articolo art)
	{
		articoli.add(art);
	}

	public List<Articolo> getArticoli() 
	{
		return articoli;
	}

	

	
}
