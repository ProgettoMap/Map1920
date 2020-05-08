/**
 * Entità nodo di split relativo ad un attributo indipendente discreto
 */
class DiscreteNode extends SplitNode {

    /**
     * Input: training set complessivo, indici estremi del sotto-insieme di
     * training, attributo indipendente sul quale si definisce lo split
     * 
     * Comportamento: Istanzia un oggetto invocando il costruttore della superclasse
     * con il parametro attribute
     * 
     * @param Data      trainingSet
     * @param int       beginExampelIndex - Indice estremo del sotto-insieme di
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
     * @param Data      trainingSet
     * @param int       beginExampelIndex - Indice estremo del sotto-insieme di
     *                  training
     * @param int       endExampleIndex - Indice estremo del sotto-insieme di
     *                  training
     * @param Attribute attribute - Attributo indipendente sul quale si definisce lo
     *                  split
     * 
     */
    @Override
    void setSplitInfo(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute) {
	// TODO: rivedere
	SplitNode splitNodeInstance = null;

	for (int i = beginExampleIndex; i < endExampleIndex; i++) {
	    splitNodeInstance.new SplitInfo(attribute, beginExampleIndex, endExampleIndex, getIdNode());

	}

    }

//    private int getNextIndex(int beginExampleIndex, Data trainingSet, Attribute attribute){
//	    
//	    trainingSet
//	    String name = attribute.getName();
//		int i = beginExampleIndex;
//		while(name == attribute.getName()) {
//		    i++;
//		}
//		return i;
//	}

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
	return "DISCRETE: " + super.toString() +  getSplitInfo(getIdNode()) ;
    }

}
