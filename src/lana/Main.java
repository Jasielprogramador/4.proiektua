package lana;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import pasaden_lana.Gakoa;
import pasaden_lana.Gakoak;
import pasaden_lana.Teklatua;
import pasaden_lana.WebOrria;
import pasaden_lana.WebOrriak;


public class Main {
	
	
	public static void getWebOrria(WebOrriak webLista,String w) {
		//CONSEGUIR UNA WEBORRIA
		WebOrria web=webLista.getWebOrria(w);
		if (web == null)
			System.out.println("Ez dago web orria.");
		else 
			System.out.print("Lortutako web orria: "+web.getUrl());
	}
	
	public static void webOrriTxertatu(WebOrriak webLista,WebOrria w) {
		//METER UNA WEBORRIA
		if(webLista.getWebOrria(w.getUrl())!=null) {
			System.out.println("WebOrria jada zerrendaren barruan dago");
		}
		else {
			webLista.webOrriTxertatu(w);
		}
		
		if(webLista.getWebOrria(w.getUrl())!=null) {
			System.out.println("Txertatuta");
		}
	}
		
	public static void webOrriKendu(WebOrriak webLista,WebOrria w) {
		//QUITAR UNA WEBORRIA 
		if(w== null || webLista.getWebOrria(w.getUrl())==null) {
			System.out.println("WebOrria ez dago zerrendan");
		}
		else {
			webLista.webOrriKendu(w);
			if(webLista.getWebOrria(w.getUrl())==null) {
				System.out.println("Ezabatuta");
			}
		}
	}
	
	public static void estekak(WebOrriak webLista,WebOrria w) {
		//MIRAR QUE WEBORRIS REDIRECCIONA UNA PAGINA
		ArrayList<String> e=w.irteerakoEstekak();
				
		for(int i=0;i<e.size();i++) {	
			System.out.println(e.get(i));		
		}
				
	}
	
	public static void hitzak(WebOrriak webLista,Gakoa gakoa) {
		//Gako-hitz bat emanda, gako-hitz hau duten web-orrien zerranda bueltatu 
		ArrayList<String> o=webLista.word2Webs(gakoa.getIzena());
				
		for(int i=0;i<o.size();i++) {
			System.out.println(o.get(i));		
		}
	}
	
	
	public void ordenatu(WebOrriak webLista) {
		//ORDENAR EL DOCUMENTO
		//webLista.webOrde
		ArrayList<String> o=webLista.getUrlLista();
				
		for(int i=0;i<o.size();i++) {
					
			System.out.println(webLista.getUrlLista().get(i));
					
		}
	}
	
	
	public static void idatziDokumentuan(WebOrriak webLista) {
		//METER EN EL DOCUMENTO
				
		try {
			webLista.dokumentuaSortu();
		}
		catch (IOException p){
			p.toString();
		}
	}	
	
	public static void main(String[] args) throws IOException {
		
		//Deiak
		Graph grafoa = new Graph();
		WebOrriak zerrenda = WebOrriak.getNireWebOrriak();
		Teklatua teklatua = Teklatua.getNireTeklatua();
		
		//Metodo barneko aldagaiak
		boolean irten = false;
		zerrenda.webOrriakKargatu();
		zerrenda.webOrrienErlazioakKargatu();

		
		Gakoak gakoak=new Gakoak();
		gakoak.listaKargatu();
		
		
		while (!irten) {
			System.out.println("0.Irtetzeko");
			System.out.println("1.WebOrri bat lortu");
			System.out.println("2.WebOrri bat txertatu");
			System.out.println("3.WebOrri bat ezabatu");
			System.out.println("4.WebOrri batek estekatzen dituen beste web orriak lortu");
			System.out.println("5.Gako-hitz hau duten web-orrien zerranda bueltatu ");
			System.out.println("6.WebOrrien lista ordenatu");
			System.out.println("7.WebOrrien zerrenda dokumentu batean idatzi");
			System.out.println("8.Grafoa kargatu");
			System.out.println("9.Grafoa inprimatu");
			System.out.println("10.Graph klasearen erlazionatuta metodoa"); 
			System.out.println("11.Graph klasearen erlazioBide metodoa");
			
			int zenbakia=teklatua.irakurriZenb();
			WebOrria web=null;
		
			if(zenbakia==1) {
				System.out.println("Zein WebOrri lortu nahi duzu?");
				String text = teklatua.irakurriString();
				getWebOrria(zerrenda,text);
				
			}
			else if(zenbakia==2) {
				System.out.println("Zein Web Orri txertatu nahi duzu?"); 
				String text = teklatua.irakurriString();
				web = new WebOrria(text,zerrenda.luzeera());
				webOrriTxertatu(zerrenda,web);
			}
			else if(zenbakia==3) {
				System.out.println("Zein WebOrri ezabatu nahi duzu?"); 
				String text = teklatua.irakurriString();
				
				web=zerrenda.getWebOrria(text);
				webOrriKendu(zerrenda,web);
			}
			else if(zenbakia==4) {
				System.out.println("Proba, WebOrria(0-euro-handys.de,22)-rekin egingo dugu "); 
				web=new WebOrria("0-euro-handys.de", 22);
				estekak(zerrenda,web);
			}
			else if(zenbakia==5) {
				System.out.println("Proba, abaddon gakoarekin egingo dugu"); 
				Gakoa gakoa=new Gakoa("abaddon");
				hitzak(zerrenda,gakoa);
			}
			else if(zenbakia==6) {
				zerrenda.quickSort(zerrenda.getLista(),0,WebOrriak.getNireWebOrriak().luzeera());
				int i = 0;
			}
			else if(zenbakia==7) {
				System.out.println("Proba, url_lista,txt izeneko dokumentu batekin egin dugu"); 
				idatziDokumentuan(zerrenda);
			}
			else if(zenbakia == 8) {
				grafoa.grafoaSortu(zerrenda);
				System.out.println("Grafoa kargatu egin da");
			}
			else if(zenbakia==9) {
				grafoa.print();
			}
			else if(zenbakia==10) {
				System.out.println("Sartu root bezala hartu nahi duzun url");
				String ulr1=teklatua.irakurriString();
				System.out.println("Sartu helburu bezala nahi duzun url-a");
				String url2=teklatua.irakurriString();
				System.out.println("EMAITZA "+grafoa.erlazionatuta(ulr1, url2));
			}
			else if(zenbakia==11) {
				System.out.println("Sartu root bezala hartu nahi duzun url");
				String ulr1=teklatua.irakurriString();
				System.out.println("Sartu helburu bezala nahi duzun url-a");
				String url2=teklatua.irakurriString();
				ArrayList<String> emaitza = grafoa.erlazioBide(ulr1, url2);
				
				if(emaitza.size()<=1) {
					System.out.println("Url-ak ez daude erlazionatuta");
				}
				else {
					for (int i = 0;i<emaitza.size();i++) {
						System.out.println(i+": "+emaitza.get(i));
					}
				}
				
			}
			else if(zenbakia == 0) {
				irten = true;
			}
		}
		
	}
}
