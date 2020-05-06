/**
 * 
 *
 */
class RegressionTree {

    Node root; // radice del sotto-albero corrente
    RegressionTree childTree[]; // array di sotto-alberi originanti nel nodo root:vi è un elemento nell’array
			       // per ogni figlio del nodo

    /**
     * Istanzia un sotto-albero dell'intero albero
     * 
     * @param trainingSet
     */
    RegressionTree(Data trainingSet) {

	learnTree(trainingSet, 0, trainingSet.getNumberOfExamples() - 1, trainingSet.getNumberOfExamples() * 10 / 100);

    }

    /**
     * Input: training set complessivo, indici estremi del sotto-insieme di
     * training, numero max che una foglia deve contenere
     * 
     * Output: //
     * 
     * Comportamento: genera un sotto-albero con il sotto-insieme di input
     * istanziando un nodo fogliare (isLeaf()) o un nodo di split. In tal caso
     * determina il miglior nodo rispetto al sotto-insieme di input
     * (determineBestSplitNode()), ed a tale nodo esso associa un sotto-albero
     * avente radice il nodo medesimo (root) e avente un numero di rami pari il
     * numero dei figli determinati dallo split (childTree[]). Ricorsivamente ogni
     * oggetto DecisionTree in childTree[] sarà re-invocato il metodo learnTree()
     * per l'apprendimento su un insieme ridotto del sotto-insieme attuale (begin...
     * end). Nella condizione in cui il nodo di split non origina figli, il nodo
     * diventa fogliare.
     * 
     * @param trainingSet
     * @param begin
     * @param end
     * @param numberOfExamplesPerLeaf
     */
    void learnTree(Data trainingSet, int begin, int end, int numberOfExamplesPerLeaf) {
	if (isLeaf(trainingSet, begin, end, numberOfExamplesPerLeaf)) {
	    // determina la classe che compare pi� frequentemente nella partizione corrente
	    root = new LeafNode(trainingSet, begin, end);
	} else // split node
	{
	    root = determineBestSplitNode(trainingSet, begin, end);

	    if (root.getNumberOfChildren() > 1) {
		childTree = new RegressionTree[root.getNumberOfChildren()];
		for (int i = 0; i < root.getNumberOfChildren(); i++) {
//						childTree[i]=new RgressionTree();
		    childTree[i].learnTree(trainingSet, ((SplitNode) root).getSplitInfo(i).beginIndex,
			    ((SplitNode) root).getSplitInfo(i).endIndex, numberOfExamplesPerLeaf);
		}
	    } else
		root = new LeafNode(trainingSet, begin, end);

	}
    }

    /**
     * Input: training set complessivo, indici estremi del sotto-insieme di training
     * 
     * Output: nodo di split migliore per il sotto-insieme di training
     * 
     * Comportamento: Per ciascun attributo indipendente istanzia il DiscreteNode
     * associato e seleziona il nodo di split con minore varianza tra i DiscreteNode
     * istanziati. Ordina la porzione di trainingSet corrente (tra begin ed end)
     * rispetto all’attributo indipendente del nodo di split selezionato.
     * Restituisce il nodo selezionato.
     * 
     * @param trainingSet
     * @param begin
     * @param end
     * @return
     */
    SplitNode determineBestSplitNode(Data trainingSet, int begin, int end) {
	return null;
    }

    void printTree() {
	System.out.println("********* TREE **********\n");
	System.out.println(toString());
	System.out.println("*************************\n");
    }

    /**
     * Comportamento: Scandisce ciascun ramo dell'albero completo dalla radice alla
     * foglia concatenando le informazioni dei nodi di split fino al nodo foglia. In
     * particolare per ogni sotto-albero (oggetto DecisionTree) in childTree[]
     * concatena le informazioni del nodo root: se è di split discende
     * ricorsivamente l'albero per ottenere le informazioni del nodo sottostante
     * (necessario per ricostruire le condizioni in AND) di ogni ramo-regola, se è
     * di foglia (leaf) termina l'attraversamento visualizzando la regola.
     */
    void printRules() {

    }

    /**
     * Input: Informazioni del nodo di split del sotto-albero al livello superiore
     * 
     * Output:
     * 
     * Comportamento: Supporta il metodo public void printRules(). Concatena alle
     * informazioni in current del precedente nodo quelle del nodo root del corrente
     * sotto-albero (oggetto DecisionTree): se il nodo corrente è di split il metodo
     * viene invocato ricorsivamente con current e le informazioni del nodo
     * corrente, se è di fogliare (leaf) visualizza tutte le informazioni
     * concatenate.
     * 
     */
    void printRules(String current) {

    }

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
     * Input: training set complessivo, indici estremi del sotto-insieme di
     * training, numero minimo che una foglia deve contenere
     * 
     * Output: esito sulle condizioni per i nodi fogliari
     * 
     * Comportamento: verifica se il sotto-insieme corrente può essere coperto da un
     * nodo foglia controllando che il numero di esempi del training set compresi
     * tra begin ed end sia minore uguale di numberOfExamplesPerLeaf. N.B. isLeaf()
     * è chiamato da learnTree() che è chiamato dal costruttore di RegresioinTree
     * dove numberOfExamplesPerLeaf è fissato al 10% della dimensione del training
     * set
     * 
     * @param trainingSet
     * @param begin
     * @param end
     * @param numberOfExamplesPerLeaf
     * @return
     */
    boolean isLeaf(Data trainingSet, int begin, int end, int numberOfExamplesPerLeaf) {
	return false;
    }
}
