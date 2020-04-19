	/**
	 * La classe modella un generico attributo discreto o continuo. 
	 * 
	 */
public abstract class Attribute {
	
	String name; // nome simbolico dell'attributo
	int index;	// Identificativo numerico dell'attributo
	
	/**
	 *  E' il costruttore di classe. Inizializza i valori dei membri  name,  index
	 * @param name
	 * @param index
	 */
	public Attribute(String name,int index) {

		this.index=index;
		this.name=name;		
		
	}
	/**
	 * Restituisce il nome simbolico dell'attributo
	 * @return name
	 */
	String getName() {
		return name;
	}
	
	/**
	 * Restituisce il valore nel membro index; 
	 * @return index
	 */
	int getIndex() {
		return index;
	}
	
}
