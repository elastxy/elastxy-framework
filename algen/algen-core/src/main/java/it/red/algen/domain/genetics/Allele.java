package it.red.algen.domain.genetics;

public class Allele<T> {
	
	public T value;
	
	public boolean dominant;
	
	public String encode(){
		return value.toString(); // TODOM: encoding based on type
	}

	public Allele<T> copy(){
		Allele<T> result = new Allele<T>();
		result.value = value;
		result.dominant = dominant;
		return result;
	}
	
	@Override
	public String toString() {
		return "Allele [value=" + value + ", dominant=" + dominant + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Allele other = (Allele) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
