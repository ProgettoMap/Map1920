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

    public TrainingDataException() { }

    public TrainingDataException(String message) { super(message); }

}

