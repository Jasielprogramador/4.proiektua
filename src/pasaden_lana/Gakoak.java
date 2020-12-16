package pasaden_lana;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class Gakoak {
	//atributuak
	private ArrayList<Gakoa> lista;
	private HashMap<String,Gakoa> mapGakoa;
	
	private static final Gakoak instance = new Gakoak();

    public static Gakoak getInstance() {
        return instance;
    }
	 
	//eraikitzailea
	public Gakoak() {
		this.lista=new ArrayList<Gakoa>();
		this.mapGakoa = new HashMap<String,Gakoa>();
	}
	
	public Iterator<Gakoa> getIteradorea(){
		return this.lista.iterator();
	}
	
	//Esto lo añado para conseguir el gakoa para despues utilizarlo en el graph
	public Gakoa gakoaLortu(String gakoa) {
		return this.mapGakoa.get(gakoa);
	}
	
	public void listaKargatu() {
		try {
			Scanner sarrera=new Scanner(new FileReader("words.txt"));
			String lerroa;
			
			while(sarrera.hasNextLine()) {
				lerroa=sarrera.nextLine();
				Gakoa g=new Gakoa(lerroa);
				this.mapGakoa.put(lerroa, g);
				this.lista.add(g);
			}
			sarrera.close();
		}
		catch (FileNotFoundException e)
	    {
	      System.out.println("There was an exception!  The file was not found!");
	    } 
	    catch (IOException e)
	    {
	      System.out.println("There was an exception handling the file!");
	    }
	}
	
	//Te da las palabras que contiene una pagina web
	public ArrayList<WebOrria> word2Webs(String w){
		ArrayList<WebOrria> urlLista = new ArrayList<WebOrria>();
		if(!mapGakoa.get(w).equals(null)) {
			Gakoa gakoa = mapGakoa.get(w);
			urlLista = gakoa.getLista();
		}
		return urlLista;
		
	}
	
	public ArrayList<Gakoa> getLista(){
		return this.lista;
	}
	
	public HashMap<String,Gakoa> getMap(){
		return this.mapGakoa;
	}
	

}
