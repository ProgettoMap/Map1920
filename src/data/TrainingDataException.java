package data;


/**
 * Gestisce le eccezioni causate da acquisizione errata del file:
 * 
 * - File inesistente
 * 
 * - Schema mancante
 *
 * - Training set vuoto
 * 
 * - Training set privo di variabile target numerica
 */
@SuppressWarnings("serial")
public class TrainingDataException extends Exception {

    public TrainingDataException() {
    }

    public TrainingDataException(String message) {
	super(message);
    }

    public TrainingDataException(Throwable cause) {
	super(cause);
    }

    public TrainingDataException(String message, Throwable cause) {
	super(message, cause);
	// TODO Auto-generated constructor stub
    }

    public TrainingDataException(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}

