import java.io.FileNotFoundException;

import data.Data;
import tree.RegressionTree;
import utility.Keyboard;

class MainTest extends Keyboard {

	public static void main(String[] args) throws FileNotFoundException {

		System.out.println("Training set:\n");
		// String filename = Keyboard.readString();
		Data trainingSet = new Data("prova.dat");
		RegressionTree tree = new RegressionTree(trainingSet);
		tree.printRules();
		tree.printTree();

	}

}
