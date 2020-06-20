package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Modella l'insieme di esempi di training
 */
public class Data {

	private static final int NUM_PAROLE_RIGA_SCHEMA = 2;
	private static final int NUM_PAROLE_RIGA_DESC = 3;
	private static final int NUM_PAROLE_RIGA_TARGET = 2;
	private static final int NUM_PAROLE_RIGA_DATA = 2;

	private Object data[][]; // Matrice nXm di tipo Object che contiene il training set organizzato come
	// numberOfExamples X numberAttribute

	private int numberOfExamples; // Cardinalita'  del training set

	// NOTE: E' la riga letta nel traning set nel formato Attribute es motor
	// A,B,C,D,E
	private List<Attribute> explanatorySet = new LinkedList<Attribute>(); // Array di oggetti di tipo Attribute per
																			// rappresentare gli attributi
	// indipendenti di tipo discreto

	private ContinuousAttribute classAttribute; // Oggetto per modellare l'attributo di classe ContinuousAttribute

	public Data(String fileName) throws TrainingDataException {

		System.out.println("Starting data acquisition phase:\n");

		boolean isTagTargetFound = false;
		boolean isTagDescFound = false;

		File inFile = new File(fileName);
		Scanner sc = null;
		int rowFileRead = 0; // Righe lette (necessario per indicare l'errore nel file)
		try {
			sc = new Scanner(inFile);
			String line = sc.nextLine(); // Leggo la prima riga
			rowFileRead++; // Incremento numero righe lette

			String s[] = line.split(" ");
			if (s.length != NUM_PAROLE_RIGA_SCHEMA) {
				throw new TrainingDataException("Errore nel training set a riga " + rowFileRead
						+ ": il numero di parole trovate nella riga è diverso da quello atteso.\n"
						+ "Ricontrolla la sintassi.\nEsempio: '@schema <numero intero positivo maggiore di 0>'");
			}

			if (!s[0].equals("@schema")) {
				throw new TrainingDataException(
						"Errore nel training set a riga " + rowFileRead + ": Attributo @schema non trovato.");
			}

			if (!s[1].matches("[0-9]+")) {
				throw new TrainingDataException("Errore nel training set a riga " + rowFileRead
						+ ": Il valore dell'attributo @schema deve essere intero! Valore inserito: " + s[1]);
			}

			// explanatorySet = new Attribute[new Integer(s[1])];
			explanatorySet = new LinkedList<Attribute>();
			int explanatorySetSize = new Integer(s[1]);
			short iAttribute = 0;
			line = sc.nextLine();
			rowFileRead++;

			while (!line.contains("@data")) { // Trovo tutti i desc e i target
				if (sc.hasNextLine()) {
					s = line.split(" ");

					if (s[0].equals("@desc") && iAttribute <= explanatorySetSize - 1) {
						// aggiungo l'attributo allo spazio descrittivo
						// @desc motor discrete A,B,C,D,E
						if (!isTagDescFound)
							isTagDescFound = true;

						if (s.length != NUM_PAROLE_RIGA_DESC) {
							throw new TrainingDataException("Errore nel training set a riga " + rowFileRead
									+ ": il numero di parole trovate nella riga è diverso da quello atteso.\n"
									+ "Ricontrolla la sintassi.\n"
									+ "Esempio: '@desc <nome attributo> <valori attributi separati da virgola>' ");
						}

						Set<String> discreteValues = new TreeSet<String>();
						for (String string : s[2].split(",")) {
							discreteValues.add(string);
						}

						explanatorySet.add(iAttribute, new DiscreteAttribute(s[1], iAttribute, discreteValues));
						iAttribute++;

					} else if (s[0].equals("@target")) {

						if (!isTagTargetFound)
							isTagTargetFound = true;

						if (s.length != NUM_PAROLE_RIGA_TARGET) {
							throw new TrainingDataException("Errore nel training set a riga " + rowFileRead
									+ ": il numero di parole trovate nella riga è diverso da quello atteso.\n"
									+ "Ricontrolla la sintassi.\n" + "Esempio: '@target <nome attributo>'");
						}

						classAttribute = new ContinuousAttribute(s[1], iAttribute);

					}

					line = sc.nextLine();
					rowFileRead++;

				} else {
					throw new TrainingDataException(new NoSuchElementException().toString()
							+ ": Attributo @data non trovato nel training set.");
				}

			}

			// Se sono arrivato all'attributo data e non ho trovato questi due parametri,
			// andrò in eccezione
			if (!isTagDescFound) {
				throw new TrainingDataException("Errore nel training set: Tag @desc non trovato");
			}
			if (!isTagTargetFound) {
				throw new TrainingDataException("Errore nel training set: Tag @target non trovato");
			}

			// Se il numero di attributi individuato è diverso dal valore di schema
			if (explanatorySet.size() != iAttribute) {
				throw new TrainingDataException("Numero di attributi individuati con il tag @desc nel training set "
						+ "diverso da quello specificato nel parametro @schema");
			}

			// avvalorare numero di esempi
			// @data 167
			String[] dataRow = line.split(" "); // Mi aspetto di trovare una riga di tipo @data <numero>
			if (dataRow.length != NUM_PAROLE_RIGA_DATA) {
				throw new TrainingDataException("Errore nel training set a riga " + rowFileRead
						+ ": il numero di parole trovate nella riga dell'attributo @data "
						+ "è diverso da quello atteso.\nRicontrolla la sintassi.\n"
						+ "Esempio: '@data <valore intero maggiore di 0>'");
			} else { // Tag data trovato
				if (!dataRow[0].equals("@data")) {
					throw new TrainingDataException(new ArrayIndexOutOfBoundsException().toString()
							+ "Errore nel training set a riga " + rowFileRead + ": Tag @data non trovato");
				}
				if (!dataRow[1].matches("[1-9][0-9]*")) {
					// Se il parametro di data non è un intero positivo
					throw new TrainingDataException("Errore nel training set a riga " + rowFileRead
							+ ": il parametro specificato per il tag @data non è un intero maggiore di 0");
				}
			}

			numberOfExamples = new Integer(dataRow[1]);
			// popolare data
			data = new Object[numberOfExamples][explanatorySet.size() + 1];
			short iRow = 0;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				rowFileRead++;

				// assumo che attributi siano tutti discreti
				s = line.split(","); // E,E,5,4, 0.28125095

				if (s.length - 1 != explanatorySet.size()) { // Numero di parole lette diverso da @schema + 1
					throw new TrainingDataException(
							new ArrayIndexOutOfBoundsException().toString() + ": I valori letti in riga " + rowFileRead
									+ " sono diversi dal numero di attributi attesi nel file " + fileName);
				}
				if (iRow >= numberOfExamples) { // Individuato a runtime
					throw new TrainingDataException(new ArrayIndexOutOfBoundsException().toString()
							+ ": Il numero di esempi nel training set è diverso dal parametro @data.");
				}

				// Controllo per ogni attributo del training set che sia compreso nella classe
				// degli attributi relativa.
				for (int jColumn = 0; jColumn < s.length - 1; jColumn++) {
					boolean trovato = false;
					int y = 0;
					DiscreteAttribute temp = ((DiscreteAttribute) explanatorySet.get(jColumn)); // TODO: implementare
					// istanceof quando
					// inseriremo gli attributi
					// continui
					while (y < temp.getNumberOfDistinctValues() && !trovato) {
						if (s[jColumn].equalsIgnoreCase(temp.getValue(y))) {
							trovato = true;
						}
						y++;
					}
					if (!trovato)
						throw new TrainingDataException("L'attributo '" + s[jColumn] + "' letto nel dataset '"
								+ fileName + "' nella riga " + rowFileRead + ", colonna " + (jColumn + 1)
								+ " non è tra gli attributi discreti dichiarati nell'intestazione del file");
					else
						data[iRow][jColumn] = s[jColumn];

				}

				if (s[s.length - 1].isEmpty() || !isDouble(s[s.length - 1])) {
					throw new TrainingDataException("Il valore target specificato nella riga " + (iRow + 1)
							+ ", colonna " + s.length + " non è di tipo double");
				}

				data[iRow][s.length - 1] = new Double(s[s.length - 1]);
				iRow++;

			}

			if (iRow != numberOfExamples) // Individuato se ho finito il file
				throw new TrainingDataException("Il numero di esempi nel training set è diverso dal parametro @data.");

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
		return explanatorySet.size();
	}

	/**
	 * Restituisce il valore dell'attributo di classe per l'esempio exampleIndex
	 *
	 * @param int exampleIndex - indice di riga per la matrice data[][] per uno
	 *            specifico esempio
	 *
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

	@Override
	public String toString() {
		String value = "";
		for (int i = 0; i < numberOfExamples; i++) {
			for (int j = 0; j < explanatorySet.size(); j++)
				value += data[i][j] + ",";

			value += data[i][explanatorySet.size()] + "\n";
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
		return explanatorySet.get(index);
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
