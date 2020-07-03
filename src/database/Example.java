package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Example implements Comparable<Example>, Iterable<Object> {

	private List<Object> example = new ArrayList<Object>();

	public void add(Object o) {
		example.add(o);
	}

	public Object get(int i) {
		return example.get(i);
	}

	@Override
	public int compareTo(Example ex) {

		int i = 0;
		for (Object o : ex.example) {
			if (!o.equals(this.example.get(i)))
				return ((Comparable) o).compareTo(example.get(i));
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

	//TODO: chiedere se va bene
	public void set(int k, Object o) {
		example.set(k, o);
	}

}