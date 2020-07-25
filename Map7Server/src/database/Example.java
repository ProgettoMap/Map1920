package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Modella una transazione letta dalla base di dati.
 */
public class Example implements Comparable<Example>, Iterable<Object> {

	private List<Object> example = new ArrayList<Object>();

	void add(Object o) {
		example.add(o);
	}

	public Object get(int i) {
		return example.get(i);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" }) // I warning sono stati soppressi poichè non ritenuti necessari.
													// Nello specifico, il warning 'rawtypes' è stato soppresso perchè
													// quando il parametro non viene passato in input ad un'interfaccia
													// generica,
													// di default viene preso il tipo Object (tramite inferenza).
													// Per quanto riguarda il warning 'unchecked' invece, siamo sicuri
													// di passare alle funzioni i tipi corretti (in questo caso, Object).
	@Override
	public
	int compareTo(Example ex) {
		int i = 0;
		for (Object o : ex.example) {
			if (!o.equals(this.example.get(i))) {
				return ((Comparable) o).compareTo(example.get(i));
			}
			i++;
		}
		return 0;
	}

	@Override
	public String toString() {
		String str = "";
		for (Object o : example)
			str += o.toString() + " ";
		return str;
	}

	@Override
	public Iterator<Object> iterator() {
		return null;
	}

	// Metodo implementato per inserire un oggetto all'interno dell'arrayList
	/**
	 * @param k - Indice dell'arraylist nella quale si vuole inserire / rimpiazzare l'oggetto
	 * @param o - Oggetto da inserire all'interno dell'arrayList
	 */
	public void set(int k, Object o) {
		example.set(k, o);
	}

}