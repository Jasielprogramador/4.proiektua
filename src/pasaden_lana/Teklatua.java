package pasaden_lana;

import java.util.Scanner;

public class Teklatua{
	//atributua
	private Scanner sc;
	private static Teklatua nireTeklatua;
	
	//eraikitzailea
	private Teklatua (){
		this.sc=new Scanner (System.in);
	}
	
	//metodoak
	public static Teklatua getNireTeklatua(){
		if (nireTeklatua==null){
			nireTeklatua=new Teklatua();
		}
		return nireTeklatua;
	}
	
	public int irakurriZenb() throws NumberFormatException {
		String sar=this.sc.nextLine();
		int zenb= Integer.parseInt(sar);
		return zenb;
	}
	
	public String irakurriString()  {
		Scanner sc= new Scanner(System.in);
		String text= sc.next();
		return text;
	}
	
}