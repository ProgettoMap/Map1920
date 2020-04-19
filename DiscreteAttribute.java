/**
 * Rappresenta un attributo discreto
 */
public class DiscreteAttribute extends Attribute {

	// Rappresenta l'insieme di valori che l'attributo può assumere
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
		super(name, index, values);
	}

	/**
	 * 
	 * @return Conta i valori discreti
	 */
	int getNumberOfDistinctValues() {
		return values.length();
	}

	/**
	 * Indice di un valore discreto
	 * 
	 * @param i
	 * @return I-esimo valore discreto
	 */
	String getValue(int i) {

		return values[i];

	}

}
