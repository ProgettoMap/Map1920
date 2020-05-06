abstract class SplitNode extends Node {

    // Classe che aggrega tutte le informazioni riguardanti un nodo di split
    /*
     * NOTE: E' la descrizione della variabile indipendente
     * Es.
     * Descrizione: motor | Valori: A,B,C,D,E Descrizione: screw | Valori: A,B,C,D,E
     * Descrizione: pgain | Valori: 3,4,5,6 Descrizione: vgain | Valori: 1,2,3,4,5
     */
    class SplitInfo {

	Object splitValue; // valore di tipo Object (di un attributo indipendente) che definisce uno split

	int beginIndex;
	int endIndex;
	int numberChild; // numero di split (nodi figli) originanti dal nodo corrente
	String comparator = "="; // operatore matematico che definisce il test nel nodo
	// corrente (“=” per valori discreti)

	// TODO: aggiungere documentazione
	/**
	 * 
	 * 
	 * @param splitValue
	 * @param beginIndex
	 * @param endIndex
	 * @param numberChild
	 */
	SplitInfo(Object splitValue, int beginIndex, int endIndex, int numberChild) {
	    // Costruttore che avvalora gli attributi di classe per split a valori discreti
	    this.splitValue = splitValue;
	    this.beginIndex = beginIndex;
	    this.endIndex = endIndex;
	    this.numberChild = numberChild;
	}

	// TODO: aggiungere documentazione
	/**
	 * 
	 * @param splitValue
	 * @param beginIndex
	 * @param endIndex
	 * @param numberChild
	 */
	SplitInfo(Object splitValue, int beginIndex, int endIndex, int numberChild, String comparator) {
	    // Costruttore che avvalora gli attributi di classe per generici split (da usare
	    // per valori continui)
	    this.splitValue = splitValue;
	    this.beginIndex = beginIndex;
	    this.endIndex = endIndex;
	    this.numberChild = numberChild;
	    this.comparator = comparator;
	}

	// TODO: aggiungere documentazione
	/**
	 * 
	 * @return
	 */
	int getBeginindex() {
	    return beginIndex;
	}

	// TODO: aggiungere documentazione
	/**
	 * 
	 * @return
	 */
	int getEndIndex() {
	    return endIndex;
	}

	// TODO: aggiungere documentazione
	/**
	 * 
	 * @return
	 */
	Object getSplitValue() {
	    return splitValue;
	}

	// TODO: aggiungere documentazione
	/**
	 * 
	 */
	public String toString() {
	    return "child " + numberChild + " split value" + comparator + splitValue + "[Examples:" + beginIndex + "-"
		    + endIndex + "]";
	}

	// TODO: aggiungere documentazione
	/**
	 * 
	 * @return
	 */
	String getComparator() {
	    return comparator;
	}

    }

    Attribute attribute; // oggetto Attribute che modella l'attributo indipendente sul quale lo split è
    // generato
    SplitInfo mapSplit[]; // array per memorizzare gli split candidati in una struttura dati di dimensione
    // pari ai possibili valori di test
    double splitVariance; // attributo che contiene il valore di varianza a seguito del partizionamento
    // indotto dallo split corrente

    /**
     * Input: training set complessivo, indici estremi del sotto-insieme di
     * training, attributo indipendente sul quale si definisce lo split
     * 
     * Output: //
     * 
     * Comportamento:metodo abstract per generare le informazioni necessarie per
     * ciascuno degli split candidati (in mapSplit[])
     * 
     * 
     * @param trainingSet
     * @param beginExampelIndex
     * @param endExampleIndex
     * @param attribute
     */
    abstract void setSplitInfo(Data trainingSet, int beginExampelIndex, int endExampleIndex, Attribute attribute);

    /**
     * Input: valore dell'attributo che si vuole testare rispetto a tutti gli split
     * 
     * Output: //
     * 
     * Comportamento:metodo abstract per modellare la condizione di test (ad ogni
     * valore di test c'è un ramo dallo split)
     * 
     * @param value
     * @return
     */
    abstract int testCondition(Object value);

    /**
     * Input: training set complessivo, indici estremi del sotto-insieme di
     * training, attributo indipendente sul quale si definisce lo split
     * 
     * Output: //
     * 
     * 
     * Comportamento: metodo abstract per generare le informazioni necessarie per
     * ciascuno degli split candidati (in mapSplit[])
     * 
     * 
     * @param trainingSet
     * @param beginExampleIndex
     * @param endExampleIndex
     * @param attribute
     */
    SplitNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute) {
	super(trainingSet, beginExampleIndex, endExampleIndex);
	this.attribute = attribute;
	trainingSet.sort(attribute, beginExampleIndex, endExampleIndex); // order by attribute
	setSplitInfo(trainingSet, beginExampleIndex, endExampleIndex, attribute);

	// compute variance
	splitVariance = 0;
	for (int i = 0; i < mapSplit.length; i++) {

	    double localVariance = new LeafNode(trainingSet, mapSplit[i].getBeginindex(), mapSplit[i].getEndIndex())
		    .getVariance();
	    splitVariance += (localVariance);

	}
    }

    /**
     * Comportamento: restituisce l'oggetto per l'attributo usato per lo split
     * 
     * @return
     */
    Attribute getAttribute() {
	return attribute;
    }

    /**
     * Comportamento: restituisce l'information gain per lo split corrente
     */
    double getVariance() {
	return splitVariance;
    }

    /**
     * Comportamento: restituisce il numero dei rami originanti nel nodo corrente
     */
    int getNumberOfChildren() {
	return mapSplit.length;
    }

    /**
     * Comportamento: restituisce le informazioni per il ramo in mapSplit[]
     * indicizzato da child.
     * 
     * @param child
     * @return
     */
    SplitInfo getSplitInfo(int child) {
	return mapSplit[child];
    }

    /**
     * Comportamento: concatena le informazioni di ciascuno test (attributo,
     * operatore e valore) in una String finale.
     * 
     * Necessario per la predizione di nuovi esempi
     * 
     * @return
     */
    String formulateQuery() {
	String query = "";
	for (int i = 0; i < mapSplit.length; i++)
	    query += (i + ":" + attribute + mapSplit[i].getComparator() + mapSplit[i].getSplitValue()) + "\n";
	return query;
    }

    /**
     * Concatena le informazioni di ciascuno test (attributo, esempi coperti,
     * varianza, varianza di Split) in una String finale.
     */
    public String toString() {
	String v = "SPLIT : attribute=" + attribute + " " + super.toString() + " Split Variance: " + getVariance()
		+ "\n";

	for (int i = 0; i < mapSplit.length; i++) {
	    v += "\t" + mapSplit[i] + "\n";
	}

	return v;
    }
}
