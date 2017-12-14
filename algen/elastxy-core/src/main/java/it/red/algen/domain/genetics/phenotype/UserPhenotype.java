package it.red.algen.domain.genetics.phenotype;

public class UserPhenotype<U> implements Phenotype<U> {
	public U value;
	
	@Override
	public U getValue() {
		return value;
	}
	
	
	public UserPhenotype<U> copy(){
		UserPhenotype<U> result = new UserPhenotype<U>();
		result.value = value;
		return result;
	}


	@Override
	public String toString() {
		return String.format("(UserPhenotype) %s", value==null?"N/A":value.toString());
	}
}
