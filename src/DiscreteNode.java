/**
 *  Entità nodo di split relativo ad un attributo indipendente discreto
 */
class DiscreteNode extends SplitNode {

    /**
     * Input: training set complessivo, indici estremi del sotto-insieme di
     * training, attributo indipendente sul quale si definisce lo split
     * 
     * Output: //
     * 
     * Comportamento: Istanzia un oggetto invocando il costruttore della superclasse
     * con il parametro attribute
     * 
     * @param trainingSet
     * @param beginExampleIndex
     * @param endExampleIndex
     * @param attribute
     */
    public DiscreteNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute) {
	super(trainingSet, beginExampleIndex, endExampleIndex, attribute);
    }

    /**
     * Input: training set complessivo, indici estremi del sotto-insieme di
     * training, attributo indipendente sul quale si definisce lo split
     * 
     * Output: //
     * 
     * Comportamento (Implementazione da class abstract): istanzia oggetti SpliInfo
     * (definita come inner class in Splitnode) con ciascuno dei valori discreti
     * dell’attributo relativamente al sotto-insieme di training corrente (ossia la
     * porzione di trainingSet compresa tra beginExampelIndex e endExampelIndex),
     * quindi popola l'array c mapSplit[] con tali oggetti.
     */
    @Override
    void setSplitInfo(Data trainingSet, int beginExampelIndex, int endExampleIndex, Attribute attribute) {

    }

    /**
     * Input: valore discreto dell'attributo che si vuole testare rispetto a tutti
     * gli split
     * 
     * Output: numero del ramo di split
     * 
     * Comportamento (Implementazione da class abstract): effettua il confronto del
     * valore in input rispetto al valore contenuto nell’attributo splitValue di
     * ciascuno degli oggetti SplitInfo collezionati in mapSplit[] e restituisce
     * l'identificativo dello split (indice della posizione nell’array mapSplit) con
     * cui il test è positivo
     */
    @Override
    int testCondition(Object value) {
	return 0;
    }

    /**
     * Comportamento:invoca il metodo della superclasse specializzandolo per
     * discreti
     */
    @Override
    public String toString() {
	// TODO Auto-generated method stub
	return super.toString();
    }

}
