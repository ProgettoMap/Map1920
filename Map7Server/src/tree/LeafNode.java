package tree;

import java.io.Serializable;

import data.Data;

/**
 * Entità Nodo fogliare
 */
@SuppressWarnings("serial")
class LeafNode extends Node implements Serializable{

	Double predictedClassValue; // valore dell'attributo di classe espresso nella foglia corrente

	/**
	 * Istanzia un oggetto invocando il costruttore della superclasse
	 *
	 * Avvalora l'attributo predictedClassValue (come media dei valori
	 * dell’attributo di classe che ricadono nella partizione -- ossia la porzione
	 * di trainingSet compresa tra beginExampleIndex e endExampleIndex)
	 *
	 * @param Data trainingSet - oggetto di classe Data contenente il training set
	 *             completo
	 * @param      int beginExampleIndex - indice che identifica il sotto-insieme di
	 *             training coperto dal nodo corrente
	 * @param      int endExampleIndex - indice che identifica il sotto-insieme di
	 *             training coperto dal nodo corrente
	 */
	public LeafNode(Data trainingSet, int beginExampleIndex, int endExampleIndex) {
		super(trainingSet, beginExampleIndex, endExampleIndex);

		// Avvaloro attributo predictedClassValue come media dei valori
		// dell’attributo di classe che ricadono nella partizione da begin a end
		double sumPredictedClassValue = 0;
		for (int i = beginExampleIndex; i <= endExampleIndex; i++) {
			sumPredictedClassValue += trainingSet.getClassValue(i);
		}
		this.predictedClassValue = sumPredictedClassValue / (endExampleIndex + 1 - beginExampleIndex);

	}

	/**
	 * Restituisce il numero di split originanti dal nodo foglia, ovvero 0.
	 */
	@Override
	int getNumberOfChildren() {
		return 0;
	}

	/**
	 * Restituisce il valore dell'attributo predictedClassValue
	 *
	 * @return double predictedClassValue
	 */
	public Double getPredictedClassValue() {
		return predictedClassValue;
	}

	/**
	 * Invoca il metodo della superclasse e assegna anche il valore di classe della
	 * foglia
	 */
	@Override
	public String toString() {
		return "LEAF : class=" + getPredictedClassValue() + " Nodo: " + super.toString();
	}

}
