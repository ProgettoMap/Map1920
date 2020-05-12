package tree;

import utility.Keyboard;
import data.Data;

/**
 * Entità albero di decisione come insieme di sotto-alberi
 *
 */
public class RegressionTree extends Keyboard {

	Node root; // radice del sotto-albero corrente
	RegressionTree childTree[]; // array di sotto-alberi originanti nel nodo root.
	// Vi è un elemento nell’array per ogni figlio del nodo

	/**
	 * Istanzia un sotto-albero dell'intero albero
	 */
	private RegressionTree() {
	}

	/**
	 * Istanzia un sotto-albero dell'intero albero e avvia l'induzione dell'albero
	 * dagli esempi di training in input
	 *
	 * @param trainingSet
	 */
	public RegressionTree(Data trainingSet) {

		learnTree(trainingSet, 0, trainingSet.getNumberOfExamples() - 1, trainingSet.getNumberOfExamples() * 10 / 100);

	}

	/**
	 * Genera un sotto-albero con il sotto-insieme di input istanziando un nodo
	 * fogliare (isLeaf()) o un nodo di split. In tal caso determina il miglior nodo
	 * rispetto al sotto-insieme di input (determineBestSplitNode()), ed a tale nodo
	 * esso associa un sotto-albero avente radice il nodo medesimo (root) e avente
	 * un numero di rami pari il numero dei figli determinati dallo split
	 * (childTree[]). Ricorsivamente ogni oggetto DecisionTree in childTree[] sarà
	 * re-invocato il metodo learnTree() per l'apprendimento su un insieme ridotto
	 * del sotto-insieme attuale (begin... end). Nella condizione in cui il nodo di
	 * split non origina figli, il nodo diventa fogliare.
	 *
	 *
	 * @param Data trainingSet oggetto di classe Data contenente il training set
	 *             completo
	 * @param      int begin indice che identifica il sotto-insieme di training
	 *             coperto dal nodo corrente
	 * @param      int end indice che identifica il sotto-insieme di training
	 *             coperto dal nodo corrente
	 * @param      int numberOfExamplesPerLeaf Numero massimo che una foglia deve
	 *             contenere
	 */
	void learnTree(Data trainingSet, int begin, int end, int numberOfExamplesPerLeaf) {
		if (isLeaf(trainingSet, begin, end, numberOfExamplesPerLeaf)) {
			// determina la classe che compare più frequentemente nella partizione corrente
			root = new LeafNode(trainingSet, begin, end);
		} else // split node
		{
			root = determineBestSplitNode(trainingSet, begin, end);

			if (root.getNumberOfChildren() > 1) {
				childTree = new RegressionTree[root.getNumberOfChildren()];
				for (int i = 0; i < root.getNumberOfChildren(); i++) {
					childTree[i] = new RegressionTree();

					int beginIndex = ((SplitNode) root).getSplitInfo(i).beginIndex;
					int endIndex = ((SplitNode) root).getSplitInfo(i).endIndex;

					childTree[i].learnTree(trainingSet, beginIndex, endIndex, numberOfExamplesPerLeaf);
				}
			} else
				root = new LeafNode(trainingSet, begin, end);

		}
	}

	/**
	 * Per ciascun attributo indipendente istanzia il DiscreteNode associato e
	 * seleziona il nodo di split con minore varianza tra i DiscreteNode istanziati.
	 *
	 * Ordina la porzione di trainingSet corrente (tra begin ed end) rispetto
	 * all’attributo indipendente del nodo di split selezionato.
	 *
	 * Restituisce il nodo selezionato.
	 *
	 * @param Data  - oggetto di classe Data contenente il training set completo
	 * @param begin - indice che identifica il sotto-insieme di training coperto dal
	 *              nodo corrente
	 * @param end   - indice che identifica il sotto-insieme di training coperto dal
	 *              nodo corrente
	 * @return SplitNode - nodo di split migliore per il sotto-insieme di training
	 */
	SplitNode determineBestSplitNode(Data trainingSet, int begin, int end) {

		DiscreteNode potentialNode = new DiscreteNode(trainingSet, begin, end, trainingSet.getExplanatoryAttribute(0));
		DiscreteNode temp;

		for (int i = 1; i < trainingSet.getNumberOfExplanatoryAttributes(); i++) {
			temp = new DiscreteNode(trainingSet, begin, end, trainingSet.getExplanatoryAttribute(i));
			if (potentialNode.getVariance() > temp.getVariance())
				potentialNode = temp;
		}

		trainingSet.sort(potentialNode.getAttribute(), begin, end);

		return potentialNode;
	}

	/**
	 * Stampa le informazioni dell'intero albero (compresa una intestazione)
	 */
	public void printTree() {
		System.out.println("********* TREE **********\n");
		System.out.println(toString());
		System.out.println("*************************\n");
	}

	/**
	 * Scandisce ciascun ramo dell'albero completo dalla radice alla foglia,
	 * concatenando le informazioni dei nodi di split fino al nodo foglia. In
	 * particolare per ogni sotto-albero (oggetto DecisionTree) in childTree[]
	 * concatena le informazioni del nodo root: se è di split discende
	 * ricorsivamente l'albero per ottenere le informazioni del nodo sottostante
	 * (necessario per ricostruire le condizioni in AND) di ogni ramo-regola, se è
	 * di foglia (leaf) termina l'attraversamento visualizzando la regola.
	 */
	public void printRules() {

		System.out.println("********* RULES **********\n");
		printRules("");
		System.out.println("\n**************************\n");

	}

	/**
	 * Supporta il metodo public void printRules(). Concatena alle informazioni in
	 * current del precedente nodo quelle del nodo root del corrente sotto-albero
	 * (oggetto DecisionTree): se il nodo corrente è di split il metodo viene
	 * invocato ricorsivamente con current e le informazioni del nodo corrente, se è
	 * di fogliare (leaf) visualizza tutte le informazioni concatenate.
	 *
	 * @param current - Informazioni del nodo di split del sotto-albero al livello
	 *                superiore
	 */
	private void printRules(String current) {

		if (root instanceof LeafNode) {

			current += " ==> Class = " + ((LeafNode) root).getPredictedClassValue();
			System.out.println(current);

		} else if (root instanceof SplitNode) {

			current += ((SplitNode) root).getAttribute() + " ";

			for (int i = 0; i < root.getNumberOfChildren(); i++) {

				String temp = "";

				temp = current + ((SplitNode) root).getSplitInfo(i).getComparator() + " "
						+ ((SplitNode) root).getSplitInfo(i).getSplitValue();

				if (childTree[i].root.getNumberOfChildren() != 0)
					temp += " AND ";

				childTree[i].printRules(temp);

			}

		}

	}

	/**
	 * Restituisce una stringa con le informazioni dell'intero albero
	 *
	 * Concatena in una String tutte le informazioni di root-childTree[] correnti
	 * invocando i relativi metodi toString(). Nel caso il root corrente è di split
	 * vengono concatenate anche le informazioni dei rami.
	 *
	 */
	public String toString() {

		String tree = root.toString() + "\n";

		if (root instanceof LeafNode) {

		} else // split node
		{

			for (int i = 0; i < childTree.length; i++)
				tree += childTree[i];
		}
		return tree;
	}

	/**
	 * Verifica se il sotto-insieme corrente può essere coperto da un nodo foglia
	 * controllando che il numero di esempi del training set compresi tra begin ed
	 * end sia minore o uguale di numberOfExamplesPerLeaf.
	 *
	 * N.B. isLeaf() è chiamato da learnTree() che è chiamato dal costruttore di
	 * RegressionTree dove numberOfExamplesPerLeaf è fissato al 10% della dimensione
	 * del training set
	 *
	 * @param Data trainingSet - oggetto di classe Data contenente il training set
	 *             completo
	 * @param      int indice che identifica il sotto-insieme di training coperto
	 *             dal nodo corrente
	 * @param      int indice che identifica il sotto-insieme di training coperto
	 *             dal nodo corrente
	 * @param      int Numero minimo che una foglia deve contenere
	 *
	 * @return boolean - Ritorna vero se la partizione data contiene un nodo
	 *         fogliare, falso altrimenti
	 */
	boolean isLeaf(Data trainingSet, int begin, int end, int numberOfExamplesPerLeaf) {
		return ((end - begin + 1) <= numberOfExamplesPerLeaf);
	}

	/**
	 * Visualizza le informazioni di ciascuno split dell'albero
	 * (SplitNode.formulateQuery()) e per il corrispondente attributo acquisisce il
	 * valore dell'esempio da predire da tastiera.
	 * 
	 * Se il nodo root corrente è leaf termina l'acquisizione e visualizza la
	 * predizione per l’attributo classe, altrimenti invoca ricorsivamente sul
	 * figlio di root in childTree[] individuato dal valore acquisito da tastiera.
	 * 
	 * Il metodo sollevare l'eccezione UnknownValueException qualora la risposta
	 * dell’utente non permetta di selezionare una ramo valido del nodo di split.
	 * L'eccezione sarà gestita nel metodo che invoca predictClass() .
	 * 
	 * @return Double - oggetto contenente il valore di classe predetto per
	 *         l'esempio acquisito
	 * @throws UnknownValueException
	 */
//	Double predictClass() throws UnknownValueException {
//
//		if (root instanceof LeafNode)
//			return ((LeafNode) root).getPredictedClassValue();
//		else {
//			int risp;
//			System.out.println(((SplitNode) root).formulateQuery());
//			risp = Keyboard.readInt();
//			if (risp == -1 || risp >= root.getNumberOfChildren())
//				throw new UnknownValueException(
//						"The answer should be an integer between 0 and " + (root.getNumberOfChildren() - 1) + "!");
//			else
//				return childTree[risp].predictClass();
//		}
//	}
}
