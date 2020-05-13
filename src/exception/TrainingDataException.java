package exception;

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
public class TrainingDataException extends Exception {

    public TrainingDataException() {

    }

    public TrainingDataException(String arg0) {
	System.out.println(arg0);
    }

}
