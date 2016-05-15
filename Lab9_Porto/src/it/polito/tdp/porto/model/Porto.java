package it.polito.tdp.porto.model;

import java.util.*;

import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.porto.db.ArticoloDAO;
import it.polito.tdp.porto.db.AutoreDAO;
import it.polito.tdp.porto.db.ConnessioneDAO;

public class Porto 
{
	private DefaultDirectedWeightedGraph<Autore, DefaultWeightedEdge> grafo = new DefaultDirectedWeightedGraph<Autore, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	private List<Autore> autori= new ArrayList<Autore>();
	private List<Articolo> articoli= new ArrayList<Articolo>();
	
	public List<Autore> getAutori()
	{
		AutoreDAO adao = new AutoreDAO();
		autori = adao.getAutori();
		return autori;
	}
		
	public void creaGrafo()
	{
		ArticoloDAO adao = new ArticoloDAO();
		articoli = adao.getArticoli();
		ConnessioneDAO cdao = new ConnessioneDAO();
		
		for (Autore autore : autori) 
		{
			cdao.getArticoliperAutore(autore);
			grafo.addVertex(autore);
		}
		
		for (Articolo articolo : articoli) 
		{
			cdao.getAutoriperArticolo(articolo);
			for (Autore a : articolo.getAutori())
			{
				for (Autore a2 :articolo.getAutori())
				{
					if(a.getId() != a2.getId() && !grafo.containsEdge(a, a2))
					{
						Graphs.addEdgeWithVertices(grafo, a, a2, 1.0);
						Graphs.addEdgeWithVertices(grafo, a2, a, 1.0);
					}
				}
			}
		}
	}
	public List<Autore> getCoautori(Autore a)
	{
		return Graphs.neighborListOf(grafo, a);
	}

	public List<List<Autore>> cercaCluster() 
	{
		List<List<Autore>> aut = new ArrayList<List<Autore>>();
		List<Autore> visited = new ArrayList<Autore>();
		List<Autore> temp = new ArrayList<Autore>();
		for (Autore autore : autori)
		{
			if(!visited.contains(autore))
			{
				GraphIterator<Autore, DefaultWeightedEdge> dfv = new DepthFirstIterator<Autore, DefaultWeightedEdge>(grafo, autore);
				while (dfv.hasNext()) 
				{
					Autore aa = dfv.next();
					visited.add(aa);
					temp.add(aa);
				}
				aut.add(new ArrayList<Autore>(temp));
				temp.clear();
			}
		}
		return aut;
	}
	public List<Articolo> trovaCammini(Autore a, Autore b)
	{
		List<Autore> coaut = getCoautori(a);
		List<Autore> aut = new ArrayList<Autore>();
		List<Articolo> art = new ArrayList<Articolo>();
		if(!coaut.contains(b))
		{
			DijkstraShortestPath<Autore, DefaultWeightedEdge> dijkstra= new DijkstraShortestPath<>(grafo, a, b);
			GraphPath<Autore, DefaultWeightedEdge> path = dijkstra.getPath();
			if(path != null)
			{
				aut = Graphs.getPathVertexList(path);
				for (int i = 0; i < aut.size(); i++)
				{
					if(i!= aut.size()-1)
					{
						ConnessioneDAO cdao = new ConnessioneDAO();
						art.addAll(cdao.trovaArticoliTraAutori(aut.get(i), aut.get(i+1)));
					}
				}
				return art;
			}
			else
				return null;
		}
		else
			return null;
	}
}
