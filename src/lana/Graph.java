package lana;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import pasaden_lana.Gakoa;
import pasaden_lana.Gakoak;
import pasaden_lana.WebOrria;
import pasaden_lana.WebOrriak;


public class Graph {
	
	//URL bakoitzeko, bere identifikatzailea ematen du
	private HashMap<String,Integer> th;
	
	//Indentifikatzaile bakoitzeko bere URLa.
	private String[] keys;
	
	//URL bakoitza zein beste URL ditu
	private ArrayList<Integer>[] adjList;
	
	
	private ArrayList<Integer>[] adjListAlde;

	
	//Eraikitzailea
	public Graph(){
	}
	
	public void grafoaSortu(ArrayList<WebOrria> lista) throws IOException{
		
		if(!lista.equals(null)) {
			// Post: web-en zerrendatik grafoa sortu
			//Nodoak web-en url-ak dira
			
	        // 1. pausua:  “th” bete
	        // KODEA INPLEMENTATU
			
			this.th=new HashMap<String,Integer>();
			keys = new String[lista.size()];
			this.adjList= new ArrayList[lista.size()];
			this.adjListAlde = new ArrayList[lista.size()];
			
			for (int i = 0;i<lista.size();i++) {
				
				//1.pausua th bete
				this.th.put(lista.get(i).getUrl(),i);
				
				// 2. pausua: “keys” bete
				this.keys[i]=lista.get(i).getUrl();
			}
			
			//hasieratzeko
			for (int z = 0;z<lista.size();z++) {
				this.adjList[z]=new ArrayList<Integer>();
				this.adjListAlde[z]=new ArrayList<Integer>();
			}
			
			for ( int a = 0;a<lista.size();a++) {
				
				for (int h = 0;h<lista.get(a).getListaOrriak().size();h++) {
					
					// 3. pausua: “adjList” bete 
					int arku = this.th.get(lista.get(a).getListaOrriak().get(h).getUrl());
					this.adjList[a].add(arku);
					
					//4.pausua "adjListInbertsoa" bete
					this.adjListAlde[arku].add(a);
				}
	
			}
		}
		else {
			System.out.println("WebOrrien zerrenda hutsa");
		}
		
	}
	
	
	public void print(){
		
		for (int i = 0; i < adjList.length; i++){
			
			System.out.print("Element: " + i + " " + keys[i] + " --> ");
			
			for (int k: adjList[i])  System.out.print(keys[k] + " ### ");
				System.out.println();
			}
	}
	
	public boolean erlazionatuta(String a1, String a2) {
		
		Queue<Integer> aztertuGabeak = new LinkedList<Integer>();

		int pos1 = th.get(a1);		//Te da la posicion que tiene la url que le has pasado a1,a2
		int pos2 = th.get(a2);
		boolean aurkitua = false;
		boolean[] aztertuak = new boolean[th.size()]; //aztertuak es el boolean que utilizo para ver si ya
													  //he mirado una url o no
		
		aztertuGabeak.add(pos1);		//al principio metemos la primera url en la lista de los que 
										//no han sido actulizados.
		aztertuak[pos1]=true;

		
		while(!aztertuGabeak.isEmpty() && !aurkitua) {
			Integer a = aztertuGabeak.remove();
			if(a.equals(pos2)) {					//Despues aqui miramos si el elemento que hemos sacado del
				aurkitua=true;						//aztertugabeak es el mismo que pos2 es decir miramos los 
													//identificadores
			}
			else {
				for(int i = 0;i<adjList[a].size();i++) {			//Aqui empezamos un loop que va a recorrer 
																	//el adjList que contiene todas las url
																	//que redirecciona a1
					if(aztertuak[adjList[a].get(i)] == false) {	//Despues miramos si ese identificador de 
																	//la url ya se ha procesado
																	//y si no es asi lo metemos en nuestra lista
																	//de los que no han sido procesados y 
																	//ponemos que ha sido procesado para que no
																	//se vuelva a procesar
						aztertuGabeak.add(adjList[a].get(i));
						aztertuak[adjList[a].get(i)]=true;
					}
				}
			}
		}
		
		return aurkitua;
	}
	
	

	public ArrayList<String> erlazioBide(String a1,String a2){
		
		Queue<Integer> aztertuGabeak = new LinkedList<Integer>();
		ArrayList<String> emaitza=new ArrayList<String>();
		
		if(this.erlazionatuta(a1, a2)) {

			int pos1 = th.get(a1);		
			int pos2 = th.get(a2);
			boolean aurkitua = false;
			boolean[] aztertuak = new boolean[th.size()]; 
			emaitza.add(a1);
														
			aztertuGabeak.add(pos1);		
			aztertuak[pos1]=true;
	
			while(!aztertuGabeak.isEmpty() && !aurkitua) {
				Integer a = aztertuGabeak.remove();
				
				if(a.equals(pos2)) {					
					aurkitua=true;
				}
				else {
					for(int i = 0;i<adjList[a].size();i++) {	
						if(aztertuak[adjList[a].get(i)] == false) {	
							aztertuGabeak.add(adjList[a].get(i));
							aztertuak[adjList[a].get(i)]=true;
							emaitza.set(i,this.keys[a]);
						}
					}
				}
			}
			
		}
		
		return emaitza;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//LABORATEGI BERRIA
	
	public HashMap<String,Double> pageRank() {
		
		//Exekuzio denbora kalkulatzeko
		long hasiera = System.currentTimeMillis();
		
		//Ea iterazio gehiago ala ez
		double diferentziaAbsolutua = 0.0;
		
		int grafoarenLuzeera=keys.length;
		HashMap<String,Double> zaharra = new HashMap<String,Double>();
		HashMap<String,Double> berria = new HashMap<String,Double>();
		double haFormula = (double)(1.0/(double)grafoarenLuzeera);
		
		final double dumpingFactor = 0.85;
		double itFormula = (1-dumpingFactor)/grafoarenLuzeera;
		boolean lehena = true;
		
		double unekoRank = 0.0;
		
		if(grafoarenLuzeera>0) {
		
			for(int i = 0;i<grafoarenLuzeera;i++) {
				zaharra.put(this.keys[i], haFormula);
			}
			
			int kont=0;
			while(kont<30  || lehena) {
				lehena = false;
				
				for(int j = 0; j<grafoarenLuzeera;j++) {
					
					for(int i: this.adjListAlde[j]) {
						unekoRank += zaharra.get(this.keys[i])/this.adjList[i].size();
					}
					unekoRank *= dumpingFactor;
					unekoRank += itFormula;
					berria.put(this.keys[j],unekoRank);
					unekoRank = 0.0;
	
				}
				
				for (int i=0;i<grafoarenLuzeera;i++) {
					diferentziaAbsolutua += Math.abs(zaharra.get(this.keys[i])-berria.get(this.keys[i]));
				}
				
				HashMap<String,Double> aux = zaharra;
				zaharra = berria;
				berria = aux;
				kont++;
				
				System.out.println("iterazioa");
			}
			
			long bukaera = System.currentTimeMillis();
			double denbora=(double)((bukaera-hasiera)/1000);
			System.out.println(denbora);
		}	
		return berria;
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////
	
	//QUICKSORT
	/////////////////////////////////////////////////////////////////////////////////////////
	public void quickSort(ArrayList<Bikote > lista,int hasiera, int bukaera) {
		if(bukaera-hasiera>0) {
			
			int z = this.zatiketa(lista,hasiera,bukaera);
			this.quickSort(lista,hasiera,z-1);
			this.quickSort(lista,z+1,bukaera);

		}
	}
	
	private int zatiketa(ArrayList<Bikote> lista,int i, int f) {
		String lag=lista.get(i).getWeb();
		int ezker =i;
		int eskuin=f;
		
		while(ezker<eskuin) {
			while(lista.get(ezker).getWeb().compareTo(lag)<=0 && ezker<eskuin) {
				ezker++;
			}
			while(lista.get(eskuin).getWeb().compareTo(lag)>0) {
				eskuin--;
			}
			if(ezker<eskuin) {
				this.swap(lista, ezker,eskuin);
			}
		}
		lista.set(i, lista.get(eskuin));
		WebOrria web = WebOrriak.getNireWebOrriak().getWebOrria(lag);
		Bikote b = new Bikote(web.getUrl(),this.pageRank().get(web.getUrl()));
		lista.set(eskuin,b);
		
		return eskuin;
	}
	
	private void swap(ArrayList<Bikote> lista,int ezker, int eskuin) {
		Bikote w=lista.get(ezker);
		lista.set(ezker,lista.get(eskuin));
		lista.set(eskuin, w);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public ArrayList<Bikote> bilatzaileBat(String gakoHitz){
		// Post: Emaitza emandako gako-hitza duten web-orrien zerrenda da,
		//berepagerank-aren   arabera   handienetik   txikienera   ordenatuta
		//(hau   da,lehenengo posizioetan pagerank handiena duten web-orriak agertuko dira)
		
		Gakoak gakoak = new Gakoak();
		ArrayList<Bikote> emaitza = new ArrayList<Bikote>();
		gakoak.listaKargatu();
		
		for(int i = 0;i<gakoak.getLista().size();i++) {
			gakoak.getLista().get(i).kargatuLista();
		}
	
		if(gakoak.gakoaLortu(gakoHitz).equals(null))
			System.out.println("Sartu duzun gakoa ez dago words.txt dokumentuaren barne");
		
		else {
		
			Gakoa gakoa = gakoak.gakoaLortu(gakoHitz);			//Lehenik gako hitzen lista kargatuko dut
																	//Hemen gakoHitz(izena) hori duen gakoa lortu egiten dut gakoaLortu() metodoaren
																			//bitartez, eta ondoren gako horrek berarekin erlazionaturik dituen url-en lista
																			//kargatzen du	
			ArrayList<WebOrria> webOrrienLista = new ArrayList<WebOrria>();
			webOrrienLista = gakoa.getLista();
																					//hemen emaitza aldagaiaren barnean gakoa gako horrek berarekin erlazionaturik dituen url-en
																					//lista gordetzen du
			for (int i = 0;i<webOrrienLista.size();i++) {
				Double pr = this.pageRank().get(webOrrienLista.get(i).getUrl());
				Bikote bikote = new Bikote(webOrrienLista.get(i).getUrl(),pr);
				emaitza.add(bikote);
			}
			
			this.quickSort(emaitza, 0, emaitza.size()-1);
			
		}
		
		
		return emaitza;
	}
	
	
	public ArrayList<Bikote> bilatzaileBi(String gakoHitz1,String gakoHitz2){
		// Post: Emaitza emandako gako-hitzak dituzten web-orrien zerrenda da,
		//bere pagerank-aren arabera handienetik txikienera ordenatuta (hau da,
		//lehenengo posizioetan pagerank handiena duten web-orriak agertuko dira)
		
		
		Gakoak g1 = new Gakoak();
		Gakoak g2 = new Gakoak();
		ArrayList<Bikote> emaitza = new ArrayList<Bikote>();
		g1.listaKargatu();	
		g2.listaKargatu();	
		
		for(int i = 0;i<g1.getLista().size();i++) {
			g1.getLista().get(i).kargatuLista();
		}
		
		for(int i = 0;i<g2.getLista().size();i++) {
			g2.getLista().get(i).kargatuLista();
		}
		
		if(g1.gakoaLortu(gakoHitz1).equals(null) || g2.gakoaLortu(gakoHitz2).equals(null))	
			System.out.println("Sartutako gakoren bat ez dago words.txt dokumentuaren barne");
		else {
			g1.gakoaLortu(gakoHitz1).kargatuLista();	
			g2.gakoaLortu(gakoHitz2).kargatuLista();						
			
			ArrayList<WebOrria> e1 = new ArrayList<WebOrria>();	
			ArrayList<WebOrria> e2 = new ArrayList<WebOrria>();
			e1 = g1.gakoaLortu(gakoHitz1).getLista();	
			e2 = g2.gakoaLortu(gakoHitz2).getLista();		
			
			for (int i = 0;i<e1.size();i++) {
				Double pr = this.pageRank().get(e1.get(i).getUrl());
				Bikote bikote = new Bikote(e1.get(i).getUrl(),pr);
				emaitza.add(bikote);
			}
			
			for (int i = 0;i<e2.size();i++) {
				Double pr = this.pageRank().get(e2.get(i).getUrl());
				Bikote bikote = new Bikote(e2.get(i).getUrl(),pr);
				emaitza.add(bikote);
			}
			
			this.quickSort(emaitza, 0, emaitza.size()-1);
			
		}
		return emaitza;
	}
	
}