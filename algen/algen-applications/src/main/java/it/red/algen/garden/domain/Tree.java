package it.red.algen.garden.domain;

/**
 * 
Ogni pianta ha le seguenti caratteristiche, in base al tipo:
Pa = richiesta di acqua: alta, media, bassa, nessuna
Ps = richiesta di sole: alta, media, bassa, nessuna
Pv = sofferenza al vento: totale, alta, bassa, nessuna
 * @author Gabriele
 *
 */
public class Tree {
	private String code;

	private int sunRequest;	// 0,1,2
	private int wetAllowed; // 0,1,2
	private int windAllowed;// 0,1,2

	public Tree(String code, int sunRequest, int wetRequest, int windAllowed) {
		super();
		this.code = code;
		this.sunRequest = sunRequest;
		this.wetAllowed = wetRequest;
		this.windAllowed = windAllowed;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public int getWetAllowed() {
		return wetAllowed;
	}
	public void setWetAllowed(int wetRequest) {
		this.wetAllowed = wetRequest;
	}
	public int getSunRequest() {
		return sunRequest;
	}
	public void setSunRequest(int sunRequest) {
		this.sunRequest = sunRequest;
	}
	public int getWindAllowed() {
		return windAllowed;
	}
	public void setWindAllowed(int windAllowed) {
		this.windAllowed = windAllowed;
	}
	
	public static final int getWorstSituation(){
		return 2+2+2;
	}
	
	public String toString(){
		String result = "";
		
		if(sunRequest==0) result += "_";
		else if(sunRequest==1) result += "s";
		else if(sunRequest==2) result += "S";
		
		if(wetAllowed==0) result += "_";
		else if(wetAllowed==1) result += "a";
		else if(wetAllowed==2) result += "A";
		
		if(windAllowed==0) result += "_";
		else if(windAllowed==1) result += "v";
		else if(windAllowed==2) result += "V";
		
		return "["+result+"]"+code;
	}
}