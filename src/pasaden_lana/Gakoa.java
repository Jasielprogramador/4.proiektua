package pasaden_lana;
import java.util.ArrayList;
import java.util.Iterator;

public class Gakoa {
	//atributuak
	private String izena;
	private ArrayList<WebOrria> lista;

	
	//eraikitzailea
	public Gakoa(String pIzena) {
		this.izena=pIzena;
		this.lista=new ArrayList<WebOrria>();
	}
	
	public ArrayList<WebOrria> getWebOrriak(){
		return this.lista;
	}
	
	//Te da los gakoas que estan dentro de una url
	public boolean webGako(String s) {
		if(s.contains(this.izena)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getIzena() {
		return this.izena;
	}
	
	public void kargatuLista() {
		WebOrriak.getNireWebOrriak().gakoaWebOrriak(this.izena);
	}
	
	
	
	
	
}
