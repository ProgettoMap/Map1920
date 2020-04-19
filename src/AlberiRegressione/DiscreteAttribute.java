package AlberiRegressione;

/**
 * Rappresenta un attributo discreto
 */
public class DiscreteAttribute extends Attribute {

	// Rappresenta l'insieme di valori discreti che l'attributo può assumere
	String values[];

	/**
	 * Invoca il costruttore della super-classe e avvalora l'array values[] con i
	 * valori discreti in input.
	 * 
	 * @param name   Nome simbolico dell'attributo
	 * @param index  Indice dell'attributo
	 * @param values Valori discreti
	 */
	DiscreteAttribute(String name, int index, String[] values) {
		super(name, index);
		this.values = values;
	}

	/**
	 * 
	 * @return Cardinalit� dell'array values (numero di valori discreti)
	 */
	int getNumberOfDistinctValues() {
		return values.length;
	}

	/**
	 * Metodo che prende in input un indice i di un solo valore discreto
	 * 
	 * @param i
	 * @return i-esimo valore discreto dell'array values[]
	 */
	String getValue(int i) {

		return values[i];

	}

	@Override
	public String toString() {

		String values_concat = "";
		for (String s : values) {
			values_concat += s + " ";
		}
		return values_concat;

	}
}
