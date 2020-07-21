package database;

public class Column {
	private String name;
	private String type;

	public Column(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getColumnName() {
		return name;
	}

	public String getTypeName() { //TODO: Aggiunto, controllare se possibile ottenerlo in qualche modo
		return type;
	}

	public boolean isNumber() {
		return type.equals("number");
	}

	public boolean isString() {
		return type.equals("string");
	}


	@Override
	public String toString() {
		return name + ":" + type;
	}
}