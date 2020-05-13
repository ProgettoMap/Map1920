import java.io.FileNotFoundException;

import data.Data;
import exception.TrainingDataException;
import exception.UnknownValueException;
import tree.RegressionTree;
import utility.Keyboard;

class MainTest extends Keyboard {

    public static void main(String[] args) throws TrainingDataException, UnknownValueException, FileNotFoundException {

	System.out.println("Training set:\n");

	String filename = Keyboard.readString();
	Data trainingSet = new Data(filename);
	RegressionTree tree = new RegressionTree(trainingSet);
	tree.printRules();
	tree.printTree();
	Character response;
	do {
	    System.out.println("Starting prediction phase!:\n");
	    tree.predictClass();
	    System.out.println("Would you want to repeat? (y/n):\n");
	    do {
		response = Keyboard.readChar();
		if (!isValidResponse(response))
		    System.out.println("Character not valid. Retry please");
	    } while (!isValidResponse(response));

	} while (response == 'y');

    }

    private static boolean isValidResponse(Character response) {
	return (response == 'y' || response == 'n');
    }

}
