package it.polito.tdp.porto.model;

import java.util.*;

public class Articolo
{
	private int id;
	private int anno;
	private String titolo;
	private List<Autore> autori = new LinkedList<Autore>();
	
	public Articolo(int id, int anno, String titolo) 
	{
		this.id = id;
		this.anno = anno;
		this.titolo = titolo;
	}

	public int getId() 
	{
		return id;
	}

	public int getAnno() 
	{
		return anno;
	}

	public String getTitolo() 
	{
		return titolo;
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
		Articolo other = (Articolo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public void addAutore(Autore aut)
	{
		autori.add(aut);
	}

	public List<Autore> getAutori() 
	{
		return autori;
	}
	
	
	
	
}
