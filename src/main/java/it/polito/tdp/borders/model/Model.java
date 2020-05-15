package it.polito.tdp.borders.model;
import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;
public class Model {
	
	private Map<Integer, Country> idMap;
	private Graph <Country, DefaultEdge> grafo;
	private BordersDAO dao;
	public Model() {
		idMap=new TreeMap<>();
		dao=new BordersDAO();
	}
	
	public String creaGrafo(int anno)
	{
		String ris="";
		this.grafo=new SimpleGraph<>(DefaultEdge.class);
		
		dao.loadAllCountriesPerYear(idMap, anno);
		Graphs.addAllVertices(this.grafo, idMap.values());
		for(Country c: idMap.values())
		{
			ris+=c.getStateNme()+" ("+c.getStateAbb()+"): "+c.getStatiConfinanti()+"\n";
		}
		List<Border> listaBorder=dao.getCountryPairs(idMap, anno);
		for(Border b:listaBorder)
		{
			this.grafo.addEdge(b.getState1(), b.getState2());	
		}
		ris+=String.format("Grafo creato! #vertici %d, # Archi %d\n", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
		List <Set<Country>> connessioni=new ConnectivityInspector<>(grafo).connectedSets();
		int size=connessioni.size();
		ris+="Ci sono "+size+" componenti connesse";
		return ris;
	}
	
	public List<Country> loadAllCountriesPerYear(int anno)
	{
		BordersDAO dao=new BordersDAO();
		return dao.loadAllCountriesPerYear(idMap,anno);
	}
	
	public List<Country> visitaAmpiezza(Country source)
	{
		List<Country> visita=new ArrayList<>();
		GraphIterator<Country,DefaultEdge> bfv=new BreadthFirstIterator<>(grafo,source);
		while(bfv.hasNext())
		{
			visita.add(bfv.next());
		}
		return visita;
	}
	
	public List<Country> visitaProfondita(Country source)
	{
		List<Country> visita=new ArrayList<>();
		GraphIterator<Country,DefaultEdge> dfv=new DepthFirstIterator<>(grafo,source);
		while(dfv.hasNext())
		{
			visita.add(dfv.next());
		}
		return visita;
	}
	
}
