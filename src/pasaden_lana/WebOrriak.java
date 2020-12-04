package pasaden_lana;
import java.io.FileNotFoundException;




import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map; 
import java.util.Collection;
import java.util.Collections;
import java.io.FileWriter;


public class WebOrriak {

	//atributuak
	private ArrayList<WebOrria> lista;
	private static WebOrriak nireWebOrriak=null;
	private HashMap<String,WebOrria> map;
	
	//eraikitzailea
	private WebOrriak() {
		this.lista=new ArrayList<WebOrria>();
		this.map=new HashMap<String,WebOrria>();
	}

	public static WebOrriak getNireWebOrriak() {
		if(nireWebOrriak==null) {
			nireWebOrriak=new WebOrriak();
		}
		return nireWebOrriak;
	}
	
	public Iterator<WebOrria> getIterator(){
		return this.lista.iterator();
	}
	
	public ArrayList<WebOrria> getLista(){
		return this.lista;
	}
	
	public void webOrriakKargatu(){
	           
	        try{
	        	Scanner sarrera=new Scanner(new FileReader("index"));
	            String line = null;
	            
	            //read file line by line
	            while (  sarrera.hasNextLine() ){
	                
	            	line = sarrera.nextLine();
	                //split the line by :
	                String[] parts = line.split(" ");
	                
	                //lehenengo partea indizea bestea url-a, trim() a espacio en blanco-ak kentzen ditu, eta parse int-a string-a integerrean bihurtzen du
	                Integer indizea = Integer.parseInt( parts[1].trim() );
	                String url = parts[0].trim();
	               
	                
	                WebOrria web=new WebOrria(url,indizea);
	                
	               
	                
	                //jarri indizea eta url-a batera 
	                if( !url.equals("") && !indizea.equals("") ) {
	                	this.lista.add(web);
	                	this.map.put(url,web);
	                }
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
	
	public String id2String(int x) {
		return this.lista.get(x).getUrl();
	}
	
	public int String2id(String s) {
		return this.map.get(s).getIndexa();
	}
	
	public void webOrriTxertatu(WebOrria w) {
		this.map.put(w.getUrl(),w);
		this.lista.add(w);
	}
	
	public void webOrriKendu(WebOrria w) {
		if (w!=null && this.getWebOrria(w.getUrl()) != null) {
			this.lista.remove(w);
			this.map.remove(w.getUrl());
		}
		else 
			System.out.println("Ezin da kendu.");
	}
	
	public int luzeera() {
		return this.lista.size();
	}
	
	//Te da las paginas web que contiene una palabra
	public ArrayList<String> word2Webs(String s){
		ArrayList<String> e = new ArrayList<String>();
		Iterator<WebOrria> itr=this.getIterator();
		WebOrria w;
		while(itr.hasNext()) {
			w= itr.next();
			if(w.gakoWeb(s)) {
				e.add(w.getUrl());
			}
		}
 		return e;
	}
	
	public ArrayList<WebOrria> gakoaWebOrriak(String s){
		ArrayList<WebOrria> e = new ArrayList<WebOrria>();
		Iterator<WebOrria> itr=this.getIterator();
		WebOrria w;
		while(itr.hasNext()) {
			w= itr.next();
			if(w.gakoWeb(s)) {
				e.add((w));
			}
		}
		return e;
	}
	
	public WebOrria getWebOrria(String s) {
		return this.map.get(s);
	}
	
	//Ordenar la lista con quicksort  ACABAR
	/*public ArrayList<String> webOrdenatua(){
		ArrayList<String> e = new ArrayList<String>();
		
		this.quickSort(this.getUrlLista(), 0, this.lista.size());
		for (int i = 0;i<this.lista.size();i++) {
			e.set(i,this.lista.get(i).getUrl());
		}
		return e;
		
		
	}*/
	
	private void swap(ArrayList<WebOrria> lista,int ezker, int eskuin) {
		WebOrria w=this.lista.get(ezker);
		this.lista.set(ezker,this.lista.get(eskuin));
		this.lista.set(eskuin, w);
	}
	
	public void quickSort(ArrayList<WebOrria> lista,int hasiera, int bukaera) {
		if(bukaera-hasiera>0) {
			
			int z = this.zatiketa(this.lista,hasiera,bukaera);
			this.quickSort(this.lista,hasiera,z-1);
			this.quickSort(this.lista,z+1,bukaera);

		}
	}
	
	private int zatiketa(ArrayList<WebOrria> lista,int i, int f) {
		String lag=lista.get(i).getUrl();
		int ezker =i;
		int eskuin=f;
		
		while(ezker<eskuin) {
			while(lista.get(ezker).getUrl().compareTo(lag)<=0 && ezker<eskuin) {
				ezker++;
			}
			while(lista.get(eskuin).getUrl().compareTo(lag)>0) {
				eskuin--;
			}
			if(ezker<eskuin) {
				this.swap(lista, ezker,eskuin);
			}
		}
		lista.set(i, lista.get(eskuin));
		lista.set(eskuin, this.getWebOrria(lag));
		
		return eskuin;
	}
	
	public ArrayList<String> getUrlLista(){
		ArrayList<String> e = new ArrayList<String>();
		for (String key:this.map.keySet()) {
			e.add(key);
		}
		return e;
	}
	
	//Para escribir la lista de urls en un txt
	public void dokumentuaSortu() throws IOException {
        FileWriter writer = new FileWriter("url_lista.txt");
        int size = this.getLista().size();
        for (int i=0;i<size;i++) {
            String str = this.getLista().get(i).getUrl();
            writer.write(str);
            if(i < size-1)//This prevent creating a blank like at the end of the file**
                writer.write("\n");
        }
        writer.close();
	}
	
	public void webOrrienErlazioakKargatu(){
		
        try{
        	Scanner sarrera=new Scanner(new FileReader("pld-arcs-1-N"));
            String line = null;
            
            //read file line by line
            while ( sarrera.hasNextLine() ){
                
            	line = sarrera.nextLine();
                //split the line by :
                String[] parts = line.split(" --> ");
                
                int indizea=0;
                
                if(parts.length==1) {
                	String[] partslag=parts[0].split(" ");
                	indizea = Integer.parseInt( partslag[0].trim() );
                }
                else {
                	indizea = Integer.parseInt( parts[0].trim() );
                }
                //lehenengo partea indizea bestea url-a, trim() a espacio en blanco-ak kentzen ditu, eta parse int-a string-a integerrean bihurtzen du
                
                
                //Bigarren partea array bat da
                
                
                if(parts.length>1) {
                	String [] alde = parts[1].split(" ");
                	int i=0;
                    
                    
                    while (i<alde.length) {
                    	//Estas creando de nuevo las webs cuando ya las tienes en la lista
                    	String url = WebOrriak.getNireWebOrriak().id2String(Integer.parseInt(alde[i]));
                    	int ind = Integer.parseInt(alde[i]);
                    	//NO hay que crear las webs de nuevo
                    	this.lista.get(indizea).getListaOrriak().add(this.lista.get(ind));
                    	
                    	i++;
                    }
                }
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
	
	
}
