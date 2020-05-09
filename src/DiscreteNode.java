/**
 * Entità nodo di split relativo ad un attributo indipendente discreto
 */
class DiscreteNode extends SplitNode {

    /**
     * 
     * Istanzia un oggetto invocando il costruttore della superclasse con il
     * parametro attribute
     * 
     * @param Data      trainingSet - oggetto di classe Data contenente il training
     *                  set completo
     * @param int       beginExampleIndex - Indice estremo del sotto-insieme di
     *                  training
     * @param int       endExampleIndex - Indice estremo del sotto-insieme di
     *                  training
     * @param Attribute attribute - Attributo indipendente sul quale si definisce lo
     *                  split
     */
    public DiscreteNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute) {
	super(trainingSet, beginExampleIndex, endExampleIndex, attribute);
    }

    /**
     * (Implementazione da class abstract): Istanzia oggetti SplitInfo (definita
     * come inner class in Splitnode) con ciascuno dei valori discreti
     * dell’attributo relativamente al sotto-insieme di training corrente (ossia la
     * porzione di trainingSet compresa tra beginExampleIndex e endExampleIndex),
     * quindi popola l'array mapSplit[] con tali oggetti
     * 
     * @param Data      trainingSet - oggetto di classe Data contenente il training
     *                  set completo
     * @param int       beginExampleIndex - indice che identifica il sotto-insieme
     *                  di training coperto dal nodo corrente
     * @param int       endExampleIndex - indice che identifica il sotto-insieme di
     *                  training coperto dal nodo corrente
     * @param Attribute attribute - Attributo indipendente sul quale si definisce lo
     *                  split
     * 
     */
    @Override
    void setSplitInfo(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute) {
	SplitInfo[] mapSplitTemp = new SplitInfo[endExampleIndex - beginExampleIndex + 1];
	for (int i = beginExampleIndex; i < endExampleIndex; i++) {
	    mapSplitTemp[i] = new SplitInfo(attribute, beginExampleIndex, endExampleIndex, getIdNode());
	}
	mapSplit = mapSplitTemp;
    }

    private int getNextIndex(Data trainingSet, int beginExampleIndex, Attribute attribute){
	    
	    String name = attribute.getName();
		int i = beginExampleIndex;
		while(name == attribute.getName()) {
		    i++;
		}
		return i;
	}

    /**
     * (Implementazione da class abstract): effettua il confronto del valore in
     * input rispetto al valore contenuto nell’attributo splitValue di ciascuno
     * degli oggetti SplitInfo collezionati in mapSplit[] e restituisce
     * l'identificativo dello split (indice della posizione nell’array mapSplit) con
     * cui il test è positivo
     * 
     * @param Object value - valore discreto dell'attributo che si vuole testare
     *               rispetto a tutti gli split
     * @return int branchNumber - Identificativo dello split (indice della posizione
     *         nell’array mapSplit)
     * 
     */
    @Override
    int testCondition(Object value) {

	return 0;
    }

    /**
     * Invoca il metodo della superclasse specializzandolo per discreti
     */
    @Override
    public String toString() {
	return "DISCRETE: " + super.toString() + getSplitInfo(getIdNode());
    }

}
