package data;

import java.io.Serializable;

/**
 * Estende la classe Attribute e rappresenta un attributo continuo
 *
 */
//NOTE: Attributi numerici (es. 1,3,5...)
@SuppressWarnings("serial")
public class ContinuousAttribute extends Attribute implements Serializable {

	/**
	 * Invoca il costruttore della super-classe
	 *
	 * @param name  Nome dell'attributo
	 * @param index Indice dell'attributo
	 */
	public ContinuousAttribute(String name, int index) {
		super(name, index);
	}

	@Override
	public String toString() {
		return getName();
	}

}
