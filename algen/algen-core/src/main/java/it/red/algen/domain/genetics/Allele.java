package it.red.algen.domain.genetics;

public class Allele<T> {
	
	public T value;
	
	public boolean dominant;
	
	public String encode(){
		return value.toString(); // TODOA: encoding in base al tipo
	}
	
	 // TODOA: change to copy() all clone()!
	public Allele<T> copy(){
		Allele<T> result = new Allele<T>();
		result.value = value;
		result.dominant = dominant;
		return result;
	}
}
