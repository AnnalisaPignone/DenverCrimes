package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	private Graph<String, DefaultWeightedEdge>grafo; 
	List <String> vertici; 
	List <String> categorie;
	EventsDao dao;
	List<Arco> archi; 
	List<List<String>> soluzioni; 
	Set<String> visitati; 
	List <String> soluzioneValida;
	
	public Model() {
		vertici= new ArrayList<>();
		categorie= new ArrayList<>();
		dao=new EventsDao(); 
		archi=new ArrayList<>(); 
		soluzioni=new ArrayList<List<String>>(); 
		visitati= new HashSet<>();
		soluzioneValida=new ArrayList<String>(); 
	}
	
	public List<String> getAllCategorie(){
		categorie= this.dao.getAllCategorie(); 
		Collections.sort(categorie, new ComparatoreAlfabetico());
		return categorie; 
	}
	
	public void creaGrafo(String categoria, int mese) {
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
		vertici= new ArrayList<>(dao.getVertici(categoria, mese)); 
		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, dao.getVertici(categoria, mese)); 
		//aggiungere gli archi 
		for (int i=0; i<vertici.size(); i++) {
			for (int j=i+1; j<vertici.size(); j++) {
				double peso= this.dao.getPesoArco(mese, vertici.get(i), vertici.get(j)); 
				if (peso!=0) {
					archi.add(new Arco(vertici.get(i), vertici.get(j), peso));
					Graphs.addEdgeWithVertices(this.grafo, vertici.get(i) , vertici.get(j), peso); 
				}
			}
		}
		
		
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List <Arco> getArchiDaStampare(){
		double pesoMedio=0; 
		List <Arco> result= new ArrayList <Arco>(); 
		for (Arco a: archi) {
			pesoMedio+=a.getPesoArco(); 
		}
		pesoMedio=pesoMedio/archi.size(); 
		for (Arco a: archi) {
			if(a.getPesoArco()>pesoMedio) {
				result.add(a); 
			}
		}
		return result; 
	}
	
	public List<String> calcolaPercorso(Arco arco) {
		//carchiamo la componente connessa 
		ConnectivityInspector<String, DefaultWeightedEdge> ci = new ConnectivityInspector<String, DefaultWeightedEdge>(this.grafo);
		 
		visitati= ci.connectedSetOf(arco.getV1());
		List<String> parziale= new ArrayList<>();
		parziale.add(arco.getV1()); 
		ricorsiva(parziale, 1, arco); 
		return soluzioneValida; 
	}

	private void ricorsiva(List<String>parziale, int L, Arco arco) {
		//controllare i casi terminali
		if(parziale.size()>1) {
			DefaultWeightedEdge edge=this.grafo.getEdge(parziale.get(parziale.size()-2), parziale.get(parziale.size()-1)); 
			if (edge==null)
				return; 
		}
		
		//soluzioni valide 
		if(parziale.get(parziale.size()-1).compareTo(arco.getV2())==0) {
			//soluzioni.add(new ArrayList<String>(parziale)); 
			int lunghezza=0; 
			if(parziale.size()>lunghezza) {
				lunghezza=parziale.size(); 
				soluzioneValida=new ArrayList <String>(parziale); 
			}
			
		}
			
		
		//generiamo i sottoproblemi
		for (String s: visitati) {
			if(!parziale.contains(s)) {
				parziale.add(s); 
				ricorsiva(parziale,L+1, arco); 
				parziale.remove(parziale.get(parziale.size()-1));
			}
		}
			
		
	}
	
}
