package database;

@SuppressWarnings("serial")
public class EmptySetException extends Exception {

	public EmptySetException() {
	}

	public EmptySetException(String arg0) {
		super(arg0);
	}

	public EmptySetException(Throwable arg0) {
		super(arg0);
	}

	public EmptySetException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EmptySetException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
