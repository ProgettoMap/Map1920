
abstract class Node {

    static int idNodeCount = 0; // contatore dei nodi generati nell'albero
    private int idNode; // identificativo numerico del nodo
    private int beginExampleIndex; // indice nell'array del training set del primo esempio coperto dal nodo
    // corrente
    private int endExampleIndex; // indice nell'array del training set dell'ultimo //esempio coperto dal nodo
    // corrente. beginExampleIndex e endExampleIndex individuano //un sotto-insieme
    // di training.
    private double variance; // valore della varianza calcolata, rispetto all'attributo di classe, nel
    // sotto-insieme di training del nodo

    /**
     * Avvalora gli attributi primitivi di classe, inclusa la varianza che viene
     * calcolata rispetto all'attributo da predire nel sotto-insieme di training
     * coperto dal nodo
     * 
     * @param Data trainingSet - oggetto di classe Data contenente il training set
     *             completo
     * @param int  beginExampleIndex - indice che identifica il sotto-insieme di
     *             training coperto dal nodo corrente
     * @param int  endExampleIndex - indice che identifica il sotto-insieme di
     *             training coperto dal nodo corrente
     */
    Node(Data trainingSet, int beginExampleIndex, int endExampleIndex) {
	this.beginExampleIndex = beginExampleIndex;
	this.endExampleIndex = endExampleIndex;
    }

    //TODO: completare commenti
    /**
     *
     * Input: // Output: identificativo numerico del nodo Comportamento: Restituisce
     * il valore del membro idNode
     * 
     * @return
     */
    int getIdNode() {
	return idNode;
    }

    //TODO: completare commenti
    /**
     * Input: // Output: indice del primo (ultimo) esempio del sotto-insieme
     * rispetto al training set complessivo Comportamento: Restituisce il valore del
     * membro beginExampleIndex (endExampleIndex)
     * 
     * @return
     */
    int getBeginExampleIndex() {
	return beginExampleIndex;
    }

    //TODO: completare commenti
    /**
     * 
     * @return
     */
    int getEndExampleIndex() {
	return endExampleIndex;
    }

    //TODO: completare commenti
    /**
     * Input: // Output: valore della varianza dell’attributo da predire rispetto al
     * nodo corrente Comportamento: Restituisce il valore del membro variance
     * 
     * 
     * @return
     */
    double getVariance() {
	return variance;
    }

    //TODO: completare commenti
    /**
     * Input: // Output: valore del numero di nodi sottostanti Comportamento:E' un
     * metodo astratto la cui implementazione riguarda i nodi di tipo test (split
     * node) dai quali si possono generare figli, uno per ogni split prodotto.
     * Restituisce il numero di tali nodi figli
     * 
     * @return
     */
    abstract int getNumberOfChildren();

    //TODO: completare commenti
    /**
     * Input: // Comportamento: Concatena in un oggetto String i valori di
     * beginExampleIndex,endExampleIndex, variance e restituisce la stringa finale.
     * 
     * 
     */
    public String toString() {
	return ("Esempio primo nodo: " + beginExampleIndex + ". Esempio ultimo nodo: " + endExampleIndex + ". "
		+ "Valore della varianza:" + variance);
    }

}
