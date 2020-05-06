/**
 * 
 */
class LeafNode extends Node {

    Double predictedClassValue; // valore dell'attributo di classe espresso nella foglia corrente

    /**
     * Input: training set complessivo, indici estremi del sotto-insieme di
     * training, coperto nella foglia
     * 
     * Output: //
     * 
     * Comportamento: istanzia un oggetto invocando il costruttore della superclasse
     * e avvalora l'attributo predictedClassValue (come media dei valori
     * dell’attributo di classe che ricadono nella partizione--ossia la porzione di
     * trainingSet compresa tra beginExampelIndex e endExampelIndex )
     * 
     * @param trainingSet
     * @param beginExampleIndex
     * @param endExampleIndex
     */
    public LeafNode(Data trainingSet, int beginExampleIndex, int endExampleIndex) {
	super(trainingSet, beginExampleIndex, endExampleIndex);
    }

    /**
     * restituisce il numero di split originanti dal nodo foglia, ovvero 0.
     */
    @Override
    int getNumberOfChildren() {
	return 0;
    }

    /**
     * Restituisce il valore del membro predictedClassValue
     * 
     * @return the predictedClassValue
     */
    public Double getPredictedClassValue() {
	return predictedClassValue;
    }

    /**
     * Comportamento: invoca il metodo della superclasse e assegnando anche il valore
     * di classe della foglia
     */
    @Override
    public String toString() {
	return super.toString();
    }

}
