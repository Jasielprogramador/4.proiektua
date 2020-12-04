package lana;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
	
	
	private HashMap<String,Double> pageRank;

	
	//Eraikitzailea
	public Graph(){
	}
	
	public void grafoaSortu(WebOrriak lista) throws IOException{
		
		if(!lista.equals(null)) {
			// Post: web-en zerrendatik grafoa sortu
			//Nodoak web-en url-ak dira
			
	        // 1. pausua:  “th” bete
	        // KODEA INPLEMENTATU
			
			this.th=new HashMap<String,Integer>();
			keys = new String[lista.luzeera()];
			this.adjList= new ArrayList[lista.luzeera()];
			
			for (int i = 0;i<lista.luzeera();i++) {
				
				//1.pausua th bete
				this.th.put(lista.getLista().get(i).getUrl(),i);
				
				// 2. pausua: “keys” bete
				this.keys[i]=lista.getLista().get(i).getUrl();
			}
			
			//hasieratzeko
			for (int z = 0;z<lista.luzeera();z++) {
				this.adjList[z]=new ArrayList<Integer>();
			
			}
			
			for ( int a = 0;a<lista.luzeera();a++) {
				
				for (int h = 0;h<lista.getLista().get(a).getListaOrriak().size();h++) {
					
					// 3. pausua: “adjList” bete 
					int arku = this.th.get(lista.getLista().get(a).getListaOrriak().get(h).getUrl());
					this.adjList[a].add(arku);
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
				emaitza.add(this.keys[a]);
				
				if(a.equals(pos2)) {					
					aurkitua=true;
				}
				else {
					for(int i = 0;i<adjList[a].size();i++) {	
						if(aztertuak[adjList[a].get(i)] == false) {	
							aztertuGabeak.add(adjList[a].get(i));
							aztertuak[adjList[a].get(i)]=true;
						}
					}
				}
			}
			
		}
		
		return emaitza;
	}
	
	
	private void pageRankaSortu() {

		HashMap<String,Double> zaharra = new HashMap<String,Double>();
		HashMap<String,Double> berria =  new HashMap<String,Double>();
		double baturaAbsolutua = 0.0;
		int grafoarenLuzeera=keys.length;
		double hasierakoFor = (double)1/grafoarenLuzeera;
		double amaierakoFor = ((1-0.85)/grafoarenLuzeera)+0.85;
		
		for(int i = 0;i<grafoarenLuzeera;i++) {
			zaharra.put(keys[i],hasierakoFor);
		}
		
		int i = 1;
		while(baturaAbsolutua >= 0.0001) {
			for(int j = 0; j<grafoarenLuzeera;j++) {
				if(!berria.get(keys[i]).equals(null))
					berria.put(keys[i],zaharra.get(j)/keys[j].length());
				else {
					berria.put(keys[i],berria.get(keys[i])+zaharra.get(j)/keys[j].length());
				}
				baturaAbsolutua = baturaAbsolutua + Math.abs(berria.get(j)-zaharra.get(j));
			}
			i++;
			baturaAbsolutua = baturaAbsolutua * amaierakoFor;
		}
	}
	
	private String[] urlOrdenatuQuickSort(String arr[], int l, int r) {
		int hasiera = l;
		int amaiera = r;
		if (r - l > 0) {
			String pibotea = arr[l];
			while (amaiera > hasiera) {
				while (arr[hasiera].compareTo(pibotea) <= 0 && hasiera <= r	 && amaiera > hasiera) hasiera++;
				while(arr[amaiera].compareTo(pibotea) > 0 && amaiera >= l && amaiera >= hasiera) amaiera--;
				if (amaiera > hasiera) {
					aldatu(arr, hasiera, amaiera);
					}
			}
			aldatu(arr, l, amaiera);
			urlOrdenatuQuickSort(arr, l, amaiera - 1);
			urlOrdenatuQuickSort(arr, amaiera + 1, r);	
		} 
		return arr;
				
	}
	private void aldatu(String arr[], int i, int j) {
		//pre: 0 <= i < arr.length; 0 <= j < arr.length; arr != null
		//post: arr Array<String>-eko i eta j posizioetan dauden string-ak trukatzen ditu.
		String temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	public ArrayList<Bikote> bilatzailea(String gakoHitz){
		// Post: Emaitza emandako gako-hitza duten web-orrien zerrenda da,
		//berepagerank-aren   arabera   handienetik   txikienera   ordenatuta
		//(hau   da,lehenengo posizioetan pagerank handiena duten web-orriak agertuko dira)
		
		Gakoak gakoak = new Gakoak();
		gakoak.listaKargatu();											//Lehenik gako hitzen lista kargatuko dut
		gakoak.gakoaLortu(gakoHitz).kargatuLista();						//Hemen gakoHitz(izena) hori duen gakoa lortu egiten dut gakoaLortu() metodoaren
																		//bitartez, eta ondoren gako horrek berarekin erlazionaturik dituen url-en lista
																		//kargatzen du
		
		ArrayList<WebOrria> emaitza = new ArrayList<WebOrria>();		
		emaitza = gakoak.gakoaLortu(gakoHitz).getLista();				//hemen emaitza aldagaiaren barnean gakoa gako horrek berarekin erlazionaturik dituen url-en
																		//lista gordetzen du
		
		
		
		//hemen doa quicksort algoritmoa 
		
		
		
		
		
	}
	
	
	public ArrayList<Bikote> bilatzailea(String gakoHitz1,String gakoHitz2){
		// Post: Emaitza emandako gako-hitzak dituzten web-orrien zerrenda da,
		//bere pagerank-aren arabera handienetik txikienera ordenatuta (hau da,
		//lehenengo posizioetan pagerank handiena duten web-orriak agertuko dira)
		
		
		Gakoak g1 = new Gakoak();
		Gakoak g2 = new Gakoak();
		g1.listaKargatu();	
		g2.listaKargatu();												//Lehenik gako hitzen lista kargatuko dut
		g1.gakoaLortu(gakoHitz1).kargatuLista();	
		g2.gakoaLortu(gakoHitz2).kargatuLista();						//Hemen gakoHitz(izena) hori duen gakoa lortu egiten dut gakoaLortu() metodoaren
																		//bitartez, eta ondoren gako horrek berarekin erlazionaturik dituen url-en lista
																		//kargatzen du
		
		ArrayList<WebOrria> e1 = new ArrayList<WebOrria>();	
		ArrayList<WebOrria> e2 = new ArrayList<WebOrria>();
		e1 = g1.gakoaLortu(gakoHitz1).getLista();	
		e2 = g2.gakoaLortu(gakoHitz2).getLista();						//hemen emaitza aldagaiaren barnean gakoa gako horrek berarekin erlazionaturik dituen url-en
																		//lista gordetzen du
		
		
		//hemen doa quicksort algoritmoa
		
	}
}