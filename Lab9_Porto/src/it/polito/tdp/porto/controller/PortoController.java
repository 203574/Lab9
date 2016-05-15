package it.polito.tdp.porto.controller;

import java.net.URL;
import java.util.*;

import it.polito.tdp.porto.model.Articolo;
import it.polito.tdp.porto.model.Autore;
import it.polito.tdp.porto.model.Porto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController 
{
	Porto p = new Porto();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboAutore1;

    @FXML
    private ComboBox<String> comboAutore2;

    @FXML
    private Button buttonCoautori;

    @FXML
    private Button buttonCluster;

    @FXML
    private Button buttonArticoli;

    @FXML
    private TextArea txtResult;
    
    public Autore getAutoreById(String s)
    {
    	int id = Integer.parseInt(s.substring(0, s.indexOf(" ")));
    	for (Autore a : p.getAutori())
    	{
    		if(a.getId() == id)
    		{
    			return a;
    		}
		}
    	return null;
    }

    @FXML
    void doCercaArticoli(ActionEvent event) 
    {
    	txtResult.clear();
    	Autore a1 = getAutoreById(comboAutore1.getValue());
    	Autore a2 = getAutoreById(comboAutore2.getValue());
    	List<Articolo> art = p.trovaCammini(a1, a2);
    	txtResult.appendText("Articoli trovati per "+a1.getCognome()+" "+a1.getNome()+" e "+a2.getCognome()+" "+a2.getNome()+":\n");
    	if(art != null)
    	{
    		for (Articolo articolo : art)
    		{
    			txtResult.appendText(articolo.getTitolo()+"\n");
			}
    	}
    	else
    	{
    		txtResult.appendText("Spiacenti non esiste nessun articolo in comune tra i due autori");
    	}
    }

    @FXML
    void doCercaCoautori(ActionEvent event) 
    {
    	txtResult.clear();
    	List<Autore> coautori = new ArrayList<Autore>();
    	if(comboAutore1.getValue() != null)
    	{
    			Autore a = getAutoreById(comboAutore1.getValue());
    			txtResult.appendText("Coautori per "+a.getNome()+" "+a.getCognome()+":\n");
    			coautori = p.getCoautori(a);
    	}
    	else
    	{
   			Autore a = getAutoreById(comboAutore2.getValue());
   			txtResult.appendText("Coautori per "+a.getNome()+" "+a.getCognome()+":\n");
   			coautori = p.getCoautori(a);
    	}
    	for (Autore autore : coautori) 
   		{
   			txtResult.appendText(autore.getNome()+" "+autore.getCognome()+";\n");
		}
    	
    	
    }

    @FXML
    void doCercacluster(ActionEvent event) 
    {
    	txtResult.clear();
    	List<List<Autore>> cluster = p.cercaCluster();
    	int count = 1;
    	for (List<Autore> list : cluster) 
    	{
    		txtResult.appendText("cluster n"+count+"\n");
    		for (Autore autore : list) 
    		{
    			txtResult.appendText("Autore: "+autore.getNome()+" "+autore.getCognome()+";\n");
			}
    		count++;
		}
    }

    @FXML
    void initialize() 
    {
        assert comboAutore1 != null : "fx:id=\"comboAutore1\" was not injected: check your FXML file 'Porto.fxml'.";
        assert comboAutore2 != null : "fx:id=\"comboAutore2\" was not injected: check your FXML file 'Porto.fxml'.";
        assert buttonCoautori != null : "fx:id=\"buttonCoautori\" was not injected: check your FXML file 'Porto.fxml'.";
        assert buttonCluster != null : "fx:id=\"buttonCluster\" was not injected: check your FXML file 'Porto.fxml'.";
        assert buttonArticoli != null : "fx:id=\"buttonArticoli\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";
        List<Autore> autori = p.getAutori();
        for (Autore autore : autori) 
        {
        	comboAutore1.getItems().add(autore.getId()+" "+autore.getNome()+" "+autore.getCognome());
        	comboAutore2.getItems().add(autore.getId()+" "+autore.getNome()+" "+autore.getCognome());
		}
        p.creaGrafo();
        
    }
}
