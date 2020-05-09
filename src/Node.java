
abstract class Node {

    static int idNodeCount = 0; // contatore dei nodi generati nell'albero
    private int idNode; // identificativo numerico del nodo
    private int beginExampleIndex; // indice nell'array del training set del primo esempio coperto dal nodo
    // corrente
    private int endExampleIndex; // indice nell'array del training set dell'ultimo esempio coperto dal nodo
    // corrente. beginExampleIndex e endExampleIndex individuano //un sotto-insieme
    // di training
    private double variance; // valore dello SSE calcolato, rispetto all'attributo di classe, nel
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

	 idNodeCount++;
	 this.idNode = idNodeCount;

    }

    /**
     * Restituisce il valore dell'attributo idNode
     * 
     * @return int - identificativo numerico del nodo
     */
    int getIdNode() {
	return idNode;
    }

    /**
     * Restituisce il valore dell'attributo beginExampleIndex
     * 
     * @return int - indice del primo esempio del sotto-insieme rispetto al training
     *         set complessivo
     */
    int getBeginExampleIndex() {
	return beginExampleIndex;
    }

    /**
     * Restituisce il valore dell'attributo endExampleIndex
     * 
     * @return int - indice dell'ultimo esempio del sotto-insieme rispetto al
     *         training set complessivo
     */
    int getEndExampleIndex() {
	return endExampleIndex;
    }

    /**
     * Restituisce il valore dell'attributo variance
     * 
     * @return double - valore dello SSE dell’attributo da predire rispetto al nodo
     *         corrente
     */
    double getVariance() {
	return variance;
    }

    /**
     * Metodo astratto la cui implementazione riguarda i nodi di tipo test (split
     * node) dai quali si possono generare figli, uno per ogni split prodotto.
     * 
     * @return int - Numero di nodi figli
     */
    abstract int getNumberOfChildren();

    /**
     * Concatena in un oggetto String i valori di beginExampleIndex,
     * endExampleIndex, variance e restituisce la stringa finale.
     * 
     */
    public String toString() {
	return ("[Examples: " + beginExampleIndex + "-" + endExampleIndex + "] variance:" + variance);
    }

}
