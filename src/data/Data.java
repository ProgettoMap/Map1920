package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Modella l'insieme di esempi di training
 */
public class Data {

	private Object data[][]; // Matrice nXm di tipo Object che contiene il training set organizzato come
	// numberOfExamples X numberAttribute

	private int numberOfExamples; // Cardinalita'  del training set

	// NOTE: E' la riga letta nel traning set nel formato Attribute es motor
	// A,B,C,D,E
	private Attribute explanatorySet[]; // Array di oggetti di tipo Attribute per rappresentare gli attributi
	// indipendenti di tipo discreto

	private ContinuousAttribute classAttribute; // Oggetto per modellare l'attributo di classe ContinuousAttribute

	public Data(String fileName) throws TrainingDataException { // ATTENZIONE! NON MODIFICARE L'intestazione! Deve
		// essere "public Data(String fileName) throws
		// TrainingDataException"

		System.out.println("Starting data acquisition phase:\n");

		boolean isTagTargetFound = false;
		boolean isTagDescFound = false;

		File inFile = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(inFile);
			String line = sc.nextLine();
			if (!line.contains("@schema")) {
				sc.close();
				throw new TrainingDataException("Errore nel training set. Attributo @schema non trovato.");
			}

			String s[] = line.split(" ");
			explanatorySet = new Attribute[new Integer(s[1])];
			short iAttribute = 0;
			line = sc.nextLine();
			while (!line.contains("@data")) {
				if (sc.hasNextLine()) { // Finchè ci sono righe nel file
					s = line.split(" ");

					if (s[0].equals("@desc") && iAttribute <= explanatorySet.length - 1) { // aggiungo l'attributo allo
																							// spazio descrittivo
						// @desc motor discrete A,B,C,D,E
						isTagDescFound = true;
						String discreteValues[] = s[2].split(",");
						explanatorySet[iAttribute] = new DiscreteAttribute(s[1], iAttribute, discreteValues);
					} else if (s[0].equals("@target")) {
						isTagTargetFound = true;
						classAttribute = new ContinuousAttribute(s[1], iAttribute);
					} else if (iAttribute > explanatorySet.length - 1) {
						throw new TrainingDataException(
								new ArrayIndexOutOfBoundsException().toString() + ": Tag @data non trovato");
					}

					iAttribute++;
					line = sc.nextLine();
				} else {
					throw new TrainingDataException(
							new NoSuchElementException().toString() + ": Numero di tag @desc maggiore ");
				}

			}
			// TODO: indicare numero di riga dell'errore

			// TODO: errore a runtime se esiste un tag non valido (quindi qualsiasi stringa
			// dopo il tag @ che non sia schema, desc, data, o target genero errore

			if (!isTagTargetFound) {
				throw new TrainingDataException("Tag @target non trovato");
			}
			if (!isTagDescFound) {
				throw new TrainingDataException("Tag @desc non trovato");
			}
			if (explanatorySet[explanatorySet.length - 1] == null) {
				throw new TrainingDataException(
						new NoSuchElementException().toString() + ": Numero di tag @desc minore");
			}

			/*
			 * TODO: gestire l'eccezione dei seguenti casi:
			 * 
			 * - Mancanza dell'attributo schema (fatto)
			 * 
			 * - Mancanza dell'attributo @desc (fatto)
			 * 
			 * - Mancanza dell'attributo @target (fatto)
			 * 
			 * - Mancanza dell'attributo @data (fatto)
			 * 
			 * - Numero di attributi diverso da attributi desc (fatto)
			 * 
			 * - Valori in data diversi dal valore dell'attributo
			 * 
			 * - Numero di attributi nel training set diverso dagli attributi specificati
			 * nel training set (fatto)
			 * 
			 * - Attributo di classe non di tipo float.(fatto)
			 */

			// avvalorare numero di esempi
			// @data 167
			numberOfExamples = new Integer(line.split(" ")[1]);

			// popolare data
			data = new Object[numberOfExamples][explanatorySet.length + 1];
			short iRow = 0;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				// assumo che attributi siano tutti discreti
				s = line.split(","); // E,E,5,4, 0.28125095

				if (iRow >= numberOfExamples)
					throw new TrainingDataException(
							new ArrayIndexOutOfBoundsException().toString() + "I valori letti sono diversi dal parametro @data.");
				
					if (!isDouble(s[s.length - 1]) || s[s.length-1]==null) {
						throw new TrainingDataException("Valore target non double");
					}

				for (int jColumn = 0; jColumn < s.length - 1; jColumn++) {					
					data[iRow][jColumn] = s[jColumn];
				}

				data[iRow][s.length - 1] = new Double(s[s.length - 1]);
				iRow++;

			}

			if (iRow != numberOfExamples)
				throw new TrainingDataException("I valori letti sono diversi dal parametro @data.");

		} catch (FileNotFoundException e) {
			throw new TrainingDataException(e.toString());
		} finally {
			if (sc != null) // Chiudo lo scanner solo se viene trovato il file
				sc.close();
		}

	}

	/**
	 * Metodo che restituisce la cardinalita'  del traning set in osservazione
	 *
	 * @return getNumberOfExamples
	 */
	public int getNumberOfExamples() {
		return numberOfExamples;
	}

	/**
	 * Metodo che restituisce la cardinalita'  degli attributi indipendenti
	 *
	 * @return length dell'array explanatorySet
	 */
	public int getNumberOfExplanatoryAttributes() {
		return explanatorySet.length;
	}

	/**
	 * Restituisce il valore dell'attributo di classe per l'esempio exampleIndex
	 * 
	 * @param int exampleIndex - indice di riga per la matrice data[][] per uno
	 *        specifico esempio
	 * @return double - valore dell'attributo di classe per l'esempio indicizzato in
	 *         input
	 */
	public Double getClassValue(int exampleIndex) {
		return (Double) data[exampleIndex][classAttribute.getIndex()];
	}

	/**
	 * Restituisce il valore dell'attributo indicizzato da attributeIndex per
	 * l'esempio exampleIndex
	 *
	 * @return data[exampleIndex][attributeIndex]
	 */

	public Object getExplanatoryValue(int exampleIndex, int attributeIndex) {
		return data[exampleIndex][attributeIndex];
	}

	public String toString() {
		String value = "";
		for (int i = 0; i < numberOfExamples; i++) {
			for (int j = 0; j < explanatorySet.length; j++)
				value += data[i][j] + ",";

			value += data[i][explanatorySet.length] + "\n";
		}
		return value;

	}

	/**
	 * Ordina il sottoinsieme di esempi compresi nell'intervallo [beginExampleIndex,
	 * endExampleIndex] in data[][] rispetto allo specifico attributo attribute.
	 *
	 * @param attribute         Attributo i cui valori devono essere ordinati
	 * @param beginExampleIndex - indice che identifica il sotto-insieme di training
	 *                          coperto dal nodo corrente
	 * @param endExampleIndex   - indice che identifica il sotto-insieme di training
	 *                          coperto dal nodo corrente
	 */
	public void sort(Attribute attribute, int beginExampleIndex, int endExampleIndex) {
		quicksort(attribute, beginExampleIndex, endExampleIndex);
	}

	// scambio esempio i con esempio j
	private void swap(int i, int j) {
		Object temp;
		for (int k = 0; k < getNumberOfExplanatoryAttributes() + 1; k++) {
			temp = data[i][k];
			data[i][k] = data[j][k];
			data[j][k] = temp;
		}

	}

	/*
	 * Partiziona il vettore rispetto all'elemento x e restiutisce il punto di
	 * separazione
	 */
	private int partition(DiscreteAttribute attribute, int inf, int sup) {
		int i, j;

		i = inf;
		j = sup;
		int med = (inf + sup) / 2;
		String x = (String) getExplanatoryValue(med, attribute.getIndex());
		swap(inf, med);

		while (true) {
			while (i <= sup && ((String) getExplanatoryValue(i, attribute.getIndex())).compareTo(x) <= 0) {
				i++;
			}
			while (((String) getExplanatoryValue(j, attribute.getIndex())).compareTo(x) > 0) {
				j--;
			}

			if (i < j) {
				swap(i, j);
			} else
				break;
		}
		swap(inf, j);
		return j;

	}

	/*
	 * Algoritmo quicksort per l'ordinamento di un array di interi A usando come
	 * relazione d'ordine totale "<="
	 *
	 * @param A
	 */
	private void quicksort(Attribute attribute, int inf, int sup) {

		if (inf <= sup) {

			int pos;

			pos = partition((DiscreteAttribute) attribute, inf, sup);

			if ((pos - inf) < (sup - pos + 1)) {
				quicksort(attribute, inf, pos - 1);
				quicksort(attribute, pos + 1, sup);
			} else {
				quicksort(attribute, pos + 1, sup);
				quicksort(attribute, inf, pos - 1);
			}

		}

	}

	/**
	 * Restituisce l'attributo indicizzato da index in explanatorySet[]
	 *
	 * @param index Indice nell'array explanatorySet[] per uno specifico attributo
	 *              indipendente
	 * @return oggetto Attribute indicizzato da index
	 */
	public Attribute getExplanatoryAttribute(int index) {
		return explanatorySet[index];
	}

	/**
	 * @return Oggetto corrispondente all'attributo di classe
	 */
	@SuppressWarnings("unused")
	private ContinuousAttribute getClassAttribute() {
		return classAttribute;
	}

//    /**
//     * Consente il test delle classi implementate, in particolare permette la stampa
//     * degli esempi ordinati per valori di attributo
//     */
//    public static void main(String args[]) throws FileNotFoundException {
//	Data trainingSet = new Data("servo.dat");
//	System.out.println(trainingSet);
//
//	for (int jColumn = 0; jColumn < trainingSet.getNumberOfExplanatoryAttributes(); jColumn++) {
//	    System.out.println("ORDER BY " + trainingSet.getExplanatoryAttribute(jColumn).toString());
//	    trainingSet.quicksort(trainingSet.getExplanatoryAttribute(jColumn), 0,
//		    trainingSet.getNumberOfExamples() - 1);
//	    System.out.println(trainingSet);
//	}
//
//    }

	private boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
