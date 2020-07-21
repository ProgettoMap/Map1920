package tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import data.Attribute;
import data.ContinuousAttribute;
import data.Data;

/**
 * La classe ContinuousNode rappresenta un nodo corrispondente ad un attributo
 * continuo
 */
@SuppressWarnings("serial")
public class ContinuousNode extends SplitNode implements Serializable {

	ContinuousNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, ContinuousAttribute attribute) {
		super(trainingSet, beginExampleIndex, endExampleIndex, attribute);
	}

	@Override
	void setSplitInfo(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute) {
		// Update mapSplit defined in SplitNode -- contiene gli indici del
		// partizionamento
		Double currentSplitValue = (Double) trainingSet.getExplanatoryValue(beginExampleIndex, attribute.getIndex());
		double bestInfoVariance = 0;
		List<SplitInfo> bestMapSplit = null;

		for (int i = beginExampleIndex + 1; i <= endExampleIndex; i++) {
			Double value = (Double) trainingSet.getExplanatoryValue(i, attribute.getIndex());
			if (value.doubleValue() != currentSplitValue.doubleValue()) {
				double localVariance = new LeafNode(trainingSet, beginExampleIndex, i - 1).getVariance();
				double candidateSplitVariance = localVariance;
				localVariance = new LeafNode(trainingSet, i, endExampleIndex).getVariance();
				candidateSplitVariance += localVariance;
				if (bestMapSplit == null) {
					bestMapSplit = new ArrayList<SplitInfo>();
					bestMapSplit.add(new SplitInfo(currentSplitValue, beginExampleIndex, i - 1, 0, "<="));
					bestMapSplit.add(new SplitInfo(currentSplitValue, i, endExampleIndex, 1, ">"));
					bestInfoVariance = candidateSplitVariance;
				} else {

					if (candidateSplitVariance < bestInfoVariance) {
						bestInfoVariance = candidateSplitVariance;
						bestMapSplit.set(0, new SplitInfo(currentSplitValue, beginExampleIndex, i - 1, 0, "<="));
						bestMapSplit.set(1, new SplitInfo(currentSplitValue, i, endExampleIndex, 1, ">"));
					}
				}
				currentSplitValue = value;
			}
		}
		mapSplit = bestMapSplit;
		// rimuovo split inutili (che includono tutti gli esempi nella stessa
		// partizione)

		if ((mapSplit.get(1).beginIndex == mapSplit.get(1).getEndIndex()))
			mapSplit.remove(1);

	}

	@Override
	int testCondition(Object value) {

		int valueInt = (int) value;
		int k = 0;
		for (SplitInfo splitInfo : mapSplit) {
			if (splitInfo.getSplitValue().equals(valueInt))
				return k;
			k += 1;
		}
		return -1;
	}

	@Override
	public String toString() {
		return "CONTINUOUS " + super.toString();
	}

	@Override
	public int compareTo(SplitNode o) { //TODO: OBBLIGATORIAMENTE spostarla nella classe madre
		return ((Double)this.getVariance()).compareTo(o.getVariance());
	}

}
