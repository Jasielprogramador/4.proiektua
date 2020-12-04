package pasaden_lana;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class Gakoak {
	//atributuak
	private ArrayList<Gakoa> lista;
	 
	//eraikitzailea
	public Gakoak() {
		this.lista=new ArrayList<Gakoa>();
	}
	
	public Iterator<Gakoa> getIteradorea(){
		return this.lista.iterator();
	}
	
	public void listaKargatu() {
		try {
			Scanner sarrera=new Scanner(new FileReader("words.txt"));
			String lerroa;
			
			while(sarrera.hasNextLine()) {
				lerroa=sarrera.nextLine();
				Gakoa g=new Gakoa(lerroa);
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
	public ArrayList<String> web2Words(String w){
		ArrayList<String> e = new ArrayList<String>();
		Iterator<Gakoa> itr=this.getIteradorea();
		Gakoa g;
		
		while(itr.hasNext()) {
			g=itr.next();
			if(g.webGako(w)){
				e.add(g.getIzena());
			}
		}
		return e;
		
	}

}
